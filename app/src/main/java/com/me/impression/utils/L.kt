package com.me.impression.utils

import android.text.TextUtils
import android.util.Log
import com.me.impression.common.AppConfig
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object L {
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private const val JSON_INDENT = 4
    private const val LINE_LIMIT_SIZE = 3 * 1024
    private const val V = 0x1
    private const val D = 0x2
    private const val I = 0x3
    private const val W = 0x4
    private const val E = 0x5
    private const val A = 0x6
    private const val JSON = 0x7
    private var Tag = AppConfig.LOG_TAG
    private const val isDebug = AppConfig.SHOW_LOG
    fun setTag(tag: String) {
        Tag = tag
    }

    fun d(s: String) {
        if (isDebug) {
            logdInner(Tag, s)
        }
    }

    fun i(s: String) {
        if (isDebug) {
            logiInner(Tag, s)
        }
    }

    fun e(s: String) {
        if (isDebug) {
            logeInner(Tag, s)
        }
    }

    fun d(tag: String?, s: String) {
        if (isDebug) {
            logdInner(tag, s)
        }
    }

    fun i(tag: String?, s: String) {
        if (isDebug) {
            logiInner(tag, s)
        }
    }

    fun e(tag: String?, s: String) {
        if (isDebug) {
            logeInner(tag, s)
        }
    }

    fun json(msg: String?) {
        if (isDebug) {
            json(Tag, msg)
        }
    }

    fun json(tag: String?, desc: String, msg: String?) {
        if (isDebug) {
            i(tag, desc)
            json(tag, msg)
        }
    }

    private fun logdInner(tag: String?, msg: String) {
        var msg = msg
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return
        val segmentSize = LINE_LIMIT_SIZE
        val length = msg.length.toLong()
        if (length <= segmentSize) {
            Log.d(tag, msg)
        } else {
            while (msg.length > segmentSize) {
                val logContent = msg.substring(0, segmentSize)
                msg = msg.replace(logContent, "")
                Log.d(tag, logContent)
            }
            Log.d(tag, msg)
        }
    }

    private fun logiInner(tag: String?, msg: String) {
        var msg = msg
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return
        val segmentSize = LINE_LIMIT_SIZE
        val length = msg.length.toLong()
        if (length <= segmentSize) {
            Log.i(tag, msg)
        } else {
            while (msg.length > segmentSize) {
                val logContent = msg.substring(0, segmentSize)
                msg = msg.replace(logContent, "")
                Log.i(tag, logContent)
            }
            Log.i(tag, msg)
        }
    }

    private fun logeInner(tag: String?, msg: String) {
        var msg = msg
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) return
        val segmentSize = LINE_LIMIT_SIZE
        val length = msg.length.toLong()
        if (length <= segmentSize) {
            Log.e(tag, msg)
        } else {
            while (msg.length > segmentSize) {
                val logContent = msg.substring(0, segmentSize)
                msg = msg.replace(logContent, "")
                Log.e(tag, logContent)
            }
            Log.e(tag, msg)
        }
    }

    private fun json(tag: String?, jsonStr: String?) {
        printLog(JSON, tag, jsonStr)
    }

    private fun printLog(type: Int, tagStr: String?, objectMsg: Any?) {
        val msg: String
        val stackTrace =
            Thread.currentThread().stackTrace
        val index = 4
        val className = stackTrace[index].fileName
        var methodName = stackTrace[index].methodName
        val lineNumber = stackTrace[index].lineNumber
        val tag = tagStr ?: className
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#")
            .append(methodName).append(" ] ")
        msg = objectMsg?.toString() ?: "Log with null Object"
        if (msg != null && type != JSON) {
            stringBuilder.append(msg)
        }
        val logStr = stringBuilder.toString()
        when (type) {
            V -> Log.v(tag, logStr)
            D -> Log.d(tag, logStr)
            I -> Log.i(tag, logStr)
            W -> Log.w(tag, logStr)
            E -> Log.e(tag, logStr)
            A -> Log.wtf(tag, logStr)
            JSON -> {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tag, "Empty or Null json content")
                    return
                }
                var message: String? = null
                try {
                    if (msg.startsWith("{")) {
                        val jsonObject = JSONObject(msg)
                        message = jsonObject.toString(JSON_INDENT)
                    } else if (msg.startsWith("[")) {
                        val jsonArray = JSONArray(msg)
                        message = jsonArray.toString(JSON_INDENT)
                    }
                } catch (e: JSONException) {
                    e(tag, """${e.cause!!.message}$msg""".trimIndent())
                    return
                }
                printLine(tag, true)
                message = logStr + LINE_SEPARATOR + message
                val lines =
                    message.split(LINE_SEPARATOR!!.toRegex()).toTypedArray()
                val jsonContent = StringBuilder()
                for (line in lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR)
                    logdInner(tag, jsonContent.toString())
                    jsonContent.delete(0, jsonContent.length)
                }
                printLine(tag, false)
            }
        }
    }

    private fun printLine(tag: String, isTop: Boolean) {
        if (isTop) {
            Log.w(tag, "########################################################\n")
        } else {
            Log.w(tag, "########################################################\n")
        }
    }
}
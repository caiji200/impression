package com.me.impression.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: Jie Cai
 * @desc: Note Book
 */
@Entity
public class NoteRecord {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public int type;
    public String from;
    public String to;
    public String srcText;
    public String destText;
    public String extra;

    public Long createTime;
}

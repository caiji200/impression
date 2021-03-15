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

    public String word;

    public String language;

    public String meaning;

    public String extra;

    public Long createTime;

}

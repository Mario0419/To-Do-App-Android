package com.mx3studios.to_do;

import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mario on 6/20/2016.
 */
public class TodoItem implements Serializable{

    private Integer id = null;
    private String title = "";
    private String description = "";
    private String level = "";
    private String status = "";
    private String notes = "";
    private String completionDate;

    public TodoItem () {

    }

    public TodoItem(Cursor c) {
        this();
        setCursorValues(c);
    }

    private void setCursorValues(Cursor c) {
        title = c.getString(0);
        description = c.getString(1);
        status = c.getString(2);
        completionDate = c.getString(3);
        level = c.getString(4);
        id = c.getInt(5);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getStatusIndex() {
        switch(status) {
            case "To do":
                return 0;
            case "Hold":
                return 1;
            case "Done":
                return 2;
            default:
                return 0;
        }
    }

    public Integer getLevelIndex() {
        switch(level) {
            case "LOW":
                return 0;
            case "MEDIUM":
                return 1;
            case "HIGH":
                return 2;
            default:
                return 0;
        }
    }
}

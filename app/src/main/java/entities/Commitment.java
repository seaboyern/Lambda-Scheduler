package entities;

import android.content.ContentValues;

import java.util.Date;

import database.DatabaseObject;

/**
 * Created by mahmudfasihulazam on 2017-11-11.
 */

public abstract class Commitment implements DatabaseObject {
    private String title;
    private String googleId;
    private String desc;
    private int prio;

    public Commitment(String title, String desc, int prio) {
        this.setTitle(title);
        this.setDesc(desc);
        this.setPrio(prio);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public abstract Date getSequencingDateTime();
}

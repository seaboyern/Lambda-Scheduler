package entities;

import android.content.ContentValues;

import database.DatabaseObject;

/**
 * Created by mahmudfasihulazam on 2017-11-11.
 */

public abstract class Commitment implements DatabaseObject {
    protected String title;
    protected String desc;
    protected int prio;

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

}

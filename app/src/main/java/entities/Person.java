package entities;

import database.DatabaseObject;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

class Person implements DatabaseObject {
    private String fName;
    private String lName;
    private String email;

    public Person(String fName, String lName, String email) {
        this.setfName(fName);
        this.setlName(lName);
        this.setEmail(email);
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

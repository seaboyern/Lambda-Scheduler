package entities;

import database.tables.CourseCommitmentTable;

/**
 * Created by mahmudfasihulazam on 2017-11-11.
 */

public abstract class CourseCommitment extends Commitment {

    private String courseId;
    private float weight;
    private float required;
    private float achieved;

    public CourseCommitment(String title, String desc, int prio) {
        super(title, desc, prio);
        super.setGoogleId("");
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getRequired() {
        return required;
    }

    public void setRequired(float required) {
        this.required = required;
    }

    public float getAchieved() {
        return achieved;
    }

    public void setAchieved(float achieved) {
        this.achieved = achieved;
    }

}

package GradeCalculatorObjects;
import java.util.*;

/**
 * Grade Calculator System Prototype
 */

public class Course {

    public LinkedList<CourseWork> allWork;
    private String professorName;
    private String courseType;
    private int courseNumber;
    private float currentFinalGrade;
    private float goalFinalGrade;

    public Course() {
        allWork = new LinkedList<>();
        professorName = "<Empty>";
        courseType = "<Empty>";
        courseNumber = -1;
        goalFinalGrade = -1;
    }

    public Course(String initProfName, String initCourseType, int initCourseNum, int initGoalFinalGrade){

        allWork = new LinkedList<>();
        professorName = initProfName;
        courseNumber = initCourseNum;
        courseType = initCourseType;
        goalFinalGrade = initGoalFinalGrade;

    }

    public float getCurrentFinalGrade() {
        calcFinalGrade();
        return currentFinalGrade;
    }

    public void setCurrentFinalGrade(float currentFinalGrade) {

        this.currentFinalGrade = currentFinalGrade;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public float getGoalFinalGrade() {
        return goalFinalGrade;
    }

    public void setGoalFinalGrade(float goalFinalGrade) {
        this.goalFinalGrade = goalFinalGrade;
    }

    public CourseWork newCourseWork( String initName, int initWeight){
        CourseWork toAdd = new CourseWork(initName, initWeight);
        allWork.addLast(toAdd);

        return toAdd;
    }

    public CourseWork newCourseWorkWithgrade( String initName, int initWeight, int initGrade){
        CourseWork toAdd = new CourseWork(initName, initWeight, initGrade);
        allWork.addLast(toAdd);

        return toAdd;
    }

    public void calcFinalGrade(){
        float finalGrade = 0;
        CourseWork cur = new CourseWork();

        for(int i = 0; i < allWork.size(); i++){
            cur = allWork.get(i);
            finalGrade += ((cur.getWeight()/100) * (cur.getFinalGrade()));
        }

        currentFinalGrade = finalGrade;
    }

    public float calcGradeNeeded(CourseWork focus){
        float gradeNeeded;
        this.calcFinalGrade();

        gradeNeeded = ( goalFinalGrade - currentFinalGrade ) / focus.getWeight();

        return gradeNeeded * 100;
    }

    public CourseWork findCourseWork(String name){

        for(int i = 0; i < allWork.size(); i++){
            if(allWork.get(i).getName().compareTo(name) == 0){
                return allWork.get(i);
            }
        }

        return null;
    }


    @Override
    public String toString(){
        return courseType + " " + courseNumber;
    }

}

package GradeCalculatorObjects;

import java.util.LinkedList;

/**
 * Grade Calculator System Prototype
 */

public class Term {

    private String name;
    public LinkedList<Course> courses;
    protected Storage terms;

    public Term() {
        name = "<Empty>";
        courses = new LinkedList<>();
        terms = Storage.getInstance();
        terms.insert(this);

    }

    public Term(String initName) {
        name = initName;
        courses = new LinkedList<>();
        terms = Storage.getInstance();
        terms.insert(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course newCourse(String initProfName, String initCourseType, int initCourseNum,
                          int initGoalFinalGrade) {
        Course toAdd = new Course(initProfName, initCourseType, initCourseNum, initGoalFinalGrade);
        courses.addLast(toAdd);

        return toAdd;
    }

    public Course findCourse(String name){

        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).toString().compareTo(name) == 0){
                return courses.get(i);
            }
        }

        return null;
    }

    public static void main(String args[]) {

        //testing a use case for java classes Term, CourseWork, and Course

        //first create a new Term populated with courses
        Term t1 = new Term("Term 1");
        Course CMPT370 = t1.newCourse("Chanchal Roy", "CMPT", 370, 80);
        Course CMPT355 = t1.newCourse("Jerome Jacobs", "CMPT", 355, 75);
        Course MATH266 = t1.newCourse("Christine Smith", "MATH", 266, 60);

        //then populate each course with course work
        CourseWork m1_370 = CMPT370.newCourseWork("Milestone 1", 5);
        CourseWork m2_370 = CMPT370.newCourseWork("Milestone 2", 10);
        CourseWork m3_370 = CMPT370.newCourseWork("Milestone 3", 20);
        CourseWork mid_370 = CMPT370.newCourseWork("Midterm 1", 30);
        CourseWork fin_370 = CMPT370.newCourseWork("Final", 35);

        CourseWork a1_355 = CMPT355.newCourseWork("Assignment 1", 10);
        CourseWork a2_355 = CMPT355.newCourseWork("Assignment 2", 10);
        CourseWork a3_355 = CMPT355.newCourseWork("Assignment 3", 10);
        CourseWork a4_355 = CMPT355.newCourseWork("Assignment 4", 10);
        CourseWork mid_355 = CMPT355.newCourseWork("Midterm", 30);
        CourseWork fin_355 = CMPT355.newCourseWork("Final", 30);

        CourseWork a1_266 = MATH266.newCourseWork("Assignment 1", 5);
        CourseWork a2_266 = MATH266.newCourseWork("Assignment 2", 5);
        CourseWork a3_266 = MATH266.newCourseWork("Assignment 3", 5);
        CourseWork a4_266 = MATH266.newCourseWork("Assignment 4", 5);
        CourseWork mid1_266 = MATH266.newCourseWork("Midterm 1", 10);
        CourseWork mid2_266 = MATH266.newCourseWork("Midterm 2", 10);
        CourseWork mid3_266 = MATH266.newCourseWork("Midterm 3", 10);
        CourseWork fin_266 = MATH266.newCourseWork("Final", 50);

        //now after some time passes insert the grades you got on the specified course work
        m1_370.setFinalGrade(90);
        m2_370.setFinalGrade(100);
        m3_370.setFinalGrade(70);
        mid_370.setFinalGrade(63);

        a1_355.setFinalGrade(98);
        a2_355.setFinalGrade(87);
        a3_355.setFinalGrade(80);
        a4_355.setFinalGrade(75);
        mid_355.setFinalGrade(70);

        a1_266.setFinalGrade(50);
        a2_266.setFinalGrade(20);
        a3_266.setFinalGrade(39);
        a4_266.setFinalGrade(47);
        mid1_266.setFinalGrade(70);
        mid2_266.setFinalGrade(67);
        mid3_266.setFinalGrade(70);

        //before the final for each course we want to see what grade we need in order to meet our goal
        System.out.println("The grade you must get on the CMPT370 final in order to achieve your goal is: "
                + CMPT370.calcGradeNeeded(fin_370));
        System.out.println("The grade you must get on the CMPT355 final in order to achieve your goal is: "
                + CMPT355.calcGradeNeeded(fin_355));
        System.out.println("The grade you must get on the MATH266 final in order to achieve your goal is: "
                + MATH266.calcGradeNeeded(fin_266));


    }
}
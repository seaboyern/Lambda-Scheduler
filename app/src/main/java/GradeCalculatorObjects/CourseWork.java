package GradeCalculatorObjects;

/**
 * Grade Calculator System Prototype
 */

public class CourseWork {

    private String name;
    private float weight;
    private float finalGrade = 0;

    public CourseWork() {
         name = "<Empty>";

    }

    public CourseWork(String newName, float newWeight){
        name = newName;
        weight = newWeight;
    }

    public CourseWork(String newName, float newWeight, float newGrade){
        name = newName;
        weight = newWeight;
        finalGrade = newGrade;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setWeight(float newWeight){
        weight = newWeight;
    }

    public void setFinalGrade(float newFinalGrade){
        finalGrade = newFinalGrade;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    public float getFinalGrade() {
        return finalGrade;
    }


}

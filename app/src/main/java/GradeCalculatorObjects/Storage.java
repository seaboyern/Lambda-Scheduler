package GradeCalculatorObjects;

import java.util.LinkedList;

/**
 * Created by Nicholas on 2017-11-20.
 */

public class Storage {

    public LinkedList<Term> terms = new LinkedList<>();

    private static final Storage ourInstance = new Storage();

    public static Storage getInstance() {
        return ourInstance;
    }

    private Storage() {
    }

    public void insert(Term term){
        this.terms.addLast(term);
    }

    public int numOfTerms(){
        return terms.size();
    }

    public Term findTerm(String termName){
        for(int i = 0; i < terms.size(); i++){
            if(terms.get(i).getName().compareTo(termName) == 0){
                return terms.get(i);
            }
    }
    return null;
}
}


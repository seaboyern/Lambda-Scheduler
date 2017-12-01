package entities.interfaces;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public interface RecurringCommitment extends CommitmentInterface {
    public String getRecFreq();
    public int getRecCount();
    public void setRecFreq(String freq);
    public void setRecCount(int count);
}

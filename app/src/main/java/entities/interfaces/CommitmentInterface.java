package entities.interfaces;

import java.util.Date;

/**
 * Created by mahmudfasihulazam on 2017-11-30.
 */

public interface CommitmentInterface {
    public String getTitle();
    public String getDesc();
    public int getPrio();
    public String getGoogleId();
    public Date getSequencingDateTime();
}

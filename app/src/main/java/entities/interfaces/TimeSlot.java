package entities.interfaces;

import java.util.Date;

/**
 * Created by mahmudfasihulazam on 2017-12-05.
 */

public interface TimeSlot extends Comparable<TimeSlot> {
    public Date getStart();
    public Date getEnd();

    @Override
    public int compareTo(TimeSlot other);
}

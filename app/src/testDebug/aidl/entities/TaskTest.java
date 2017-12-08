package entities;

import com.example.lambda.lambdaorganizer.FormatDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

/**
 * Created by mahmudfasihulazam on 2017-12-07.
 */
public class TaskTest {
    private Task task;
    private Date date;
    private Date start;
    private Date end;

    @Before
    public void setUp() throws Exception {
        this.task = new Task("name", "desc", 1);
        this.date = FormatDateTime.getDateFromString("2017-12-07 11:30:00");
        this.start = FormatDateTime.getDateFromString("2017-12-07 11:30:00");
        this.end = FormatDateTime.getDateFromString("2017-12-07 11:59:00");
        this.task.setTimeBounds("2017-12-07", "11:30:00", "11:59:00");
    }

    @After
    public void tearDown() throws Exception {
        ;
    }

    @Test
    public void getSequencingDateTime() throws Exception {
        Assert.assertEquals(this.start.compareTo(this.task.getSequencingDateTime()), 0);
    }

    @Test
    public void getDate() throws Exception {
        Assert.assertEquals(this.date.compareTo(this.task.getDate()), 0);
    }

    @Test
    public void setDate() throws Exception {
        Date date = FormatDateTime.getDateFromString("2017-12-07 11:35:00");
        this.task.setDate(date);
        Assert.assertEquals(this.task.getDate().compareTo(date), 0);
    }

    @Test
    public void getStart() throws Exception {
        Assert.assertEquals(this.task.getStart().compareTo(this.start), 0);
    }

    @Test
    public void setStart() throws Exception {
        Date start = FormatDateTime.getDateFromString("2017-12-07 11:35:00");
        this.task.setStart(start);
        Assert.assertEquals(this.task.getStart().compareTo(start), 0);
    }

    @Test
    public void getEnd() throws Exception {
        Assert.assertEquals(this.task.getEnd().compareTo(this.end), 0);
    }

    @Test
    public void compareTo() throws Exception {
        Task task1 = new Task("name2", "desc2", 2);
        this.task.setTimeBounds("2017-12-07", "11:59:00", "11:59:00");
        task1.setTimeBounds("2017-12-07", "11:59:00", "11:59:00");
        Assert.assertEquals(this.task.compareTo(task1), 0);
    }

    @Test
    public void setEnd() throws Exception {
        Date end = FormatDateTime.getDateFromString("2017-12-07 11:55:00");
        this.task.setStart(end);
        Assert.assertEquals(this.task.getStart().compareTo(end), 0);
    }

    @Test
    public void setTimeBounds() throws Exception {
        Task task1 = new Task("name2", "desc2", 2);
        this.task.setTimeBounds("2017-12-07", "11:55:00", "11:56:00");
        task1.setTimeBounds("2017-12-07", "11:59:00", "11:59:00");
        Assert.assertEquals(this.task.compareTo(task1), -1);
    }

    @Test
    public String toString() {
        return null;
    }

    @Test
    public void conflict() throws Exception {
        Task task1 = new Task("name2", "desc2", 2);
        this.task.setTimeBounds("2017-12-07", "11:50:00", "11:56:00");
        task1.setTimeBounds("2017-12-07", "11:55:00", "11:59:00");
        Assert.assertEquals(this.task.conflict(task1), true);
    }

}
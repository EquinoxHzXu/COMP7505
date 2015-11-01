package adsa2p1;

import java.util.*;

/**
 * @author Elizur
 */
public class Course implements Comparable<Course> {
    private List<Integer> startTime;
    private List<Integer> endingTime;
    private String courseName;
    private int adjNum;
    private int adjIndex;
    private int roomAssignment;

    public Course() {
    }

    public Course(String courseName) {
        this.startTime = new ArrayList<>();
        this.endingTime = new ArrayList<>();
        this.courseName = courseName;
        this.adjNum = 0;
        this.adjIndex = 0;
        this.roomAssignment = 0;
    }

    public void addStartTime(int time) {
        startTime.add(time);
    }

    public void addEndingTime(int time) {
        endingTime.add(time);
    }

    public int getStartTime(int i) {
        return startTime.get(i);
    }

    public int getEndingTime(int i) {
        return endingTime.get(i);
    }

    public List<Integer> getAllTime() {
        List<Integer> allTime = new ArrayList<>();
        for (int i = 0; i < this.getTotalTimes(); i++) {
            int currentTime = this.getStartTime(i);
            while (currentTime < this.getEndingTime(i)) {
                allTime.add(currentTime);
                currentTime++;
            }
        }
        return allTime;
    }

    public int getTotalTimes() {
        return startTime.size();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setAdjNum(int i) {
        this.adjNum = i;
    }

    public int getAdjNum() {
        return this.adjNum;
    }

    public void setAdjIndex(int i) {
        this.adjIndex = i;
    }

    public int getAdjIndex() {
        return this.adjIndex;
    }

    public void assignRoomNumber(int i) {
        this.roomAssignment = i;
    }

    public int getRoomAssignment() {
        return this.roomAssignment;
    }

    @Override
    public int compareTo(Course course) {
        return course.getAdjNum() - this.getAdjNum();
    }

}

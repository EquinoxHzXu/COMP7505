package adsa2p1;

import java.util.*;

/**
 * @author Elizur
 */
public class ClassifiedCourse implements Comparable<ClassifiedCourse> {
    private List<Course> courseList;
    private int size;

    public ClassifiedCourse() {
        this.courseList = new ArrayList<>();
        this.size = courseList.size();
    }


    public ClassifiedCourse(Course course) {
        this.courseList = new ArrayList<>();
        courseList.add(course);
        this.size = courseList.size();
    }

    public void addCourse(Course course) {
        courseList.add(course);
        this.size = courseList.size();
    }

    public Course getCourse(int i) {
        return this.courseList.get(i);
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public int compareTo(ClassifiedCourse cc) {
        return cc.getSize() - this.getSize();
    }
}

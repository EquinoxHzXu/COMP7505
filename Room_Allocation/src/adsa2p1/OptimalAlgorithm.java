package adsa2p1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Elizur
 */
public class OptimalAlgorithm {
    private CoursesGraph coursesGraph;

    public OptimalAlgorithm(CoursesGraph coursesGraph) throws IOException {
        this.coursesGraph = coursesGraph;
        this.Optimal(coursesGraph.getCourses(), coursesGraph.getAdj());
    }

    public void Optimal(Course[] courses, int[][] adj) throws IOException {
        for (int i = 0; i < courses.length; i++) {
            courses[i].setAdjIndex(i);
        }
        List<ClassifiedCourse> cCList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();
        courseList.addAll(Arrays.asList(courses));
        classify(cCList, courseList, adj);
        int numOfRooms = cCList.size();
        Collections.sort(cCList);
        for (int b = 1; b <= numOfRooms; b++) {
            for (int a = 0; a < cCList.get(b-1).getSize(); a++) {
                cCList.get(b-1).getCourse(a).assignRoomNumber(b);
            }
        }
        saveOutput(coursesGraph.getFilename(), courses, numOfRooms);
    }

    private void classify(List<ClassifiedCourse> classifiedCourses, List<Course> courses, int[][] adj) {
        for (int i = 0; i < courses.size(); i++) {
            if (classifiedCourses.size() == 0) {
                classifiedCourses.add(new ClassifiedCourse(courses.get(i)));
            } else {
                int currentClass = 0;
                for (int j = 0; j < classifiedCourses.size(); j++) {
                    int num = 0;
                    for (int c = 0; c < classifiedCourses.get(j).getSize(); c++) {
                        if (adj[classifiedCourses.get(j).getCourse(c).getAdjIndex()][i] == 1) {
                            break;
                        } else {
                            num++;
                        }
                    }
                    if (num == classifiedCourses.get(j).getSize()) {
                        classifiedCourses.get(j).addCourse(courses.get(i));
                        break;
                    } else {
                        currentClass++;
                    }
                }
                if (currentClass == classifiedCourses.size()) {
                    classifiedCourses.add(new ClassifiedCourse(courses.get(i)));
                }
            }
        }
    }

    private void saveOutput(String filename, Course[] courses, int numOfRoom) throws IOException {
        try {
            FileWriter output = new FileWriter(filename + ".out");
            String ls = System.getProperty("line.separator");
            output.write(String.valueOf(numOfRoom) + ls);
            for (Course course : courses) {
                output.write(course.getCourseName() + " " + String.valueOf(course.getRoomAssignment()) + ls);
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

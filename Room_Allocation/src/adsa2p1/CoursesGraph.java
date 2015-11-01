package adsa2p1;

import java.io.*;
import java.util.*;

/**
 * @author Elizur
 */
public class CoursesGraph {

    private Course[] courses;
    private int[][] adj;
    private String filename;

    public CoursesGraph(String filename) throws IOException {
        this.filename = filename;
        BufferedReader input = new BufferedReader(new FileReader(filename + ".in"));
        String line;
        Scanner scanner;
        try {
            line = input.readLine();
            int size = Integer.parseInt(line);
            this.courses = new Course[size];
            this.adj = new int[size][size];
            for (int i = 0; i < size; i++) {
                int index = 0;
                line = input.readLine();
                scanner = new Scanner(line);
                courses[i] = new Course(scanner.next());
                while (scanner.hasNextInt()) {
                    if (index % 2 == 0) {
                        courses[i].addStartTime(scanner.nextInt());
                    } else {
                        courses[i].addEndingTime(scanner.nextInt());
                    }
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            input.close();
        }
        createAdjacentMatrix(courses);
    }


    private int[][] createAdjacentMatrix(Course[] courses) {
        for (int currentCourse = 1; currentCourse < courses.length; currentCourse++) {
            for (int comparedCourse = 0; comparedCourse < currentCourse; comparedCourse++) {
                for (int time : courses[currentCourse].getAllTime()) {
                    if (courses[comparedCourse].getAllTime().contains(time)) {
                        adj[comparedCourse][currentCourse] = 1;
                        adj[currentCourse][comparedCourse] = 1;
                        break;
                    }
                }
            }
        }
        return adj;
    }

    public Course[] getCourses() {
        return courses;
    }

    public int[][] getAdj() {
        return adj;
    }

    public String getFilename() {
        return filename;
    }
}

package adsa2p1;

import java.io.*;

/**
 * @author Elizur
 */
public class ProblemOne {
    public static void main(String[] args) throws IOException {
        try {
            if (args[1].equals("greedy")) {
                CoursesGraph coursesGraph = new CoursesGraph(args[0]);
                GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(coursesGraph);
            }
            if (args[1].equals("optimal")) {
                CoursesGraph coursesGraph = new CoursesGraph(args[0]);
                OptimalAlgorithm optimalAlgorithm = new OptimalAlgorithm(coursesGraph);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

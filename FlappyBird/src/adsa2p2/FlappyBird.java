package adsa2p2;

import java.io.*;
import java.util.*;

/**
 * @author Elizur
 */
public class FlappyBird {
    private List<Pipe> pipeList;
    private Point startPoint;
    private List<Point> finalPoints;
    private List<Integer> rise;
    private List<Integer> fall;
    private Space space;
    private String filename;
    private Pipe stopPipe;

    public FlappyBird(String filename) throws IOException {
        this.pipeList = new ArrayList<>();
        this.finalPoints = new ArrayList<>();
        this.rise = new ArrayList<>();
        this.fall = new ArrayList<>();
        this.startPoint = new Point();
        this.startPoint.setTime(0);
        this.filename = filename;
        this.stopPipe = new Pipe();

        BufferedReader input = new BufferedReader(new FileReader(filename + ".in"));
        String line;
        Scanner scanner;
        try {
            // line 1 handling
            line = input.readLine();
            scanner = new Scanner(line);
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int pipeNumber = scanner.nextInt();
            this.space = new Space(m, n, pipeNumber);
            // line 2 handling - Start point
            line = input.readLine();
            scanner = new Scanner(line);
            this.startPoint.setHeight(scanner.nextInt());
            //route.add(startPoint);
            // line 3 handling - x[]
            line = input.readLine();
            scanner = new Scanner(line);
            while (scanner.hasNextInt()) {
                rise.add(scanner.nextInt());
            }
            // line 4 hadling - y[]
            line = input.readLine();
            scanner = new Scanner(line);
            while (scanner.hasNextInt()) {
                fall.add(scanner.nextInt());
            }
            // from line 5
            for (int l = 0; l < pipeNumber; l++) {
                List<Integer> pipeLine = new ArrayList<>();
                line = input.readLine();
                scanner = new Scanner(line);
                while (scanner.hasNextInt()) {
                    pipeLine.add(scanner.nextInt());
                }
                Pipe currentPipe = new Pipe(pipeLine.get(0), pipeLine.get(1), pipeLine.get(2));
                pipeList.add(currentPipe);
            }
            // The last time is regarded as a pipe
            pipeList.add(new Pipe(n, 0, m));
            Collections.sort(pipeList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            input.close();
        }
    }

    public List<Pipe> getPipeList() {
        return this.pipeList;
    }

    public Pipe getStopPipe() {
        return this.stopPipe;
    }

    public Point getStartPoint() {
        return this.startPoint;
    }

    public List<Point> getFinalPoints() {
        return this.finalPoints;
    }

    public void Fly() throws IOException {
        List<Point> points = new ArrayList<>();
        points.add(this.getStartPoint());
        int canPass = this.recursiveFly(points, pipeList);
        int minTap = 0;
        int stopPipeIndex = -1;
        if (canPass == 1) {
            for (Point point : this.getFinalPoints()) {
                if (minTap == 0) {
                    minTap = point.getTap();
                } else {
                    if (point.getTap() < minTap) {
                        minTap = point.getTap();
                    }
                }
            }
        } else {
            stopPipeIndex = this.getPipeList().indexOf(this.getStopPipe());
        }
        this.saveOutput(filename, canPass, minTap, stopPipeIndex);
    }

    private List<Point> pointsGeneration(Space space, List<Point> points, List<Integer> rise, List<Integer> fall) {
        List<Point> nextPoints = new ArrayList<>();
        for (Point point : points) {
            if (point.getTime() == -1) {
                // All points cannot pass
                return nextPoints;
            }
            int tap = 1;
            if (point.getHeight() - fall.get(point.getTime()) > 0) {
                Point currentPoint = new Point(point.getTime() + 1, point.getHeight() - fall.get(point.getTime()));
                currentPoint.setTap(point.getTap());
                nextPoints.add(currentPoint);
            }
            while (point.getHeight() + tap * rise.get(point.getTime()) < space.getM()) {
                Point currentPoint = new Point(point.getTime() + 1, point.getHeight() + tap * rise.get(point.getTime()));
                currentPoint.setTap(point.getTap() + tap);
                nextPoints.add(currentPoint);
                tap++;
            }
        }
        getMinTapsPoints(nextPoints);
        return nextPoints;
    }

    private int recursiveFly(List<Point> points, List<Pipe> pipeList) {
        List<Point> filteredPoints = new ArrayList<>();
        if (points.size() == 0) {
            return 0;
        }
        if (points.get(0).getTime() == space.getN()) {
            this.finalPoints = points;
            return 1;
        }
        if (isPipeTime(points, pipeList)) {
            // come across the pipes
            Pipe currentPipe = this.getCurrentPipe(points, pipeList);
            filteredPoints.addAll(filterPoint(points, currentPipe));
            if (filteredPoints.size() == 0) {
                // All points cannot pass
                filteredPoints.add(new Point());
                this.stopPipe = currentPipe;
            }
            // return 0 if for all points the bird cannot pass the pipe, otherwise do the recursion
            return recursiveFly(pointsGeneration(space, filteredPoints, rise, fall), pipeList);
        } else {
            // do not come across the pipes
            return recursiveFly(pointsGeneration(space, points, rise, fall), pipeList);
        }
    }

    private List<Point> getMinTapsPoints(List<Point> points) {
        List<Point> removePoints = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = points.size() - 1; j > i; j--) {
                if (points.get(i).equalPoints(points.get(j))) {
                    removePoints.add(points.get(i).getTap() > points.get(j).getTap()
                            ? points.get(i) : points.get(j));
                }
            }
        }
        points.removeAll(removePoints);
        return points;
    }

    private boolean canPass(Point point, Pipe pipe) {
        return (point.getHeight() < pipe.getUpperBound() && point.getHeight() > pipe.getLowerBound());
    }

    private boolean isPipeTime(List<Point> points, List<Pipe> pipes) {
        int time = points.get(0).getTime();
        for (Pipe pipe : pipes) {
            if (time == pipe.getTime()) {
                return true;
            }
        }
        return false;
    }

    private List<Point> filterPoint(List<Point> points, Pipe pipe) {
        List<Point> removePoints = new ArrayList<>();
        for (Point point : points) {
            if (!canPass(point, pipe)) {
                removePoints.add(point);
            }
        }
        points.removeAll(removePoints);
        return points;
    }

    private Pipe getCurrentPipe(List<Point> points, List<Pipe> pipes) {
        int time = points.get(0).getTime();
        for (Pipe pipe : pipes) {
            if (time == pipe.getTime()) {
                return pipe;
            }
        }
        return new Pipe();
    }

    private void saveOutput(String filename, int canPass, int minTap, int stopPipeIndex) throws IOException {
        try {
            FileWriter output = new FileWriter(filename + ".out");
            String ls = System.getProperty("line.separator");
            if (canPass == 1) {
                output.write(String.valueOf(canPass) + ls);
                output.write(String.valueOf(minTap) + ls);
            } else {
                output.write(String.valueOf(canPass) + ls);
                output.write(String.valueOf(stopPipeIndex) + ls);
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

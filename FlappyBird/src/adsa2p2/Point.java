package adsa2p2;

import java.util.Objects;

/**
 * @author Elizur
 */
public class Point {
    private int time;
    private int height;
    private int tap;

    public Point() {
        this.time = -1;
    }

    public Point(int time, int height) {
        this.time = time;
        this.height = height;
        this.tap = 0;
    }

    public int getTime() {
        return this.time;
    }

    public int getHeight() {
        return this.height;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTap(int tap) {
        this.tap = tap;
    }

    public int getTap() {
        return this.tap;
    }

    public boolean equalPoints (Point point) {
        return (this.getTime() == point.getTime() && this.getHeight() == point.getHeight());
    }
}

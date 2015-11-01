package adsa2p2;

/**
 * @author Elizur
 */
public class Pipe implements Comparable<Pipe> {
    private int lowerBound;
    private int upperBound;
    private int time;

    public Pipe() {
    }

    public Pipe(int time, int lowerBound, int upperBound) {
        this.time = time;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public int compareTo(Pipe pipe) {
        return this.getTime() - pipe.getTime();
    }
}

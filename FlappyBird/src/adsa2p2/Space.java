package adsa2p2;

/**
 * @author Elizur
 */
public class Space {
    private int m;
    private int n;
    private int pipeNumber;

    public Space() {
    }

    public Space(int m, int n, int pipeNumber) {
        this.m = m;
        this.n = n;
        this.pipeNumber = pipeNumber;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int getPipeNumber() {
        return pipeNumber;
    }
}

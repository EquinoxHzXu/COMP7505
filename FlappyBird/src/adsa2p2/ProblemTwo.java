package adsa2p2;

import java.io.IOException;

/**
 * @author Elizur
 */
public class ProblemTwo {
    public static void main(String[] args) throws IOException {
        FlappyBird flappyBird = new FlappyBird(args[0]);
        flappyBird.Fly();
    }
}

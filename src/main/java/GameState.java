import com.googlecode.lanterna.terminal.Terminal;


import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    private Frog frog;
    private Car car;

    public GameState() {
        this.frog = new Frog(new Position(50, 50), 'F');
        this.car = new Car(randomStartPosition(), 'C');
    }

    public Frog getFrog() {
        return frog;
    }

    public Car getCar() {
        return car;
    }

    public int getFrogX() {
        return frog.getPosition().getX();
    }

    public int getFrogY() {
        return frog.getPosition().getY();
    }

    public char getFrogModel() {
        return frog.getModel();
    }

    public int getCarX() {
        return car.getPosition().getX();
    }

    public int getCarY() {
        return car.getPosition().getY();
    }

    public char getCarModel() {
        return car.getModel();
    }


    // Returnerar true om grodan och bilen är på samma position

    public boolean collision() {

        return frog.getPosition() == car.getPosition();
    }

    public Position randomStartPosition() {
        int[] leftRight = {0, 100};
        int x = leftRight[ThreadLocalRandom.current().nextInt(2)];
        int y = ThreadLocalRandom.current().nextInt(9, 40);
        return new Position(x, y);
    }
}


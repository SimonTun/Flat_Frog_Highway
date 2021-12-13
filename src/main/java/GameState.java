import com.googlecode.lanterna.terminal.Terminal;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    private Frog frog;
    private Car car;
    private List<Car> cars;
    private final char clock = '\u23F2';     // Symbol vid kommande tidtagning (highscore)
    private int terminalWidth;
    private int terminalHeight;

    public GameState() {
        this.frog = new Frog(new Position(50, 50), '\uFE61');
        this.car = new Car(randomStartPosition(), '\u2180');
        this.cars = createCars();
    }

    public List<Car> getCars() {
        return cars;
    }

    public Frog getFrog() {
        return frog;
    }

    public void setFrog() {
        this.frog = frog;
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

    public void setTerminalWidth(int terminalWidth) {
        this.terminalWidth = terminalWidth;
    }

    public void setTerminalHeight(int terminalHeight) {
        this.terminalHeight = terminalHeight;
    }
// Returnerar true om grodan och bilen är på samma position

    public void hasCrashed(List<Car> cars) {

        frog.hasCrashed(cars);               // Borde fungera......
    }

    public boolean isAlive() {
        return frog.isAlive();
    }

        //      Gammal kod
//    public boolean collision() {
//        boolean hasCrashed = false;
//        if (this.frog.getPosition().getX() == this.car.getPosition().getX() &&
//            this.frog.getPosition().getY() == this.car.getPosition().getY()) {
//            hasCrashed = true;
//        }
//        return hasCrashed;
//    }

    // Returnerar randomiserad startposition för Car

    public Position randomStartPosition() {
        int[] leftRight = {1, 100};
        int x = leftRight[ThreadLocalRandom.current().nextInt(2)];
        int y = ThreadLocalRandom.current().nextInt(11, 40);
        return new Position(x, y);
    }
    // Returnerar randomiserad startriktning för Car

    public CarDirection randomStartDirection() {
        CarDirection[] leftRight = {CarDirection.LEFT, CarDirection.RIGHT};
        CarDirection x = leftRight[ThreadLocalRandom.current().nextInt(2)];
        return x;
    }

    public List<Car> createCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(randomStartPosition(),'D'));
        cars.add(new Car(randomStartPosition(),'E'));
        cars.add(new Car(randomStartPosition(),'F'));
        cars.add(new Car(randomStartPosition(),'G'));
        cars.add(new Car(randomStartPosition(),'H'));
        cars.add(new Car(randomStartPosition(),'I'));
        return cars;
    }
}

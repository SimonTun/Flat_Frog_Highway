import com.googlecode.lanterna.terminal.Terminal;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    private Frog frog;
    private Car car;
    private List<Car> cars;

    public GameState() {
        this.frog = new Frog(new Position(50, 50), 'F');
        this.car = new Car(randomStartPosition(), 'C');
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
    // Returnerar true om grodan och bilen är på samma position

    public boolean collision() {
        boolean hasCrashed = false;
        if (this.frog.getPosition().getX() == this.car.getPosition().getX() &&
            this.frog.getPosition().getY() == this.car.getPosition().getY()) {
            hasCrashed = true;
        }
        return hasCrashed;
    }

    public Position randomStartPosition() {
        int[] leftRight = {1, 100};
        int x = leftRight[ThreadLocalRandom.current().nextInt(2)];
        int y = ThreadLocalRandom.current().nextInt(11, 40);
        return new Position(x, y);
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
//


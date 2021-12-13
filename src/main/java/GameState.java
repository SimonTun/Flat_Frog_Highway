import com.googlecode.lanterna.terminal.Terminal;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    private Frog frog;
    private Car car;
    private List<Car> cars;
    private final char clock = '\u23F2';     // Symbol vid kommande tidtagning (highscore)
    private char model = '\u2180';

    public GameState() {
        this.frog = new Frog(new Position(50, 50), '\u26c7');
        this.car = new Car(randomStartDirection(), model);
        this.cars = createCars();
    }

    public List<Car> getCars() {
        return cars;
    }

    public Frog getFrog() {
        return frog;
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

    public void hasCrashed(List<Car> cars) {
        frog.hasCrashed(cars);
    }

    public boolean isAlive() {
        return !frog.isAlive();
    }

    public CarDirection randomStartDirection() {
        CarDirection[] leftRight = {CarDirection.LEFT, CarDirection.RIGHT};
        return leftRight[ThreadLocalRandom.current().nextInt(2)];
    }

    public List<Car> createCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(randomStartDirection(),'C'));
        cars.add(new Car(randomStartDirection(),'A'));
        cars.add(new Car(randomStartDirection(),'R'));
        cars.add(new Car(randomStartDirection(),'S'));
        cars.add(new Car(randomStartDirection(),'C'));
        cars.add(new Car(randomStartDirection(),'A'));
        cars.add(new Car(randomStartDirection(),'R'));
        cars.add(new Car(randomStartDirection(),'S'));
        cars.add(new Car(randomStartDirection(),'C'));
        cars.add(new Car(randomStartDirection(),'A'));
        cars.add(new Car(randomStartDirection(),'R'));
        cars.add(new Car(randomStartDirection(),'S'));
        cars.add(new Car(randomStartDirection(),'C'));
        cars.add(new Car(randomStartDirection(),'A'));
        cars.add(new Car(randomStartDirection(),'R'));
        cars.add(new Car(randomStartDirection(),'S'));
        return cars;
    }

    public void spawnAnotherCar(int indexNumber, Terminal terminal) throws IOException {      //indexNumber syftar på indexen för bilen vars rad vi vill skapa den nya bilen på. indexNumber = 0 "klonar" bil 'D', 1 klonar bil'E' etc
        List<Car> listCars = getCars();
        int rowNumber = listCars.get(indexNumber).getPosition().getY();
        CarDirection currentCarDir = listCars.get(indexNumber).getDirection();
        char currentModel = listCars.get(indexNumber).getModel();

        listCars.add(new Car(new Position(0,rowNumber),currentModel));

        int columNumber = listCars.get(indexNumber).getPosition().getX();

        if (currentCarDir == CarDirection.LEFT) {
            if (currentModel == 'C') {
                listCars.get(listCars.size() - 1).getPosition().setX(100);
                listCars.get(listCars.size() - 1).setDirection(CarDirection.LEFT);
            }
            else if (currentModel == 'A') {
                listCars.get(listCars.size() - 1).getPosition().setX(100);
                listCars.get(listCars.size() - 1).setDirection(CarDirection.LEFT);
            }
            else {
                listCars.get(listCars.size() - 1).getPosition().setX(100);
                listCars.get(listCars.size() - 1).setDirection(CarDirection.LEFT);
            }
        }
        else if (currentCarDir == CarDirection.RIGHT) {
            if (currentModel == 'C') {
                listCars.get(listCars.size() - 1).getPosition().setX(1);
                listCars.get(listCars.size() - 1).setDirection(CarDirection.RIGHT);
            }
            else if (currentModel == 'A') {
                listCars.get(listCars.size() - 1).getPosition().setX(1);
                listCars.get(listCars.size() - 1).setDirection(CarDirection.RIGHT);
            }
            else {
                listCars.get(listCars.size() - 1).getPosition().setX(1);
                listCars.get(listCars.size() - 1).setDirection(CarDirection.RIGHT);
            }
        }

        terminal.setCursorPosition(listCars.get(listCars.size()-1).getPosition().getX(),listCars.get(listCars.size()-1).getPosition().getY());
        terminal.putCharacter(currentModel);
        terminal.flush();
    }

}

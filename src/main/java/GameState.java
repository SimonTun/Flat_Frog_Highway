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
        cars.add(new Car(randomStartPosition(),'C'));
        cars.add(new Car(randomStartPosition(),'A'));
        cars.add(new Car(randomStartPosition(),'R'));
        cars.add(new Car(randomStartPosition(),'C'));
        cars.add(new Car(randomStartPosition(),'A'));
        cars.add(new Car(randomStartPosition(),'R'));
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

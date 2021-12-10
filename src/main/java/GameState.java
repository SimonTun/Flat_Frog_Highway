import com.googlecode.lanterna.terminal.Terminal;


import java.util.concurrent.ThreadLocalRandom;

public class GameState {
    private Frog frog;
    private Car car;

    public GameState() {
        this.frog = new Frog(new Position(50, 50), 'F');
        this.car = new Car(new Position(randomNum(), 25), 'C');
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

    // Returnerar randomiserad startposition för Car
    public int carStartPosition() {

//    CarDirection[] arr={CarDirection.RIGHT, CarDirection.LEFT};
//    Random r=new Random();
//    int randomNumber=r.nextInt(arr.length);
//    return (arr[randomNumber]);
        int[] numbers = {1, 100};
        return numbers[ThreadLocalRandom.current().nextInt(2)];

    }

    public int randomNum() {
        int[] numbers = {1, 100};
        return numbers[ThreadLocalRandom.current().nextInt(2)];
    }



}

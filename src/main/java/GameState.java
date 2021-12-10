import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GameState {
    private Frog frog;
    private Car car;

    public GameState () throws IOException {
       this.frog = new Frog(new Position(50, 50), 'F');
        this.car = new Car(new Position(25,100),'C');
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

    public int getFrogPrevX() {
        return frog.getPosition().getPreviousX();
    }

    public int getFrogY() {
        return frog.getPosition().getY();
    }

    public int getFrogPrevY() {
        return frog.getPosition().getPreviousY();
    }

    public Position frogPrevPos(){
        Position prevPosition = new Position(this.frog.getPosition().getPreviousX(), this.frog.getPosition().getPreviousY());
        return prevPosition;
    }

    public Position carPrevPos(){
        Position prevPosition = new Position(this.car.getPosition().getPreviousX(), this.car.getPosition().getPreviousY());
        return prevPosition;
    }

    public char getFrogModel() {
        return frog.getModel();
    }

    public int getCarX() {
        return car.getPosition().getX();
    }

    public int getCarPrevX() {
        return car.getPosition().getPreviousX();
    }

    public int getCarY() {
        return car.getPosition().getY();
    }

    public int getCarPrevY() {
        return car.getPosition().getPreviousY();
    }

    public char getCarModel() {
        return car.getModel();
    }





}

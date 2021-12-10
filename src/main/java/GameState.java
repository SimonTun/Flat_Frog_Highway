import com.googlecode.lanterna.terminal.Terminal;

public class GameState {
    private Frog frog;
    private Car car;

    public GameState () {
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



}

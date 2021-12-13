import java.util.concurrent.ThreadLocalRandom;

public class Car {
    private Position position;
    private Position prevPosition;
    private final char model;
    private CarDirection direction;

    public Car(Position position, char model) {
        this.position = position;
        this.model = model;

    }

    public Car(CarDirection direction, char model) {
        this.position = startPosition(direction);
        this.model = model;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(Position prevPosition) {
        this.prevPosition = prevPosition;
    }

    public char getModel() {
        return model;
    }

    public void setDirection(CarDirection direction) {
        this.direction = direction;
    }

    public CarDirection getDirection() {
        return this.direction;
    }

    public Position startPosition(CarDirection direction) {

        if (direction.equals(CarDirection.RIGHT)) {
            return new Position(100, ThreadLocalRandom.current().nextInt(11, 24));

        }
        return new Position(1,ThreadLocalRandom.current().nextInt(26, 39));



    }
}

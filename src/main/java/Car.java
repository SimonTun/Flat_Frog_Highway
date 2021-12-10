public class Car {
    private Position position;
    private Position prevPosition;
    private char model;
    private CarDirection direction;

    public Car(Position position, char model) {
        this.position = position;
        this.model = model;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = this.position;
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
}

public class Car {
    private Position position;
    private Position prevPosition;
    private char model;
    private final CarDirection direction;

    public Car(Position position, char model) {
        this.position = position;
        this.model = model;
        this.direction = setDirection();
    }

    public Position getPosition() {
        return position;
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

    public void setModel(char model) {
        this.model = model;
    }

    public CarDirection setDirection() {
        if (this.position.getY() == 100) {
            return CarDirection.LEFT;
        }
        return CarDirection.RIGHT;
    }

    public CarDirection getDirection() {
        return this.direction;
    }


}

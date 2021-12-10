public class Car {
    private Position position;
    private Position prevPosition;
    private char model;
    private CarDirection direction;

    public Car(Position position, char model) {
        this.position = position;
        this.model = model;
        setDirection();
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

    public void setModel(char model) {
        this.model = model;
    }

    public void setDirection() {
        if (this.position.getY() == 100) {
            this.direction = CarDirection.LEFT;
        }
        this.direction = CarDirection.RIGHT;
    }

    public CarDirection getDirection() {
        return this.direction;
    }
}

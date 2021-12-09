public class Car {
    private Position position;
    private char model;

    public Car(Position position, char model) {
        this.position = position;
        this.model = model;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public char getModel() {
        return model;
    }

    public void setModel(char model) {
        this.model = model;
    }
}

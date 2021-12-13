import java.util.List;

public class Frog {
    private Position position;
    private Position prevPosition;
    private char model;
    private boolean isAlive;

    public Frog(Position position, char model) {
        this.position = position;
        this.model = model;
        this.isAlive = true;
    }

    public Position getPosition() {
        return position;
    }

    public Position getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(Position position) {
        this.prevPosition = position;
    }

    public char getModel() {
        return model;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean hasReachedGoal() {
        if (position.getY() < 10)
            return true;
        return false;
    }

    public void hasCrashed(List<Car> cars) {
        Position frogPos = getPosition();
        for (Car car : cars) {
            if (frogPos.equals(car.getPosition())) {
                isAlive = false;
                break;
            }
        }
    }

    public void moveUp() {
        int currentFrogY = this.position.getY();
        position.setY(currentFrogY - 1);
    }

    public void moveDown() {
        int currentFrogY = this.position.getY();
        position.setY(currentFrogY + 1);
    }

    public void moveRight() {
        int currentFrogX = this.position.getX();
        position.setX(currentFrogX + 1);
    }

    public void moveLeft() {
        int currentFrogX = this.position.getX();
        position.setX(currentFrogX - 1);
    }
}

public class Frog {
    private Position position;
    private Position prevPosition;
    private char model;

    public Frog(Position position, char model) {
        this.position = position;
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

    public void setPrevPosition(Position position) {
        this.prevPosition = position;
    }

    public char getModel() {
        return model;
    }

    public void setModel(char model) {
        this.model = model;
    }

    public boolean hasReachedGoal() {
        if (position.getY() == 10)
            return true;
        return false;
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


    public void setPrevPosition() {
        this.prevPosition = prevPosition;
    }
}

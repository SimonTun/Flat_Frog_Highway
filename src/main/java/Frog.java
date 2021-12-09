public class Frog {
    private Position position;
    private char model;

    public Frog(Position position, char model) {
        this.position = position;
        this.model = model;
    }

//    public int getX() {
//        return position.getX();
//    }
//    public int getY() {
//        return position.getY();
//            }
//    public void setX(int x) {
//        position.setX(x);
//    }
//    public void setY(int y) {
//        position.setY(position.getY()-y);
//    }

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


}

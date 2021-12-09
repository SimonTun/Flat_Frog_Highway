public class Frog {

    public Position position;
    private char model;

    public Frog(Position position, char model) {
        this.position = position;
        this.model = model;
    }

    public int getX() {
        return position.getX();
    }
    public int getY() {
        return position.getY();
            }
    public void setX(int x) {
        position.setX(x);
    }
    public void setY(int y) {
        position.setY(position.getY()-y);
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

    public boolean hasReachedGoal() {

//        if ()


        return true;
    }


}

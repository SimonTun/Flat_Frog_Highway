public class Frog {
    private int y;
    private int x;
    private char model;

    public Frog(int y, int x, char model) {
        this.y = y;
        this.x = x;
        this.model = model;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getModel() {
        return model;
    }

    public void setModel(char model) {
        this.model = model;
    }
}

package game;

public class Vektor {
    public int direction;
    public int x;
    public int y;
    final static public int RIGHT = 0;
    final static public int RIGHT_DOWN = 1;
    final static public int DOWN = 2;
    final static public int LEFT_DOWN = 3;
    final static public int LEFT = 4;
    final static public int LEFT_UP = 5;
    final static public int UP = 6;
    final static public int RIGHT_UP = 7;

    public Vektor(int direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public Vektor getOppositeVektorInstance() {
        return new Vektor((direction + 4) % 8, x * -1, y * -1);
    }
}

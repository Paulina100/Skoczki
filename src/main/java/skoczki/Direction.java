package skoczki;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Vector2d stepVector(){
        return switch (this) {
            case UP -> new Vector2d(0, 1);
            case RIGHT -> new Vector2d(1, 0);
            case DOWN -> new Vector2d(0, -1);
            case LEFT -> new Vector2d(-1, 0);
        };
    }

    public Vector2d jumpVector(){
        return switch (this) {
            case UP -> new Vector2d(0, 2);
            case RIGHT -> new Vector2d(2, 0);
            case DOWN -> new Vector2d(0, -2);
            case LEFT -> new Vector2d(-2, 0);
        };
    }
}

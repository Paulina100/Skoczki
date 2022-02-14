package skoczki;

public class Pawn {
    private final Color color;
    private Position position;
    private final Map map;

    public Pawn (Color color, Position position, Map map){
        this.color = color;
        this.position = position;
        this.map = map;
    }

    public Color getColor() {
        return color;
    }

    private void positionChanged(Position oldPosition, Position newPosition){
        map.positionChanged(oldPosition, newPosition);
    }

    public void move(Position newPosition){
        Position oldPosition = this.position;
        if (oldPosition.equals(newPosition)) throw new IllegalArgumentException("You didn't make a move!");
        this.position = newPosition;
        positionChanged(oldPosition, newPosition);
    }

}

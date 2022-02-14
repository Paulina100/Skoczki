package skoczki;

import java.util.Objects;

import static java.lang.Math.abs;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean stepDistance(Position other){
        return ((this.y == other.y && abs(this.x - other.x) == 1) || (this.x == other.x && abs(this.y - other.y) == 1));
    }

    public boolean jumpDistance(Position other){
        return ((this.y == other.y && abs(this.x - other.x) == 2) || (this.x == other.x && abs(this.y - other.y) == 2));
    }

    public boolean isAtFinish(Color color){
        if (color.equals(Color.WHITE))return this.y == 0 || this.y == 1;
        return this.y == 6 || this.y == 7;
    }

    public Position between(Position other){
        return new Position((this.x + other.x)/2, (this.y + other.y)/2);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Position))
            return false;
        Position that = (Position) other;

        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}

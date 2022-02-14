package skoczki;

import java.util.Objects;

import static java.lang.Math.abs;

public class Vector2d {
    private final int x;
    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean stepDistance(Vector2d other){
        return ((this.y == other.y && abs(this.x - other.x) == 1) || (this.x == this.y && abs(this.y - other.y) == 1));
    }

    public boolean jumpDistance(Vector2d other){
        return ((this.y == other.y && abs(this.x - other.x) == 2) || (this.x == this.y && abs(this.y - other.y) == 2));
    }

    public Vector2d between(Vector2d other){
        return new Vector2d((this.x + other.x)/2, (this.y + other.y)/2);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;

        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}

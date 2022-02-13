package skoczki;

import java.util.HashMap;

import static skoczki.Color.BLACK;
import static skoczki.Color.WHITE;

public class Map {
    // nie wiem czy static
    private final Vector2d upperLeftBoundary = new Vector2d(0, 0);
    private final Vector2d lowerRightBoundary = new Vector2d(7, 7);
    private java.util.Map<Vector2d, Pawn> pawns = new HashMap<>();


    public Map (){
        initMap();
    }

    private void initMap(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                Vector2d blackPosiiton = new Vector2d(j, i);
                Vector2d whitePosiiton = new Vector2d(7 - j, i);

                pawns.put(blackPosiiton, new Pawn(BLACK, blackPosiiton, this));
                pawns.put(whitePosiiton, new Pawn(WHITE, whitePosiiton, this));
            }
        }
    }

    public Pawn pawnAt(Vector2d position) {
        return pawns.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return pawnAt(position) != null;
    }

    private boolean isOnMap(Vector2d position){
        return position.follows(upperLeftBoundary) && position.precedes(lowerRightBoundary);
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position) && isOnMap(position) ;
    }

    public Vector2d getUpperLeftBoundary() {
        return upperLeftBoundary;
    }
    public Vector2d getLowerRightBoundary() {
        return lowerRightBoundary;
    }
}

package skoczki;

import skoczki.GUI.MoveType;

import java.util.HashMap;

import static java.lang.Math.abs;
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

//    private boolean isOnMap(Vector2d position){
//        return position.follows(upperLeftBoundary) && position.precedes(lowerRightBoundary);
//    }

    public void canStepTo(Vector2d oldPosition, Vector2d newPosition){
        if (isOccupied(newPosition)) throw new IllegalArgumentException("This position is already occupied!");
        if(oldPosition.stepDistance(newPosition)) return;
        throw new IllegalArgumentException("This position is too far!");
    }

    public void canJumpTo(Vector2d oldPosition, Vector2d newPosition){
        if (isOccupied(newPosition)) throw new IllegalArgumentException("This position is already occupied!");
        if (oldPosition.jumpDistance(newPosition) && isOccupied(oldPosition.between(newPosition))) return;

        throw new IllegalArgumentException("This position is too far!");
    }

    public MoveType canMoveTo(Vector2d oldPositon, Vector2d newPosition)
    {
        if (isOccupied(newPosition)) throw new IllegalArgumentException("This move is not allowed!");
        if(oldPositon.stepDistance(newPosition)) return MoveType.STEP;
        if (oldPositon.jumpDistance(newPosition) && isOccupied(oldPositon.between(newPosition))) return MoveType.JUMP;

        throw new IllegalArgumentException("This move is not allowed!");
    }

    public Vector2d getUpperLeftBoundary() {
        return upperLeftBoundary;
    }
    public Vector2d getLowerRightBoundary() {
        return lowerRightBoundary;
    }
}

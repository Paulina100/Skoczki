package skoczki;

import java.util.HashMap;

import static skoczki.Color.BLACK;
import static skoczki.Color.WHITE;

public class Map {
    private final java.util.Map<Position, Pawn> pawns = new HashMap<>();

    public Map (){
        initMap();
    }

    private void initMap(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                Position blackPosition = new Position(i, j);
                Position whitePosition = new Position(i, 7 - j);

                pawns.put(blackPosition, new Pawn(BLACK, blackPosition, this));
                pawns.put(whitePosition, new Pawn(WHITE, whitePosition, this));
            }
        }
    }

    public Pawn pawnAt(Position position) {
        return pawns.get(position);
    }

    public boolean isOccupied(Position position) {
        return pawnAt(position) != null;
    }

    public MoveType canMoveTo(Position oldPosition, Position newPosition)
    {
        if (!isOccupied(newPosition)) {
            if (oldPosition.stepDistance(newPosition)) return MoveType.STEP;
            if (oldPosition.jumpDistance(newPosition) && isOccupied(oldPosition.between(newPosition))) return MoveType.JUMP;
        }
        throw new IllegalArgumentException("This move is not allowed!");
    }

    public void positionChanged(Position oldPosition, Position newPosition){
        Pawn pawn = pawns.remove(oldPosition);

        pawns.put(newPosition, pawn);
    }

}

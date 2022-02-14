package skoczki;

public class Pawn {
    private final Color color;
    private Vector2d position;
    private Map map;

    public Pawn (Color color, Vector2d position, Map map){
        this.color = color;
        this.position = position;
        this.map = map;
    }

    public Color getColor() {
        return color;
    }

//    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
//        for (IPositionChangeObserver observer: observers) {
//            observer.positionChanged(oldPosition, newPosition);
//        }
//    }

    public void move(Direction direction){

    }

//    public void step(Direction direction) {
//        Vector2d newPosition = this.position.add(direction.stepVector());
//        Vector2d oldPosition = this.position;
//        if (map.canMoveTo(oldPosition, newPosition)) {
//            this.position = newPosition;
////            positionChanged(oldPosition, newPosition);
//        }
////        else{
////            jump(direction);
////        }
//    }
//
//    public void jump(Direction direction){
//        Vector2d newPosition = this.position.add(direction.jumpVector());
//        Vector2d oldPosition = this.position;
//        if (map.canMoveTo(oldPosition, newPosition)) {
//            this.position = newPosition;
////            positionChanged(oldPosition, newPosition);
//        }
//    }

}

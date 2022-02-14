package skoczki;

import skoczki.GUI.App;

public class WinningConditions {

    private int whitePawnsAtFinish = 0;
    private int blackPawnsAtFinish = 0;
    private final App app;

    public WinningConditions(App app){
        this.app = app;
    }

    public boolean checkForWin(Color color, Vector2d oldPosition, Vector2d newPosition){
        if(!oldPosition.isAtFinish(color) && newPosition.isAtFinish(color)){
            if (color.equals(Color.WHITE)) whitePawnsAtFinish += 1 ;
            else blackPawnsAtFinish += 1;
        }

//        return whitePawnsAtFinish == 1 || blackPawnsAtFinish == 1;
        return whitePawnsAtFinish == 16 || blackPawnsAtFinish == 16;
    }

}

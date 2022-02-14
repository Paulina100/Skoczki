package skoczki;


import javax.swing.*;

public class WinningConditions {
    private int whitePawnsAtFinish = 0;
    private int blackPawnsAtFinish = 0;

    public void checkForWin(Color color, Position oldPosition, Position newPosition){
        if(!oldPosition.isAtFinish(color) && newPosition.isAtFinish(color)){
            switch (color){
                case WHITE -> whitePawnsAtFinish += 1;
                case BLACK -> blackPawnsAtFinish += 1;
            }
        }
        else if(oldPosition.isAtFinish(color) && !newPosition.isAtFinish(color)){
            switch (color){
                case WHITE -> whitePawnsAtFinish -= 1;
                case BLACK -> blackPawnsAtFinish -= 1;
            }
        }

        if (whitePawnsAtFinish == 16 || blackPawnsAtFinish == 16){
            JOptionPane.showMessageDialog(null,
                    color + " wins!", "Congratulations",
                    JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
    }

}

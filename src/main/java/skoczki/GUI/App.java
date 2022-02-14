package skoczki.GUI;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import skoczki.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class App extends Application{
    private final Map map = new Map();
    private final WinningConditions winningConditions = new WinningConditions(this);
    private final GameEngine engine = new GameEngine(map, this);
    private final GridPane gridPane = new GridPane();
    private final Label label = new Label();
    private final Button startButton = new Button("Start");
    private final VBox infoAndButton = new VBox(label);
    private final HBox boardAndInfo = new HBox(gridPane, infoAndButton);

    private final CreatedImages createdImages = new CreatedImages();
    private final ButtonCreator buttonCreator = new ButtonCreator(map);


//    private void startEngine(){
//        if (!engineThread.isAlive()) {
//            engineThread = new Thread(engine);
//            engineThread.start();
//        }
//    }

    @Override
    public void start(Stage primaryStage) {
        this.choosePawn(Color.WHITE);

        Scene scene = new Scene(boardAndInfo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void makeJump(List<Vector2d> prevJumps){
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();


        Vector2d currentPosition = prevJumps.get(prevJumps.size() - 1);
        prevJumps.add(currentPosition);

//        try {
            ColumnConstraints columnWidth = new ColumnConstraints(80);
            RowConstraints rowHeight = new RowConstraints(80);
            rowHeight.setValignment(VPos.CENTER);

            for (int i = 0; i < 8; i++) {
                gridPane.getRowConstraints().add(rowHeight);
            }
            for (int i = 0; i < 8; i++) {
                gridPane.getColumnConstraints().add(columnWidth);
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Vector2d position = new Vector2d(i, j);
                    Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
                    if(prevJumps.contains(position)){
                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                    }

                    button.setContentDisplay(GRAPHIC_ONLY);
                    button.setMinSize(80, 80);
                    button.setOnAction(event -> {
                        try {
//                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                            if(map.canMoveTo(currentPosition, position).equals(MoveType.JUMP)) {
                                prevJumps.add(position);
                                makeJump(prevJumps);
                            }
                            else{
                                throw new IllegalArgumentException("This move is not allowed!");
                            }

                        } catch (IllegalArgumentException ex) {
                            out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null,
                                    ex.getMessage());
                            makeJump(prevJumps);
    //            System.exit(1);
                        }


                    });
                    gridPane.add(button, i, j);

                }
            }


            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);


//        } catch (IllegalArgumentException ex) {
//            out.println(ex.getMessage());
//            JOptionPane.showMessageDialog(null,
//                    ex.getMessage());
//            makeJump(prevJumps);
////            System.exit(1);
//        }
    }

    private void makeStep(List<Vector2d> prevJumps){
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();


//        try {
            ColumnConstraints columnWidth = new ColumnConstraints(80);
            RowConstraints rowHeight = new RowConstraints(80);
            rowHeight.setValignment(VPos.CENTER);

            Vector2d currentPosition = prevJumps.get(prevJumps.size() - 1);
            prevJumps.add(currentPosition);

            for (int i = 0; i < 8; i++) {
                gridPane.getRowConstraints().add(rowHeight);
            }
            for (int i = 0; i < 8; i++) {
                gridPane.getColumnConstraints().add(columnWidth);
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Vector2d position = new Vector2d(i, j);
                    Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
                    if(currentPosition.equals(position)){
                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                    }

                    button.setContentDisplay(GRAPHIC_ONLY);
                    button.setMinSize(80, 80);
                    button.setOnAction(event -> {
                        try {
                            if(map.canMoveTo(currentPosition, position) == MoveType.STEP) {
                                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                                prevJumps.add(position);
                            }
                            else{
                                prevJumps.add(position);
                                makeJump(prevJumps);
                            }

                        }catch (IllegalArgumentException ex){
                            out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null,
                                    ex.getMessage());
                        }

                    });
                    gridPane.add(button, i, j);

                }
            }


            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);


//        } catch (IllegalArgumentException ex) {
////            out.println(ex.getMessage());
////            JOptionPane.showMessageDialog(null,
////                    ex.getMessage());
////            List<Vector2d> jumps = new ArrayList<>();
////            jumps.add(currentPosition);
////            makeJump(jumps);
////            System.exit(1);
//        }
    }

    public void makeMove(Vector2d pawnPosition){
        infoAndButton.getChildren().add(startButton);
        Pawn chosenPawn = map.pawnAt(pawnPosition);
        List<Vector2d> jumps = new ArrayList<>();
        jumps.add(pawnPosition);
        startButton.setOnAction(event -> {
            try {
                Vector2d newPosition = jumps.get(jumps.size() - 1);
                chosenPawn.move(newPosition);
                infoAndButton.getChildren().remove(startButton);

                if (winningConditions.checkForWin(chosenPawn.getColor(), pawnPosition, newPosition)){
                    JOptionPane.showMessageDialog(null,
                            chosenPawn.getColor() + " wins!", "Congratulations",
                            JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }

                choosePawn(chosenPawn.getColor().opposite());
            }catch (IllegalArgumentException ex){
                out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        ex.getMessage());
            }
        });


//        List<Vector2d> jumps = new ArrayList<>();
//        jumps.add(pawnPosition);
//        makeJump(jumps);

        makeStep(jumps);

    }

    public void choosePawn(Color playerColor) {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        try {
            ColumnConstraints columnWidth = new ColumnConstraints(80);
            RowConstraints rowHeight = new RowConstraints(80);
            rowHeight.setValignment(VPos.CENTER);

            label.setText(playerColor + "'s turn!");

            for (int i = 0; i < 8; i++) {
                gridPane.getRowConstraints().add(rowHeight);
            }
            for (int i = 0; i < 8; i++) {
                gridPane.getColumnConstraints().add(columnWidth);
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Vector2d position = new Vector2d(i, j);
                    if (map.isOccupied(position)) {
                        if (map.pawnAt(position).getColor().equals(playerColor)){
                            Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
                            button.setContentDisplay(GRAPHIC_ONLY);
                            button.setMinSize(80, 80);
                            button.setOnAction(event -> {
//                                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                                makeMove(position);
                                //choosePawn(playerColor.opposite());
                            });
                            gridPane.add(button, i, j);
                        }
                        else{
                            gridPane.add(createdImages.getImageView(map.pawnAt(position)), i, j);
                        }
                    }
                }
            }


            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);


        } catch (IllegalArgumentException ex) {
            out.println(ex.getMessage());
            System.exit(1);
        }
    }

    // zrobić z tego klasę
    private Button createButton(Vector2d position){
        Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
        button.setContentDisplay(GRAPHIC_ONLY);
        button.setMinSize(80, 80);
        button.setOnAction(event -> {
            button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
        });
        return button;
    }


}

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.System.out;
import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class App extends Application{
    private final Map map = new Map();
    private final WinningConditions winningConditions = new WinningConditions();
    private final GameEngine engine = new GameEngine(map, this);
    private final GridPane gridPane = new GridPane();
    private final Label label = new Label();
    private final Button startButton = new Button("Start");
    private final VBox infoAndButton = new VBox(label);
    private final HBox boardAndInfo = new HBox(gridPane, infoAndButton);

    private final CreatedImages createdImages = new CreatedImages();
    private final java.util.Map<Position, Button> buttons = new HashMap<>();
    private final ButtonCreator buttonCreator = new ButtonCreator(map);


    @Override
    public void init() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);

                Button button = buttonCreator.createButton(position);
                buttons.put(position, button);
                gridPane.add(button, i, j);
            }
        }
        gridPane.setGridLinesVisible(true);
    }

    @Override
    public void start(Stage primaryStage) {
        this.choosePawn(Color.WHITE);

        Scene scene = new Scene(boardAndInfo);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void choosePawn(Color playerColor) {
        label.setText(playerColor + "'s turn!");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                    
                if (map.isOccupied(position) && map.pawnAt(position).getColor().equals(playerColor)){
                    buttons.get(position).setOnAction(event -> {
                        makeMove(position);
                    });
                }
                else{
                    buttons.get(position).setOnAction(event -> {
                    });
                }
            }
        }

    }

    public void makeMove(Position pawnPosition){
        infoAndButton.getChildren().add(startButton);
        Pawn chosenPawn = map.pawnAt(pawnPosition);
        List<Position> jumps = new ArrayList<>();
        jumps.add(pawnPosition);
        startButton.setOnAction(event -> {
            try {
                Position newPosition = jumps.get(jumps.size() - 1);
                chosenPawn.move(newPosition);
                clearClicked(jumps);
                buttons.get(newPosition).setGraphic(createdImages.getImageView(map.pawnAt(newPosition)));
                winningConditions.checkForWin(chosenPawn.getColor(), pawnPosition, newPosition);

                infoAndButton.getChildren().remove(startButton);
                choosePawn(chosenPawn.getColor().opposite());

            }catch (IllegalArgumentException ex){
                out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        ex.getMessage());
            }
        });

        makeStep(jumps);

    }

    private void makeJump(List<Position> prevJumps){
        Position currentPosition = prevJumps.get(prevJumps.size() - 1);
        prevJumps.add(currentPosition);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                Button button = buttons.get(position);

                if(prevJumps.contains(position)){
                    button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                }

                button.setOnAction(event -> {
                    try {
                        if(map.canMoveTo(currentPosition, position).equals(MoveType.JUMP)
                                && !prevJumps.contains(position)) {
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
                    }
                });
            }
        }

    }

    private void makeStep(List<Position> prevJumps){
        Position currentPosition = prevJumps.get(prevJumps.size() - 1);
        prevJumps.add(currentPosition);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                Button button = buttons.get(position);

                if(currentPosition.equals(position)){
                    button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                }

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
            }
        }

    }
    
    private void clearClicked(List<Position> jumps){
        for (Position position : jumps) {
            buttons.get(position).setGraphic(createdImages.getImageView(map.pawnAt(position)));
        }
    }

//    private void makeJump(List<Position> prevJumps){
//        gridPane.getChildren().clear();
//        gridPane.getRowConstraints().clear();
//        gridPane.getColumnConstraints().clear();
//
//
//        Position currentPosition = prevJumps.get(prevJumps.size() - 1);
//        prevJumps.add(currentPosition);
//
////        try {
//            ColumnConstraints columnWidth = new ColumnConstraints(80);
//            RowConstraints rowHeight = new RowConstraints(80);
//            rowHeight.setValignment(VPos.CENTER);
//
//            for (int i = 0; i < 8; i++) {
//                gridPane.getRowConstraints().add(rowHeight);
//            }
//            for (int i = 0; i < 8; i++) {
//                gridPane.getColumnConstraints().add(columnWidth);
//            }
//
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    Position position = new Position(i, j);
//                    Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
//                    if(prevJumps.contains(position)){
//                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
//                    }
//
//                    button.setContentDisplay(GRAPHIC_ONLY);
//                    button.setMinSize(80, 80);
//                    button.setOnAction(event -> {
//                        try {
////                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
//                            if(map.canMoveTo(currentPosition, position).equals(MoveType.JUMP)
//                            && !prevJumps.contains(position)) {
//                                prevJumps.add(position);
//                                makeJump(prevJumps);
//                            }
//                            else{
//                                throw new IllegalArgumentException("This move is not allowed!");
//                            }
//
//                        } catch (IllegalArgumentException ex) {
//                            out.println(ex.getMessage());
//                            JOptionPane.showMessageDialog(null,
//                                    ex.getMessage());
//                            makeJump(prevJumps);
//    //            System.exit(1);
//                        }
//
//
//                    });
//                    gridPane.add(button, i, j);
//
//                }
//            }
//
//
//            gridPane.setGridLinesVisible(false);
//            gridPane.setGridLinesVisible(true);
//
//
////        } catch (IllegalArgumentException ex) {
////            out.println(ex.getMessage());
////            JOptionPane.showMessageDialog(null,
////                    ex.getMessage());
////            makeJump(prevJumps);
//////            System.exit(1);
////        }
//    }
//
//    private void makeStep(List<Position> prevJumps){
//        gridPane.getChildren().clear();
//        gridPane.getRowConstraints().clear();
//        gridPane.getColumnConstraints().clear();
//
//
////        try {
//            ColumnConstraints columnWidth = new ColumnConstraints(80);
//            RowConstraints rowHeight = new RowConstraints(80);
//            rowHeight.setValignment(VPos.CENTER);
//
//            Position currentPosition = prevJumps.get(prevJumps.size() - 1);
//            prevJumps.add(currentPosition);
//
//            for (int i = 0; i < 8; i++) {
//                gridPane.getRowConstraints().add(rowHeight);
//            }
//            for (int i = 0; i < 8; i++) {
//                gridPane.getColumnConstraints().add(columnWidth);
//            }
//
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    Position position = new Position(i, j);
//                    Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
//                    if(currentPosition.equals(position)){
//                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
//                    }
//
//                    button.setContentDisplay(GRAPHIC_ONLY);
//                    button.setMinSize(80, 80);
//                    button.setOnAction(event -> {
//                        try {
//                            if(map.canMoveTo(currentPosition, position) == MoveType.STEP) {
//                                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
//                                prevJumps.add(position);
//                            }
//                            else{
//                                prevJumps.add(position);
//                                makeJump(prevJumps);
//                            }
//
//                        }catch (IllegalArgumentException ex){
//                            out.println(ex.getMessage());
//                            JOptionPane.showMessageDialog(null,
//                                    ex.getMessage());
//                        }
//
//                    });
//                    gridPane.add(button, i, j);
//
//                }
//            }
//
//
//            gridPane.setGridLinesVisible(false);
//            gridPane.setGridLinesVisible(true);
//
//
////        } catch (IllegalArgumentException ex) {
//////            out.println(ex.getMessage());
//////            JOptionPane.showMessageDialog(null,
//////                    ex.getMessage());
//////            List<Vector2d> jumps = new ArrayList<>();
//////            jumps.add(currentPosition);
//////            makeJump(jumps);
//////            System.exit(1);
////        }
//    }


//    public void choosePawn(Color playerColor) {
//        gridPane.getChildren().clear();
//        gridPane.getRowConstraints().clear();
//        gridPane.getColumnConstraints().clear();
//
//        try {
////            ColumnConstraints columnWidth = new ColumnConstraints(80);
////            RowConstraints rowHeight = new RowConstraints(80);
////            rowHeight.setValignment(VPos.CENTER);
//
//            label.setText(playerColor + "'s turn!");
//
////            for (int i = 0; i < 8; i++) {
////                gridPane.getRowConstraints().add(rowHeight);
////            }
////            for (int i = 0; i < 8; i++) {
////                gridPane.getColumnConstraints().add(columnWidth);
////            }
//
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    Position position = new Position(i, j);
//                    if (map.isOccupied(position)) {
//                        if (map.pawnAt(position).getColor().equals(playerColor)){
//                            Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
//                            button.setContentDisplay(GRAPHIC_ONLY);
//                            button.setMinSize(80, 80);
//                            button.setOnAction(event -> {
////                                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
//                                makeMove(position);
//                                //choosePawn(playerColor.opposite());
//                            });
//                            gridPane.add(button, i, j);
//                        }
//                        else{
//                            gridPane.add(createdImages.getImageView(map.pawnAt(position)), i, j);
//                        }
//                    }
//                }
//            }
//
//
//            gridPane.setGridLinesVisible(false);
//            gridPane.setGridLinesVisible(true);
//
//
//        } catch (IllegalArgumentException ex) {
//            out.println(ex.getMessage());
//            System.exit(1);
//        }
//    }
//
//    // zrobić z tego klasę
//    private Button createButton(Position position){
//        Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
//        button.setContentDisplay(GRAPHIC_ONLY);
//        button.setMinSize(80, 80);
//        button.setOnAction(event -> {
//            button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
//        });
//        return button;
//    }


}

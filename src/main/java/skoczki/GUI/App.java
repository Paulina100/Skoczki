package skoczki.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import skoczki.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static java.lang.System.out;


public class App extends Application{
    private final Map map = new Map();
    private final WinningConditions winningConditions = new WinningConditions();
    private final GridPane gridPane = new GridPane();
    private final Label label = new Label();
    private final Button startButton = new Button("Start");
    private final VBox infoAndButton = new VBox(label);
    private final HBox boardAndInfo = new HBox(gridPane, infoAndButton);

    private final CreatedImages createdImages = new CreatedImages();
    private final List<Position> fields = new ArrayList<>();
    private final ButtonCreator buttonCreator = new ButtonCreator(map);
    private final java.util.Map<Position, Button> buttons = new HashMap<>();


    @Override
    public void init() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                Button button = buttonCreator.createButton(position);

                fields.add(position);
                buttons.put(position, button);
                gridPane.add(button, i, j);
            }
        }

        label.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        infoAndButton.setAlignment(Pos.CENTER);
        infoAndButton.setMinWidth(200);

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

        for (Position position: fields) {
            if (map.isOccupied(position) && map.pawnAt(position).getColor().equals(playerColor)){
                buttons.get(position).setOnAction(event -> makeMove(position));
            }
            else{
                buttons.get(position).setOnAction(event -> {
                });
            }
        }

    }

    public void makeMove(Position pawnPosition){
        label.setText("Make a move!");
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

        for (Position position: fields) {
            Button button = buttons.get(position);

            if (prevJumps.contains(position)) {
                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
            }

            button.setOnAction(event -> {
                try {
                    if (map.canMoveTo(currentPosition, position).equals(MoveType.JUMP)
                            && !prevJumps.contains(position)) {
                        prevJumps.add(position);
                        makeJump(prevJumps);
                    } else {
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

    private void makeStep(List<Position> prevJumps){
        Position currentPosition = prevJumps.get(prevJumps.size() - 1);
        prevJumps.add(currentPosition);

        for (Position position: fields) {
            Button button = buttons.get(position);

            if (currentPosition.equals(position)) {
                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
            }

            button.setOnAction(event -> {
                try {
                    if (map.canMoveTo(currentPosition, position) == MoveType.STEP) {
                        button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                        prevJumps.add(position);
                    } else {
                        prevJumps.add(position);
                        makeJump(prevJumps);
                    }

                } catch (IllegalArgumentException ex) {
                    out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }
            });
        }

    }
    
    private void clearClicked(List<Position> jumps){
        for (Position position : jumps) {
            buttons.get(position).setGraphic(createdImages.getImageView(map.pawnAt(position)));
        }
    }

}

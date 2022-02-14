package skoczki.GUI;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import skoczki.*;

import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class App extends Application{
    private final Map map = new Map();
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

    public void makeMove(Pawn chosenPawn){
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();



    }

    public void choosePawn(Color playerColor) {
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        try {
            ColumnConstraints columnWidth = new ColumnConstraints(80);
            RowConstraints rowHeight = new RowConstraints(80);
            rowHeight.setValignment(VPos.CENTER);

            if (playerColor == Color.BLACK){
                label.setText("Blacks turn!");
                infoAndButton.getChildren().add(startButton);
            }
            else{
                label.setText("Whites turn!");
                infoAndButton.getChildren().remove(startButton);

            }

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
                                button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position)));
                                choosePawn(playerColor.opposite());
                            });
                            gridPane.add(button, j, i);
                        }
                        else{
                            gridPane.add(createdImages.getImageView(map.pawnAt(position)), j, i);
                        }
                    }
                }
            }


            gridPane.setGridLinesVisible(false);
            gridPane.setGridLinesVisible(true);


        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
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

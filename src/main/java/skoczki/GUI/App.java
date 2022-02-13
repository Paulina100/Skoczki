package skoczki.GUI;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import skoczki.Color;
import skoczki.GameEngine;
import skoczki.Map;
import skoczki.Vector2d;

import static java.lang.System.exit;
import static java.lang.System.out;
import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class App extends Application{
    private final FieldImage fieldImage = new FieldImage();
    private final Map map = new Map();
    private final GameEngine engine = new GameEngine(map, this);


//    private void startEngine(){
//        if (!engineThread.isAlive()) {
//            engineThread = new Thread(engine);
//            engineThread.start();
//        }
//    }

    @Override
    public void start(Stage primaryStage) {
        try {
            GridPane gridPane = new GridPane();
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
                    if (map.isOccupied(position)) {
                        Button button = new Button("", fieldImage.getImageView(map.pawnAt(position)));
                        button.setContentDisplay(GRAPHIC_ONLY);
                        button.setMinSize(80,80);
                        button.setOnAction(event -> {
                            System.out.println("klik");
                            button.setGraphic(fieldImage.getClicedImageView(map.pawnAt(position)));
                        });

                        gridPane.add(button, j, i);
                    }
                }
            }


            gridPane.setGridLinesVisible(true);

            Scene scene = new Scene(gridPane);
            primaryStage.setScene(scene);

            primaryStage.show();

        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}

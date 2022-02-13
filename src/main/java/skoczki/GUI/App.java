package skoczki.GUI;

import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import skoczki.Map;
import skoczki.Vector2d;
import javafx.scene.layout.VBox;

import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class App extends Application{
    private final Map map = new Map();
    private final PawnImage pawnImage = new PawnImage();

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
                        Button button = new Button("", pawnImage.getImageView(map.pawnAt(position).getColor()));
                        button.setContentDisplay(GRAPHIC_ONLY);
                        button.setMinSize(80,80);
                        button.setOnAction(event -> {
                            System.out.println("klik");
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

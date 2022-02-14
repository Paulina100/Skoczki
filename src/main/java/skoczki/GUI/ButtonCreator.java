package skoczki.GUI;

import javafx.scene.control.Button;
import skoczki.Map;
import skoczki.Vector2d;

import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class ButtonCreator {
    private final CreatedImages createdImages = new CreatedImages();
    private final Map map;

    public ButtonCreator(Map map) {
        this.map = map;
    }

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

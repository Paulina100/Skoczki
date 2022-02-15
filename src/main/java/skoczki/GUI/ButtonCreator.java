package skoczki.GUI;

import javafx.scene.control.Button;
import skoczki.Map;
import skoczki.Position;

import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class ButtonCreator {
    private final CreatedImages createdImages = new CreatedImages();
    private final Map map;

    public ButtonCreator(Map map) {
        this.map = map;
    }

    public Button createButton(Position position){
        Button button = new Button("", createdImages.getImageView(map.pawnAt(position)));
        button.setContentDisplay(GRAPHIC_ONLY);
        button.setMinSize(80, 80);
        button.setMaxSize(80, 80);

        button.setOnAction(event -> button.setGraphic(createdImages.getClickedImageView(map.pawnAt(position))));
        return button;
    }

}

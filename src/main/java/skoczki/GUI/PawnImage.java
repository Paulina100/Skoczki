package skoczki.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import skoczki.Color;
import skoczki.Pawn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PawnImage {
    private final List<Image> images = new ArrayList<>();

    public PawnImage(){
        try {
        images.add(new Image(new FileInputStream("src/main/resources/black.png")));
        images.add(new Image(new FileInputStream("src/main/resources/white.png")));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ImageView createImageView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);

        return imageView;
    }
    public ImageView getImageView(Color color){
        if (color.equals(Color.BLACK))return createImageView(images.get(0));
        return createImageView(images.get(1));
    }
}

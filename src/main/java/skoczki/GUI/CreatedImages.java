package skoczki.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import skoczki.Color;
import skoczki.Pawn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CreatedImages {
    private final List<Image> images = new ArrayList<>();

    public CreatedImages(){
        try {
        images.add(new Image(new FileInputStream("src/main/resources/black.png")));
        images.add(new Image(new FileInputStream("src/main/resources/white.png")));
        images.add(new Image(new FileInputStream("src/main/resources/space.png")));
        images.add(new Image(new FileInputStream("src/main/resources/black_clicked.png")));
        images.add(new Image(new FileInputStream("src/main/resources/white_clicked.png")));
        images.add(new Image(new FileInputStream("src/main/resources/space_clicked.png")));

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

    public ImageView getImageView(Object object){
        if (object instanceof Pawn){
            if (((Pawn) object).getColor().equals(Color.BLACK))return createImageView(images.get(0));
            return createImageView(images.get(1));
        }
        return createImageView(images.get(2));
    }

    public ImageView getClickedImageView(Object object){
        if (object instanceof Pawn){
            if (((Pawn) object).getColor().equals(Color.BLACK))return createImageView(images.get(3));
            return createImageView(images.get(4));
        }
        return createImageView(images.get(5));
    }
}

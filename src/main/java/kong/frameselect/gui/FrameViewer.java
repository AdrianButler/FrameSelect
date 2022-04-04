package kong.frameselect.gui; //TODO make arrows transparent and add option to hide them

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class FrameViewer extends Pane
{
    private ImageView imageView;
    private StackPane imageViewPositioner;
    private ArrayList<Image> frames;
    private int index;

    public FrameViewer(ArrayList<Image> frames)
    {
        this.frames = frames;

        imageView = new ImageView();
        imageView.setImage(frames.get(0));
        bindImageView();

        Triangle leftArrow = new Triangle(this,270.0);
        Triangle rightArrow = new Triangle(this,90.0);

        IndexBar indexBar = new IndexBar();
        indexBar.setStyle("-fx-background-color: #00faa8");

        indexBar.setTranslateX(10);
        indexBar.setTranslateY(10);

        setStyle("-fx-background-color: #000000");

        getChildren().addAll(imageViewPositioner, leftArrow, rightArrow, indexBar);
    }

    private void bindImageView() // solution to center image in center of pane: https://stackoverflow.com/a/25601147/12568475
    {
        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());
        imageView.setPreserveRatio(true);

        imageViewPositioner = new StackPane();
        imageViewPositioner.getChildren().add(imageView);
        imageViewPositioner.setAlignment(Pos.CENTER);

        imageViewPositioner.translateXProperty()
                .bind(widthProperty().subtract(imageViewPositioner.widthProperty())
                        .divide(2));

        imageViewPositioner.translateYProperty()
                .bind(heightProperty().subtract(imageViewPositioner.heightProperty())
                        .divide(2));
    }

    public void nextFrame()
    {
        if (index < frames.size() - 1)
        {
            index += 1;
        }

        else
        {
            index = 0;
        }

        imageView.setImage(frames.get(index));
    }

    public void previousFrame()
    {
        if (index > 0)
        {
            index -= 1;
        }
        else
        {
            index = frames.size() - 1;
        }

        imageView.setImage(frames.get(index));
    }

    public void setFrames(ArrayList<Image> frames)
    {
        this.frames = frames;
        imageView.setImage(frames.get(0));
    }

}


package kong.frameselect.gui; //TODO make arrows transparent and add option to hide them

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class FrameViewer extends Pane
{
    private ImageView imageView;
    private StackPane imageViewPositioner;
    private Image[] frames;
    private int index;

    public FrameViewer(Image[] frames)
    {
        this.frames = frames;

        imageView = new ImageView();
        imageView.setImage(frames[0]);
        bindImageView();

        Triangle leftArrow = new Triangle(this,270.0);
        Triangle rightArrow = new Triangle(this,90.0);

        setStyle("-fx-background-color: #000000");

        getChildren().addAll(imageViewPositioner, leftArrow, rightArrow);
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
        if (index < frames.length - 1)
        {
            index += 1;
        }

        else
        {
            index = 0;
        }

        imageView.setImage(frames[index]);
    }

    public void previousFrame()
    {
        if (index > 0)
        {
            index -= 1;
        }
        else
        {
            index = frames.length - 1;
        }

        imageView.setImage(frames[index]);
    }

    public void setFrames(Image[] frames)
    {
        this.frames = frames;
        imageView.setImage(frames[0]);
    }

}


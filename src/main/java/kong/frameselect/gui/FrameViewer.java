package kong.frameselect.gui; //TODO make arrows transparent and add option to hide them

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import kong.frameselect.framesplitter.MediaSplitter;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FrameViewer extends Pane
{
    private ImageView imageView;
    private StackPane imageViewPositioner;
    private Stage primaryStage;
    private ArrayList<Image> frames;
    private int index;

    public FrameViewer(ArrayList<Image> frames, Stage primaryStage)
    {
        this.frames = frames;
        this.primaryStage = primaryStage;

        imageView = new ImageView();
        imageView.setImage(frames.get(0));
        bindImageView();

        Triangle leftArrow = new Triangle(this, 270.0);
        Triangle rightArrow = new Triangle(this, 90.0);
        setStyle("-fx-background-color: #000000");

        getChildren().addAll(imageViewPositioner, leftArrow, rightArrow);
        setupButtons();
    }

    private void setupButtons()
    {
        Button saveFrameButton = new Button("Save");
        Button changeFileButton = new Button("Change File");

        getChildren().addAll(saveFrameButton, changeFileButton);

        saveFrameButton.layoutYProperty().bind(this.heightProperty().multiply(0.9));
        changeFileButton.layoutYProperty().bind(this.heightProperty().multiply(0.9));

        saveFrameButton.layoutXProperty().bind(this.widthProperty().subtract(60));
        changeFileButton.layoutXProperty().bind(this.widthProperty().subtract(150));

        changeFileButton.setOnAction(actionEvent ->
        {
            File newFile = new FileChooser().showOpenDialog(primaryStage);
            ArrayList<BufferedImage> originalImages
                    = MediaSplitter.framesToBufferedImage(MediaSplitter.getGifFrames(newFile));

            ArrayList<Image> fxImages = UIController.bufferedImageToFXImage(originalImages);
            fxImages.add(0, new Image(newFile.toURI().toString()));

            setFrames(fxImages);
        });

        saveFrameButton.setOnAction(actionEvent ->
        {
            if (index > 0)
            {
                FileChooser saveFrame = new FileChooser();
                saveFrame.setTitle("Save");
                saveFrame.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg"));

                File frameLocation = saveFrame.showSaveDialog(primaryStage);

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(frames.get(index), null);
                try
                {
                    ImageIO.write(bufferedImage, "jpg", frameLocation);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

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
        } else
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
        } else
        {
            index = frames.size() - 1;
        }

        imageView.setImage(frames.get(index));
    }

    public void setFrames(ArrayList<Image> frames)
    {
        this.frames = frames;
        imageView.setImage(frames.get(0));
        index = 0;
    }

}


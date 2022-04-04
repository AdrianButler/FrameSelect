package kong.frameselect.gui;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kong.frameselect.framesplitter.MediaSplitter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UIController extends Application //TODO add option to change image
{
    private Scene scene;
    private HomePage homePage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        homePage = new HomePage(primaryStage, this);
        scene = new Scene(homePage, 1000, 750);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToFrameViewer(File file)
    {
        ArrayList<BufferedImage> images = MediaSplitter.framesToBufferedImage(MediaSplitter.getGifFrames(file)); // extract images from frame

        ArrayList<Image> frames = new ArrayList<>();

        for (BufferedImage frame : images)
        {
            Image newImage = SwingFXUtils.toFXImage(frame, null);
            frames.add(newImage);
        }

        frames.add(0, new Image(file.toURI().toString()));

        FrameViewer frameViewer = new FrameViewer(frames);

        scene.setRoot(frameViewer);
    }


    public static void start()
    {
        launch();
    } //TODO finish https://youtu.be/4ukhZvOmAtk?t=217
}

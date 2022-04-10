package kong.frameselect.gui;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import kong.frameselect.framesplitter.MediaSplitter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class UIController extends Application
{
    private Scene scene;
    private HomePage homePage;
    private Stage primaryStage;

    public static void start()
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        homePage = new HomePage(primaryStage, this);
        scene = new Scene(homePage, 1000, 750);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToFrameViewer(File file)
    {
        ArrayList<BufferedImage> images = MediaSplitter.framesToBufferedImage(MediaSplitter.getGifFrames(file));

        ArrayList<Image> frames = bufferedImageToFXImage(images);

        frames.add(0, new Image(file.toURI().toString()));

        FrameViewer frameViewer = new FrameViewer(frames, primaryStage);

        scene.setRoot(frameViewer);
    }

    public static ArrayList<Image> bufferedImageToFXImage(ArrayList<BufferedImage> originalImages)
    {
        ArrayList<Image> fxImages = new ArrayList<>();

        for (BufferedImage frame : originalImages)
        {
            Image fxImage = SwingFXUtils.toFXImage(frame, null);
            fxImages.add(fxImage);
        }

        return fxImages;
    }
}

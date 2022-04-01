package kong.frameselect.gui;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kong.frameselect.framesplitter.Frame;
import kong.frameselect.framesplitter.MediaSplitter;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UIController extends Application //TODO add option to change image
{
    private Scene scene;
    private BorderPane window;
    private HomePage homePage;
    private WindowBar windowBar;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        homePage = new HomePage(primaryStage, this);
        windowBar = new WindowBar();

        window.setCenter(homePage);
        window.setTop(windowBar);

        scene = new Scene(homePage, 1000, 750);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToFrameViewer(File file)
    {
        ArrayList<BufferedImage> images = MediaSplitter.framesToBufferedImage(MediaSplitter.getGifFrames(file)); // extract images from frame
        Image[] frames = new Image[images.size()];

        for (int i = 0; i < frames.length; ++i)
        {
            Image image = SwingFXUtils.toFXImage(images.get(i), null);
            frames[i] = image;
        }

        FrameViewer frameViewer = new FrameViewer(frames);
        scene.setRoot(frameViewer);
    }


    public static void start()
    {
        launch();
    } //TODO finish https://youtu.be/4ukhZvOmAtk?t=217
}

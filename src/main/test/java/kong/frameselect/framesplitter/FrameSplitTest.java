package kong.frameselect.framesplitter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kong.frameselect.gui.FrameViewer;
import kong.frameselect.gui.HomePage;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FrameSplitTest extends Application
{

    @Test
    void testFrameSplit()
    {
        //todo add images from website and compare to results from custom implementation
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Image[] exampleImages = new Image[3];
        exampleImages[0] = new Image(new File("C:\\Users\\butle\\OneDrive\\Pictures\\spike.jpg").toURI().toString());
        exampleImages[1] = new Image(new File("C:\\Users\\butle\\OneDrive\\Pictures\\p-adventure-time-jeremy-shada.jpg").toURI().toString());
        exampleImages[2] = new Image(new File("C:\\Users\\butle\\OneDrive\\Pictures\\pcwallpaper22.png").toURI().toString());

        Scene scene = new Scene(new FrameViewer(exampleImages), 1000, 750);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void start()
    {
        launch();
    }
}

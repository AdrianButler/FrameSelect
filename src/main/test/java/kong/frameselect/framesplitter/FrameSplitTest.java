package kong.frameselect.framesplitter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kong.frameselect.gui.FrameViewer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class FrameSplitTest extends Application
{

    public static void start()
    {
        launch();
    }

    @Test
    void testFrameSplit()
    {
        //todo add images from website and compare to results from custom implementation
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        ArrayList<Image> exampleImages = new ArrayList<>();
        exampleImages.add(0, new Image(new File("C:\\Users\\butle\\OneDrive\\Pictures\\spike.jpg").toURI().toString()));
        exampleImages.add(1, new Image(new File("C:\\Users\\butle\\OneDrive\\Pictures\\p-adventure-time-jeremy-shada.jpg").toURI().toString()));
        exampleImages.add(2, new Image(new File("C:\\Users\\butle\\OneDrive\\Pictures\\pcwallpaper22.png").toURI().toString()));

        BorderPane window = new BorderPane();
        window.setCenter(new FrameViewer(exampleImages, primaryStage));

        Scene scene = new Scene(window, 1000, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

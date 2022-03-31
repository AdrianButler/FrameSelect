package kong.frameselect.gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.print.attribute.standard.DialogOwner;
import java.io.File;
import java.net.URISyntaxException;

public class HomePage extends BorderPane
{
    private Stage primaryStage;
    private UIController uiController;
    public HomePage(Stage primaryStage, UIController uiController)
    {
        this.primaryStage = primaryStage;
        this.uiController = uiController;

        ImageView logoView = createLogoView();
        setCenter(logoView);

        setStyle("-fx-background-color: #c7c7c7");
    }

    private ImageView createLogoView()
    {
        Image fileLogo = null;
        try
        {
            fileLogo = new Image(getClass().getResource("/kong/frameselect/FileLogo.png").toURI().toString());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
        ImageView logoView = new ImageView(fileLogo);

        logoView.setFitWidth(200);
        logoView.setFitHeight(100);
        logoView.setPreserveRatio(true);

        logoView.setPickOnBounds(true);

        logoView.setOnMouseClicked(mouseEvent ->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open GIF");
            File locationOfMedia = fileChooser.showOpenDialog(primaryStage);

            uiController.switchToFrameViewer(locationOfMedia);
        });

        return logoView;
    }
}

package kong.frameselect.gui;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.text.Caret;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;

public class IndexBar extends VBox//TODO reveal list of options (save, change gif)
{
    public IndexBar()
    {
        createDoubleCaret();

        setPickOnBounds(true);
    }

    private void createDoubleCaret()
    {
        Image caret = null;
        try
        {
            caret = new Image(getClass().getResource("/kong/frameselect/Caret.png").toURI().toString());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        ImageView bottomCaret = new ImageView(caret);
        ImageView topCaret = new ImageView(caret);

        bottomCaret.setPreserveRatio(true);
        topCaret.setPreserveRatio(true);

        bottomCaret.setFitHeight(25);
        bottomCaret.setFitWidth(25);

        topCaret.setFitHeight(25);
        topCaret.setFitWidth(25);

        getChildren().addAll(bottomCaret, topCaret);
    }
}

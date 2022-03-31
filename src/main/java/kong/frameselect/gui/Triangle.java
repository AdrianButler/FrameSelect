package kong.frameselect.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon //TODO change from triangles to thin arrow heads
{
    public Triangle(FrameViewer parent, Double rotation)
    {
        Double[] points = new Double[]
                {
                        -50.0, 40.0,
                        50.0, 40.0,
                        0.0, -60.0
                };

        getPoints().addAll(points);

        setFill(Color.GREY);
        setStroke(Color.BLACK);

        autosize();

        setRotate(rotation);

        bindToParent(parent);

        setOnMouseClicked(mouseEvent ->
        {
            if (rotation == 90)
            {
                parent.nextFrame();
            }
            else
            {
                parent.previousFrame();
            }
        });

    }

    private void bindToParent(FrameViewer parent)
    {
        layoutYProperty().bind(parent.heightProperty().divide(2));

        if(getRotate() == 90)
        {
            layoutXProperty().bind(parent.widthProperty().subtract(50));
        }
        else
        {
         setLayoutX(50);
        }
    }

}

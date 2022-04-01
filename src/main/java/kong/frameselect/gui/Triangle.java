package kong.frameselect.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Triangle extends Polygon
{
    public Triangle(FrameViewer parent, Double rotation)
    {
        Double[] points = new Double[]
                {
                        -50.0, 40.0,
                        0.0, -60.0,
                        50.0, 40.0,
                        40.0, 40.0,
                        1.0, -45.0,
                        -1.0, -45.0,
                        -40.0, 40.0
                };

        setScaleX(0.5);
        setScaleY(0.5);

        getPoints().addAll(points);

        setFill(Color.GREY);
        setStroke(Color.BLACK);

        autosize();

        setRotate(rotation);

        bindToParent(parent);

        setOnMouseEntered(mouseEvent ->
        {
            setScaleX(0.75);
            setScaleY(0.75);
        });

        setOnMouseExited(mouseEvent ->
        {
            setScaleX(0.5);
            setScaleY(0.5);
        });


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

        setPickOnBounds(true);
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

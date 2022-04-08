package kong.frameselect.framesplitter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;

@Data
@AllArgsConstructor
public class Frame
{
    private final BufferedImage image;
    private final int delay;
    private final String disposal;
    private final int width, height;

    public Frame(int delay, BufferedImage image, String disposal)
    {
        this.delay = delay;
        this.image = image;
        this.disposal = disposal;
        this.width = -1;
        this.height = -1;
    }

    public Frame(BufferedImage image)
    {
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        this.width = -1;
        this.height = -1;
    }


}

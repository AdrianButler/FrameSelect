package kong.frameselect.framesplitter;

import lombok.Data;

@Data
public class ImageMetaData
{
    private final int index;
    private final int delay;
    private final String disposal;
    private final int x;
    private final int y;
}

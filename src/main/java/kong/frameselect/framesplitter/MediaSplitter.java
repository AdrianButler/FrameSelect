package kong.frameselect.framesplitter;

import javafx.embed.swing.SwingFXUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSplitter
{
    public static ArrayList<Frame> getGifFrames(File file)
    {
        final String DISPOSAL_PREVIOUS = "restoreToPrevious";
        final String DISPOSAL_BACKGROUND = "restoreToBackgroundColor";
        final String DISPOSAL_NONE = "none";

        ArrayList<Frame> frames = new ArrayList<>(2);

        try (ImageInputStream inputStream = ImageIO.createImageInputStream(file))
        {
            Iterator<ImageReader> imageReaderIterator = ImageIO.getImageReadersBySuffix("gif");
            if (!imageReaderIterator.hasNext())
            {
                throw new IOException("No supported reader for this format");
            }

            final ImageReader imageReader = imageReaderIterator.next();
            imageReader.setInput(inputStream);

            try
            {
                final Integer[] size = extractLogicalScreenSize(imageReader);
                int width = size != null ? size[0] : -1;
                int height = size != null ? size[1] : -1;

                BufferedImage master = null;
                Graphics2D baseImage = null;
                int numFrames = imageReader.getNumImages(true);

                for (int frameIndex = 0; frameIndex < numFrames; ++frameIndex)
                {
                    BufferedImage img;

                    try
                    {
                        img = imageReader.read(frameIndex);
                    }
                    catch (IndexOutOfBoundsException e)
                    {
                        break;
                    }

                    if (width == -1 || height == -1)
                    {
                        width = img.getWidth();
                        height = img.getHeight();
                    }

                    ImageMetaData imageMetaData = extractImageMetaData(imageReader, frameIndex);
                    int x = imageMetaData.getX();
                    int y = imageMetaData.getY();

                    if (master == null)
                    {
                        master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                        baseImage = master.createGraphics();
                        baseImage.setBackground(new Color(0, 0, 0, 0));
                    }

                    baseImage.drawImage(img, x, y, null);

                    BufferedImage copy = new BufferedImage(master.getColorModel(), master.copyData(null), master.isAlphaPremultiplied(), null);
                    Frame frame = new Frame(copy, imageMetaData.getDelay(), imageMetaData.getDisposal(), width, height);
                    frames.add(frame);

                    if (DISPOSAL_PREVIOUS.equals(imageMetaData.getDisposal()))
                    {
                        BufferedImage from = null;

                        for (int i = frameIndex - 1; i >= 0; --i)
                        {
                            Frame tempFrame = frames.get(i);

                            if (!DISPOSAL_PREVIOUS.equals(frame.getDisposal()) || frameIndex == 0)
                            {
                                from = tempFrame.getImage();
                                break;
                            }
                        }

                        if (from != null)
                        {
                            master = new BufferedImage(from.getColorModel(), from.copyData(null), from.isAlphaPremultiplied(), null);
                            baseImage = master.createGraphics();

                            baseImage.setBackground(new Color(0, 0, 0, 0));
                        }
                    }

                    else if (DISPOSAL_BACKGROUND.equals(imageMetaData.getDisposal()))
                    {
                        baseImage.clearRect(x, y, img.getWidth(), img.getHeight());
                    }
                }
                return frames; // todo check if redundant
            }
            finally
            {
                imageReader.dispose();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return frames;
    }

    private static ImageMetaData extractImageMetaData(ImageReader reader, int frameIndex)
    {
        try
        {
            IIOMetadata metadata = reader.getImageMetadata(frameIndex);
            IIOMetadataNode root = (IIOMetadataNode)metadata.getAsTree("javax_imageio_gif_image_1.0");
            IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);

            int delay = Integer.parseInt(gce.getAttribute("delayTime"));
            String disposal = gce.getAttribute("disposalMethod");

            int x = 0, y = 0;

            NodeList children = root.getChildNodes();

            for (int nodeIndex = 0; nodeIndex < children.getLength(); ++nodeIndex)
            {
                Node nodeItem = children.item(nodeIndex);
                if ("ImageDescriptor".equalsIgnoreCase(nodeItem.getNodeName()))
                {
                    NamedNodeMap map = nodeItem.getAttributes();
                    x = Integer.parseInt(map.getNamedItem("imageLeftPosition").getNodeValue());
                    y = Integer.parseInt(map.getNamedItem("imageTopPosition").getNodeValue());
                }
            }

            return new ImageMetaData(frameIndex, delay, disposal, x, y);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return  null;
    }

    private static Integer[] extractLogicalScreenSize(ImageReader reader)
    {
        Integer[] size = null;
        try
        {
            IIOMetadata metadata = reader.getStreamMetadata();
            if (metadata == null)
            {
                return null;
            }

            IIOMetadataNode globalRoot = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());
            NodeList globalScreenDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");

            if (globalScreenDescriptor != null && globalScreenDescriptor.getLength() > 0)
            {
                IIOMetadataNode screenDescriptor = (IIOMetadataNode) globalScreenDescriptor.item(0);

                if(screenDescriptor != null);
                size = new Integer[2];
                size[0] = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
                size[1] = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return size;
    }

    public static ArrayList<BufferedImage> framesToBufferedImage(List<Frame> frames)
    {
        ArrayList<BufferedImage> imagesFromFrames = new ArrayList<>();

        for (int i = 0; i < frames.size(); ++i)
        {
            imagesFromFrames.add(frames.get(i).getImage());
        }

        return imagesFromFrames;
    }
}

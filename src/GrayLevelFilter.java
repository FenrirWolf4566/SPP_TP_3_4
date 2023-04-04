package src;
import java.awt.image.BufferedImage;

public class GrayLevelFilter implements IFilter {

    /**
    getMargin() should return the number of neighboring pixels in all four directions that the filter requires
    to compute a pixel value in the new image. As a result the size of the image produced by the filter will
    generally be smaller than the original image. More specifically if the original image has a dimension of
    w × h pixels, and if getMargin() returns m, then the new image should contain (w − 2m) × (h − 2m)
    pixels
     */
    @Override
    public int getMargin() {
        return 0;
    }
    /**
    applyFilterAtPoint(..) should apply the filter around the pixel (x, y) in the input image imgIn, and
    set the pixel (x − m, y − m) of imgOut, where m is the margin returned by getMargin(). During this
    computation, the filter should only use pixel values located in a square bordered by the (x − m, y − m)
    and (x + m, y + m) positions of the input image
     */
    @Override
    public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
        int r; 
        int g;
        int b;
        int gray;
        int rgb;
        rgb = imgIn.getRGB(x, y);
        r = (rgb >> 16) & 0x000000FF;
        g = (rgb >> 8) & 0x000000FF;
        b = (rgb) & 0x000000FF;
        gray = (int)((r+g+b)/3);
        int newRgb = (gray << 16) | (gray << 8) | gray; 
        imgOut.setRGB(x, y, newRgb);
    }
    
}

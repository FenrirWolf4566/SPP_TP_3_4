package src;

import java.awt.image.BufferedImage;

public class GrayLevelFilter extends FilterUtils {

    /**
     * getMargin() should return the number of neighboring pixels in all four
     * directions that the filter requires
     * to compute a pixel value in the new image. As a result the size of the image
     * produced by the filter will
     * generally be smaller than the original image. More specifically if the
     * original image has a dimension of
     * w × h pixels, and if getMargin() returns m, then the new image should contain
     * (w − 2m) × (h − 2m)
     * pixels
     */
    @Override
    public int getMargin() {
        return 0;
    }

    /**
     * applyFilterAtPoint(..) should apply the filter around the pixel (x, y) in the
     * input image imgIn, and
     * set the pixel (x − m, y − m) of imgOut, where m is the margin returned by
     * getMargin(). During this
     * computation, the filter should only use pixel values located in a square
     * bordered by the (x − m, y − m)
     * and (x + m, y + m) positions of the input image
     */
    @Override
    public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
        // extracting red, green and blue components from rgb integer
        int rgb = imgIn.getRGB(x, y);
        int[] arrRgb = intToRgb(rgb);
        int red = arrRgb[0];
        int green = arrRgb[1];
        int blue = arrRgb[2];

        // FILTER COMPUTATION HERE
        int gray = (int) ((red + green + blue) / 3);


        // computing new color from extracted components
        int newRgb = rgbToInt(gray, gray, gray);
        imgOut.setRGB(x, y, newRgb);
    }

}

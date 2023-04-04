package src;
import java.awt.image.BufferedImage;

public class ExampleFilter extends FilterUtils   {

  @Override
  public int getMargin() {
    return 0;
  }

  @Override
  public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
    // extracting red, green and blue components from rgb integer
    int rgb = imgIn.getRGB(x, y);
    int[] arrRgb = intToRgb(rgb);
    int red = arrRgb[0];
    int green = arrRgb[1];
    int blue = arrRgb[2];
    // computing new color from extracted components
    int newRgb = rgbToInt(red, green, blue); // rotating RGB values
    imgOut.setRGB(x, y, newRgb);
  }

}

package src;
import java.awt.image.BufferedImage;

public class ExampleFilter implements IFilter {

  @Override
  public int getMargin() {
    return 0;
  }

  @Override
  public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
    int rgb = imgIn.getRGB(x, y);
    // extracting red, green and blue components from rgb integer
    int red = (rgb >> 16) & 0x000000FF;
    int green = (rgb >> 8) & 0x000000FF;
    int blue = (rgb) & 0x000000FF;
    // computing new color from extracted components
    int newRgb = (((green << 8) | blue) << 8) | red; // rotating RGB values
    imgOut.setRGB(x, y, newRgb);
  }

}

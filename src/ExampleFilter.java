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


    int newRgb = ( ( (green << 8) | blue ) << 8 ) | red ;
    imgOut.setRGB(x, y, newRgb);
  }

  static public void main(String[] args) throws Exception {
    IImageFilteringEngine engine = new SingleThreadedImageFilteringEngine();
    engine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
    engine.applyFilter(new ExampleFilter());
    engine.writeOutPngImage("TEST_IMAGES/test_examplefilter.png");
  } // EndMain

}

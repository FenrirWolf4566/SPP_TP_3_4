package src;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class ImageFilteringEngine implements IImageFilteringEngine {

  private BufferedImage img;

  @Override
  public void loadImage(String inputImage) throws Exception {
    // reading image in
    BufferedImage inImg = ImageIO.read(new File(inputImage));
    img = inImg;
  }

  @Override
  public void writeOutPngImage(String outFile) throws Exception {
    // expect the image to be in the img field
    assert this.img != null;

    // writing out new image
    File f = new File(outFile);
    ImageIO.write(img, "png", f);
  }

  @Override
  public void setImg(BufferedImage newImg) {
    img = newImg;
  }

  @Override
  public BufferedImage getImg() {
    return img;
  }

  @Override
  public void applyFilter(IFilter someFilter) {
    // creating new image
    BufferedImage outImg = new BufferedImage(img.getWidth(),
        img.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    // generating new image from original
    for (int x = 0; x < img.getWidth(); x++) {
      for (int y = 0; y < img.getHeight(); y++) {
        someFilter.applyFilterAtPoint(x, y, img, outImg);
      } // EndFor y
    } // EndFor x
    img = outImg;
  }

  static public void main(String[] args) throws Exception {
    ImageFilteringEngine engine = new ImageFilteringEngine();
    engine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
    engine.applyFilter(new ExampleFilter());
    engine.writeOutPngImage("TEST_IMAGES/tmp.png");
  } // EndMain

}

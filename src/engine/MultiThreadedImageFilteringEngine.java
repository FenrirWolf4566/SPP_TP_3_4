package src.engine;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import src.filters.GaussianContourExtractorFilter;
import src.filters.IFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MultiThreadedImageFilteringEngine implements IImageFilteringEngine {
  private BufferedImage img;
  private int workerCount;

  public MultiThreadedImageFilteringEngine(int threadCount) {
    this.workerCount = threadCount;
  }

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
    int width = img.getWidth();
    int height = img.getHeight();
    int step = height / workerCount;

    // create cyclic barrier for synchronization
    CyclicBarrier barrier = new CyclicBarrier(workerCount + 1);

    // create worker threads
    ArrayList<Thread> workers = new ArrayList<Thread>();
    for (int i = 0; i < workerCount; i++) {
      int startY = i * step;
      int endY = (i == workerCount - 1) ? height : startY + step; // make sure if it's not divisible by k, the last
                                                                  // worker gets the rest
      Thread worker = new Thread(() -> {
        for (int y = startY; y < endY; y++) {
          for (int x = 0; x < width; x++) {
            someFilter.applyFilterAtPoint(x, y, img, outImg);
          }
        }
        try {
          barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
          Thread.currentThread().interrupt();
          return;
        }
      });
      workers.add(worker);
    }

    // distribute work and start worker threads
    for (int i = 0; i < workerCount; i++) {
      Thread worker = workers.get(i);
      worker.start();
    }

    // wait for worker threads to complete
    try {
      barrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      Thread.currentThread().interrupt();
      return;
    }

    img = outImg;
  }

  static public void main(String[] args) throws Exception {
    IImageFilteringEngine engine = new MultiThreadedImageFilteringEngine(16);
    engine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
    engine.applyFilter(new GaussianContourExtractorFilter());
    engine.writeOutPngImage("OUR_IMAGES/test_multithread.png");
  } // EndMain

}

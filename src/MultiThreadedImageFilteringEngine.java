package src;
import java.awt.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    int width = img.getWidth();
    int height = img.getHeight();
    int step = height / workerCount;

    // create cyclic barrier for synchronization
    CyclicBarrier barrier = new CyclicBarrier(workerCount + 1);

    // create worker threads
    ArrayList<Thread> workers = new ArrayList<Thread>();
    for (int i = 0; i < workerCount; i++) {
        int startY = i * step;
        int endY = (i == workerCount - 1) ? height : startY + step;
        Thread worker = new Thread(() -> {
            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < width; x++) {
                    someFilter.applyFilterAtPoint(x, y, img, img);
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
        int startY = i * step;
        int endY = (i == workerCount - 1) ? height : startY + step;
        worker.start();
    }

    // wait for worker threads to complete
    try {
        barrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
        Thread.currentThread().interrupt();
        return;
    }
}


    // creating new image
    BufferedImage outImg = new BufferedImage(img.getWidth(),
    img.getHeight(),
    BufferedImage.TYPE_INT_RGB);

    CyclicBarrier barrier = new CyclicBarrier(workerCount);

    ExecutorService executor = Executors.newFixedThreadPool(workerCount);
    Object lock = new Object();

    for (int i = 0; i < workerCount; i++) {
        executor.submit(() -> {
            synchronized (lock) {
              System.out.println("Thread " + Thread.currentThread().getId() + " is running");
            }
            try {
                // wait for other threads to reach the barrier point
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            // continue with the task once all threads have reached the barrier point
        });
    }

    executor.shutdown();


    // // generating new image from original
    // for (int x = 0; x < img.getWidth(); x++) {
    //   for (int y = 0; y < img.getHeight(); y++) {
    //     someFilter.applyFilterAtPoint(x, y, img, outImg);
    //   } // EndFor y
    // } // EndFor x

    img = outImg;
  }

  static public void main(String[] args) throws Exception {
    IImageFilteringEngine engine = new MultiThreadedImageFilteringEngine(4);
    engine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
    engine.applyFilter(new ExampleFilter());
    engine.writeOutPngImage("TEST_IMAGES/tmp.png");
  } // EndMain

}

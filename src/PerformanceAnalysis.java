package src;

public class PerformanceAnalysis {
  public static void main(String[] args) {
    IFilter filterGray = new GrayLevelFilter();
    IFilter filterContour = new GaussianContourExtractorFilter();

    IImageFilteringEngine single = new SingleThreadedImageFilteringEngine();
    IImageFilteringEngine multi4 = new MultiThreadedImageFilteringEngine(16);

    String img = "TEST_IMAGES/15226222451_75d515f540_o.jpg";

    System.out.println("| Engine       | GrayLevelFilter | GaussianContourExtractorFilter |");
    System.out.println("| ------------ | --------------- | ------------------------------ |");

    // single thread version
    System.out.print("| Single Thread | ");
    System.out.print(perfOf(single, img, filterGray) + " | ");
    System.out.println(perfOf(single, img, filterContour) + " |");

    // parallel version with the number of threads varying from 1 to 10.
    for (int i = 1; i <= 10; i++) {
      IImageFilteringEngine multi = new MultiThreadedImageFilteringEngine(i);
      System.out.print("| Multi Thread (k=" + i + ") | ");
      System.out.print(perfOf(multi, img, filterGray) + " | ");
      System.out.println(perfOf(multi, img, filterContour) + " |");
    }

  }

  public static String perfOf(IImageFilteringEngine engine, String imgPath, IFilter filter) {
    long sum = 0;
    int numberOfRuns = 1;
    for (int i = 0; i < numberOfRuns; i++) {
      try {
        engine.loadImage(imgPath);
      } catch (Exception e) {
        e.printStackTrace();
      }
      long startTime = System.currentTimeMillis();
      engine.applyFilter(filter);
      sum += System.currentTimeMillis() - startTime;
    }
    long avg = sum / numberOfRuns;
    return avg + "ms";
  }
}

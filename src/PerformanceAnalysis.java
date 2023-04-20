package src;

public class PerformanceAnalysis {
  public static void main(String[] args) {
    // part1();
    part2();
  }

  public static void part1() {
    IFilter filterGray = new GrayLevelFilter();
    IFilter filterContour = new GaussianContourExtractorFilter();

    IImageFilteringEngine single = new SingleThreadedImageFilteringEngine();

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

  public static void part2() {
    IFilter filterGray = new GrayLevelFilter();
    IFilter filterContour = new GaussianContourExtractorFilter();

    IImageFilteringEngine multi4 = new MultiThreadedImageFilteringEngine(16);

    String[] images = new String[] {
        "TEST_IMAGES/15226222451_75d515f540_o.jpg", // 1920 * 1080= 2073600
        "TEST_IMAGES/15226222451_a49b1a624b_h.jpg", // 1600 * 900 = 1440000
        "TEST_IMAGES/15226222451_5fd668d81a_c.jpg", // 800 * 450 = 360000
        "TEST_IMAGES/15226222451_5fd668d81a_z.jpg", // 640 * 360 = 230400
        "TEST_IMAGES/15226222451_5fd668d81a.jpg", // 500 * 281 = 140500
        "TEST_IMAGES/15226222451_5fd668d81a_n.jpg", // 320 * 179 = 57280
        "TEST_IMAGES/15226222451_5fd668d81a_m.jpg", // 240 * 134 = 32160
        "TEST_IMAGES/15226222451_5fd668d81a_t.jpg", // 100 * 56 = 5600
    };

    int[] sizes = new int[] {
        2073600,
        1440000,
        360000,
        230400,
        140500,
        57280,
        32160,
        5600,
    };

    System.out.println("| Image size   | GrayLevelFilter | GaussianContourExtractorFilter |");
    System.out.println("| ------------ | --------------- | ------------------------------ |");

    for (int i = 0; i < images.length; i++) {
      System.out.print("| "+ images[i].split("/")[1] +" (n=" + sizes[i] + ") | ");
      System.out.print(perfOf(multi4, images[i], filterGray) + " | ");
      System.out.println(perfOf(multi4, images[i], filterContour) + " |");
    }

  }

  public static String perfOf(IImageFilteringEngine engine, String imgPath, IFilter filter) {
    long sum = 0;
    int numberOfRuns = 10;
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

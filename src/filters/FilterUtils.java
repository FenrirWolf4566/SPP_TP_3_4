package src.filters;

public abstract class FilterUtils implements IFilter {

  public int rgbToInt(int r, int g, int b) {
    return (r << 16) | (g << 8) | b;
  }

  public int[] intToRgb(int rgb) {
    int[] rgbArray = new int[3];
    rgbArray[0] = (rgb >> 16) & 0x000000FF;
    rgbArray[1] = (rgb >> 8) & 0x000000FF;
    rgbArray[2] = (rgb) & 0x000000FF;
    return rgbArray;
  }

}

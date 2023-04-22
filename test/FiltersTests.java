package test;

import src.engine.IImageFilteringEngine;
import src.engine.SingleThreadedImageFilteringEngine;
import src.filters.GaussianContourExtractorFilter;
import src.filters.GrayLevelFilter;

import org.junit.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

public class FiltersTests {

    // Some useful attributes
    private Boolean verif;
    private BufferedImage testImage;
    private BufferedImage verifImage;
    
    // Basic Images
    private String normal_image = "/home/fenrir/Bureau/SPP_TP_3_4/TEST_IMAGES/15226222451_5fd668d81a_c.jpg";
    private String normal_image_grey = "/home/fenrir/Bureau/SPP_TP_3_4/TEST_IMAGES/15226222451_5fd668d81a_c_gray.png";
    private String normal_image_gaussian = "/home/fenrir/Bureau/SPP_TP_3_4/TEST_IMAGES/15226222451_5fd668d81a_c_gaussian_contour.png";

    // Circle Images
    private String four_circles = "/home/fenrir/Bureau/SPP_TP_3_4/TEST_IMAGES/FourCircles.png";
    private String four_circles_grey = "/home/fenrir/Bureau/SPP_TP_3_4/TEST_IMAGES/FourCircles_gray.png";
    private String four_circles_gaussian = "/home/fenrir/Bureau/SPP_TP_3_4/TEST_IMAGES/FourCircles_gaussian_contour.png";

    // Output
    private String output_path = "/home/fenrir/Bureau/SPP_TP_3_4/OUR_IMAGES";


    @Test
    public void testImageGreyFilter() throws Exception {
        IImageFilteringEngine singleEngine = new SingleThreadedImageFilteringEngine();
        // Use filter
        singleEngine.loadImage(normal_image);
        singleEngine.applyFilter(new GrayLevelFilter());
        singleEngine.writeOutPngImage(output_path+"/test_image_grey_filter.png");
        // Compare
        testImage = loadBufferedImage(output_path+"/test_image_grey_filter.png");
        verifImage = loadBufferedImage(normal_image_grey);
        verif = compareBufferedImages(testImage, verifImage);
        assertTrue(verif);
    } // EndMethod testImageGreyFilter

    @Test
    public void testCircleGreyFilter() throws Exception {
        IImageFilteringEngine singleEngine = new SingleThreadedImageFilteringEngine();
        // Use filter
        singleEngine.loadImage(four_circles);
        singleEngine.applyFilter(new GrayLevelFilter());
        singleEngine.writeOutPngImage(output_path+"/test_circle_grey_filter.png");
        // Compare
        testImage = loadBufferedImage(output_path+"/test_circle_grey_filter.png");
        verifImage = loadBufferedImage(four_circles_grey);
        verif = compareBufferedImages(testImage, verifImage);
        assertTrue(verif);
    } // EndMethod testCircleGreyFilter

    @Test
    public void testImageGaussianFilter() throws Exception {
        IImageFilteringEngine singleEngine = new SingleThreadedImageFilteringEngine();
        // Use filter
        singleEngine.loadImage(normal_image_grey);
        singleEngine.applyFilter(new GaussianContourExtractorFilter());
        singleEngine.writeOutPngImage(output_path+"/test_image_gaussian_filter.png");
        // Compare
        testImage = loadBufferedImage(output_path+"/test_image_gaussian_filter.png");
        verifImage = loadBufferedImage(normal_image_gaussian);
        verif = compareBufferedImages(testImage, verifImage);
        assertTrue(verif);
    } // EndMethod testImageGaussianFilter

    @Test
    public void testCircleGaussianFilter() throws Exception {
        IImageFilteringEngine singleEngine = new SingleThreadedImageFilteringEngine();
        // Use filter
        singleEngine.loadImage(four_circles_grey);
        singleEngine.applyFilter(new GaussianContourExtractorFilter());
        singleEngine.writeOutPngImage(output_path+"/test_circle_gaussian_filter.png");
        // Compare
        testImage = loadBufferedImage(output_path+"/test_circle_gaussian_filter.png");
        verifImage = loadBufferedImage(four_circles_gaussian);
        verif = compareBufferedImages(testImage, verifImage);
        assertTrue(verif);
    } // EndMethod testCircleGaussianFilter

    public BufferedImage loadBufferedImage(String path) throws IOException {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        return image;
    } // EndMethod loadBufferedImage

    public Boolean compareBufferedImages(BufferedImage testImage, BufferedImage verifImage) {
        // Check of size
        if (testImage.getWidth() != verifImage.getWidth() || testImage.getHeight() != verifImage.getHeight()) {
            return false; // Images have different dimensions
        }
        // Check for pixels
        for (int x = 0; x < testImage.getWidth(); x++) {
            for (int y = 0; y < testImage.getHeight(); y++) {
                if (testImage.getRGB(x, y) != verifImage.getRGB(x, y)) {
                    return false; // Images differ at this pixel
                }
            }
        }
        return true;
    } // EndMethod compareBufferedImages


}

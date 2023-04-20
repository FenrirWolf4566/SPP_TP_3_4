package JUNIT_TESTS;

import src.*;

import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

public class GaussianContourExtractorFilterTest {
    
    private SingleThreadedImageFilteringEngine singleEngine = new SingleThreadedImageFilteringEngine();

    // "c" images for verif
    private BufferedImage verifImageGrey;
    private BufferedImage verifImageGaussian;

    // "circle" images for verif
    private BufferedImage verifCircleGrey;
    private BufferedImage verifCircleGaussian;

    // Base images
    private BufferedImage baseImage;
    private BufferedImage baseCircle;

    private BufferedImage imgOut;
    private GaussianContourExtractorFilter filter;
    
    @Before
    public void setUp() throws Exception {

        // Charger l'image de test
        verifImageGrey = singleEngine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c_gray.jpg");
        verifImageGaussian = singleEngine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c_gaussian_contour.png");

        verifCircleGrey = singleEngine.loadImage("TEST_IMAGES/FourCircles_gray.jpg");
        verifCircleGaussian = singleEngine.loadImage("TEST_IMAGES/FourCircles_gaussian_contour.png");

        baseImage = singleEngine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
        baseCircle = singleEngine.loadImage("TEST_IMAGES/FourCircles.png");
    }
    

    @Test
    public void testGreyFilter() throws Exception {
        baseImage = singleEngine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
        singleEngine.applyFilter(new GrayLevelFilter());
        singleEngine.writeOutPngImage("OUR_IMAGES/test_gray_level.png");
        BufferedImage test = singleEngine.loadImage("OUR_IMAGES/test_gray_level.png");
        Boolean verif = compareBufferedImages(test, baseImage);
        assertTrue(verif);
      
    } // EndMethod testGreyFilter



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
package src.test;

import src.*;

import org.junit.*;
import java.util.*;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FiltersTests {

    // private SingleThreadedImageFilteringEngine singleEngine = new
    // SingleThreadedImageFilteringEngine();

    // "c" images for verif
    private String verifImageGrey;
    // private String verifImageGaussian;

    // "circle" images for verif
    // private String verifCircleGrey;
    // private String verifCircleGaussian;

    // Base images
    // private BufferedImage baseImage;
    // private BufferedImage baseCircle;

    private Boolean verif;
    private BufferedImage testImage;
    private BufferedImage verifImage;

    @Before
    public void setUp() throws Exception {

        // Charger l'image de test
        verifImageGrey = "TEST_IMAGES/15226222451_5fd668d81a_c_gray.jpg";
        // verifImageGaussian =
        // "TEST_IMAGES/15226222451_5fd668d81a_c_gaussian_contour.png";

        // verifCircleGrey = "TEST_IMAGES/FourCircles_gray.jpg";
        /// verifCircleGaussian = "TEST_IMAGES/FourCircles_gaussian_contour.png";

        // baseImage =
        // singleEngine.loadImage("TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
        // baseCircle = singleEngine.loadImage("TEST_IMAGES/FourCircles.png");
    }

    @Test
    public void testGreyFilter() throws Exception {
        IImageFilteringEngine singleEngine = new SingleThreadedImageFilteringEngine();
        // Use filter

        URL inputPath = getClass().getResource("../../TEST_IMAGES/15226222451_5fd668d81a_c.jpg");
        String outputPath = "../../OUR_IMAGES/test_gray_level.png";
        singleEngine.loadImage(inputPath.getPath());
        singleEngine.applyFilter(new GrayLevelFilter());
        singleEngine.writeOutPngImage(outputPath);

        // Compare
        testImage = loadBufferedImage(outputPath);
        verifImage = loadBufferedImage(verifImageGrey);
        verif = compareBufferedImages(testImage, verifImage);
        assertTrue(verif);

    } // EndMethod testGreyFilter

    public BufferedImage loadBufferedImage(String path) throws IOException {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        return image;
    }

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
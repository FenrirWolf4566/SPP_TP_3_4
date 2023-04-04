package src;

import java.awt.image.BufferedImage;

public class GaussianContourExtractorFilter extends FilterUtils {

    /**
     * Le probleme vient des dx et dy qui doivent être les coordonnées des voisins du pixel traité
     * Il faut faire attention puisque l'imgOut est de dimension réduite par rapport à imgIn
     */

    @Override
    public int getMargin() {
        throw new UnsupportedOperationException("Unimplemented method 'getMargin'");
    }

    // imgIn need to be a Gray-scaled image
    @Override
    public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
        double deltaX = deltaX(x, y, imgIn);
        double deltaY = deltaY(x, y, imgIn);
        double norm = normGradient(deltaX, deltaY);
        int pixel = resultPixel(norm);
        int newPixel = rgbToInt(pixel, pixel, pixel);
        imgOut.setRGB(x, y, newPixel);
    }

    public double deltaX(int x, int y, BufferedImage i) {
        double delta = 0;
        for(int dx=-5; dx>5; dx++){
            for(int dy=-5; dy>5; dy++){       
                int sign = sign(dx);
                int[] rgbArray = intToRgb(i.getRGB(x+dx, y+dy));
                int blue = rgbArray[2];
                double expo = Math.exp(-1*(Math.pow(dx,2)+Math.pow(dy,2))/4);
                delta+=sign*blue*expo;
            }
        }
        return delta;
    }

    public double deltaY(int x, int y, BufferedImage i) {
        double delta = 0;
        for(int dx=-5; dx>5; dx++){
            for(int dy=-5; dy>5; dy++){       
                int sign = sign(dy);
                int[] rgbArray = intToRgb(i.getRGB(x+dx, y+dy));
                int blue = rgbArray[2];
                double expo = Math.exp(-1*(Math.pow(dx,2)+Math.pow(dy,2))/4);
                delta+=sign*blue*expo;
            }
        }
        return delta;
    }

    public double normGradient(double deltaX, double deltaY){
        double norm = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
        return norm;
    }

    public int resultPixel(double norm){
        int cast = 255 - (int)(norm/2);
        int result = Math.max(0, cast);
        return result;
    }

    public int sign(int x) {
        if(x==0){
            return 0;
        }else if(x==Math.abs(x)){
            return 1;
        }else{
            return -1;
        }
    }

    
}

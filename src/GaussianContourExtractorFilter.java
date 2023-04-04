package src;

import java.awt.image.BufferedImage;

public class GaussianContourExtractorFilter implements IFilter {


    @Override
    public int getMargin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMargin'");
    }

    // imgIn need to be a Gray-scaled image
    @Override
    public void applyFilterAtPoint(int x, int y, BufferedImage imgIn, BufferedImage imgOut) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyFilterAtPoint'");
    }

    public int delta_y(int dx, int dy, BufferedImage i) {
        int sign = sign(dx);
        


        return 1;
    }
    public int delta_x(int[] dx, int[] dy){
        int sign = sign(dy);


        return 1
    }

    public int sign(int x) {
        if(x==Math.abs(x)){
            return 1;
        }else{
            return -1;
        }
    }

    
}

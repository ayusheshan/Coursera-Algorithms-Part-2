/*
 * public class SeamCarver {
 public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
 public Picture picture()                          // current picture
 public     int width()                            // width of current picture
 public     int height()                           // height of current picture
 public  double energy(int x, int y)               // energy of pixel at column x and row y
 public   int[] findHorizontalSeam()               // sequence of indices for horizontal seam
 public   int[] findVerticalSeam()                 // sequence of indices for vertical seam
 public    void removeHorizontalSeam(int[] seam)   // remove horizontal seam from current picture
 public    void removeVerticalSeam(int[] seam)     // remove vertical seam from current picture
 }
 */
import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
public class SeamCarver
{
    private Picture pic;
    private double e[][];
    private double te[][];
    private int col[][];
    private int height, width;
    public SeamCarver(Picture picture)
    {
        if(picture == null)
            throw new java.lang.IllegalArgumentException();
        pic = new Picture(picture);
        height = pic.height();
        width = pic.width();
        e = new double[pic.width()][pic.height()];
        te = new double[pic.width()][pic.height()];
        col = new int[pic.width()][pic.height()];
        for(int j = 0; j < height ; j++)
            for(int i = 0; i < width ; i++)
        {
            e[i][j] = energy(i , j);
            col[i][j] = pic.getRGB(i, j);
        }
    }
    public Picture picture()
    {
        //calculate();
        Picture p = new Picture( width, height);
        for(int j = 0; j < height; j++)
            for(int i = 0; i < width; i++)
            p.setRGB(i,j,col[i][j]);
        return p;
    }
    public int width()
    {
        return width;
    }
    public int height()
    {
        return height;
    }
    public double energy(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            throw new java.lang.IllegalArgumentException();
        if(x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return 1000;
        double x1 = calcx(x,y);
        double y1 = calcy(x,y);
        return Math.sqrt(x1 + y1);
    }
    private double calcx(int x, int y)
    {
        
        Color c1 = pic.get(x - 1 ,y);
        Color c2 = pic.get(x + 1, y);
        int b1 = c1.getBlue();
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        
        int b2 = c2.getBlue();
        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        
        return Math.pow(r1 - r2, 2) + Math.pow(b1 - b2, 2) + Math.pow(g1 - g2, 2);
    }
    private double calcy(int x, int y)
    {
        
        Color c1 = pic.get(x  ,y - 1);
        Color c2 = pic.get(x , y + 1);
        int b1 = c1.getBlue();
        int r1 = c1.getRed();
        int g1 = c1.getGreen();
        
        int b2 = c2.getBlue();
        int r2 = c2.getRed();
        int g2 = c2.getGreen();
        
        return Math.pow(r1 - r2, 2) + Math.pow(b1 - b2, 2) + Math.pow(g1 - g2, 2);
    }
    /*private Picture transpose(Picture pic)
     {
     Picture p = new Picture(pic.height(), pic.width());
     e = new double[pic.height()][pic.width()];
     te = new double[pic.height()][pic.width()];
     for(int j = 0; j < pic.height(); j++)
     for(int i = 0; i < pic.width(); i++)
     {
     int c = pic.getRGB(i,j);
     p.setRGB(j , i , c);
     }
     return p;
     }*/
    public   int[] findHorizontalSeam()
    {
        /*pic = transpose(pic);
         int[] seam = new int[pic.height()];
         seam = findVerticalSeam();
         pic = transpose(pic);
         return seam;*/    
        //calculate();
        int[] seam = new int[width];
        for(int j = 0; j < height ; j++)
            te[0][j] = e[0][j];
        
        
        for(int i = 1; i < width ; i++)
            for(int j = 1; j < height - 1 ; j++)
        {
            double m1,m3; m1 = m3 = Double.POSITIVE_INFINITY;
            if(j - 1 > 0)
                m1 = te[i - 1][ j - 1];
            double m2 = te[i - 1][ j  ];
            if(j + 1 < height - 1)
                m3 = te[i - 1][j + 1];
            double min = (m1 < m2)? (m1 < m3 ? m1 : m3) : (m2 < m3 ? m2 : m3);
            te[i][j] = e[i][j] + min;
        }
        
        double min = Double.POSITIVE_INFINITY;
        int i = width - 1;
        for(int j = 1; j < height - 1; j++)
        {
            if( te[i][j] < min)
            {
                min = te[i][j];
                seam[i] = j;
            }
        }
        int j = seam[i];
        i--;
        while(i > 0)
        {
            double m1,m3; m1 = m3 = Double.POSITIVE_INFINITY;
            if(j - 1 > 0)
                m1 = te[i ][ j - 1 ];
            double m2 = te[i][ j ];
            if(j + 1 < height - 1)
                m3 = te[i][j + 1];
            int m = (m1 < m2)? (m1 < m3 ? j - 1 : j + 1) : (m2 < m3 ? j : j + 1);
            seam[i] = m;
            j = m;
            i--;
        }
        if(seam.length >= 2)
            seam[0] = seam[1];
        //seam[pic.height() - 1] = seam[pic.height() - 2];
        return seam;
    }
    public   int[] findVerticalSeam()  
    {
        //calculate();
        //for(int j = 0; j <pic.height(); j++)
        for(int i = 0; i < width ; i++)
            te[i][0] = e[i][0];
        int[] seam = new int[height];
        
        for(int j = 1; j < height  ; j++)
            for(int i = 1; i < width - 1 ; i++)
        {
            double m1,m3; m1 = m3 = Double.POSITIVE_INFINITY;
            if(i - 1 > 0)
                m1 = te[i - 1][ j - 1];
            double m2 = te[i][ j - 1];
            if(i + 1 < width - 1)
                m3 = te[i + 1][j - 1];
            double min = (m1 < m2)? (m1 < m3 ? m1 : m3) : (m2 < m3 ? m2 : m3);
            te[i][j] = e[i][j] + min;
        }
        
        double min = Double.POSITIVE_INFINITY;
        int j = height - 1;
        for(int i = 1; i < width - 1; i++)
        {
            if( te[i][j] < min)
            {
                min = te[i][j];
                seam[j] = i;
            }
        }
        int i = seam[j];
        j--;
        while(j > 0)
        {
            double m1,m3; m1 = m3 = Double.POSITIVE_INFINITY;
            if(i - 1 > 0)
                m1 = te[i - 1][ j ];
            double m2 = te[i][ j ];
            if(i + 1 < width - 1)
                m3 = te[i + 1][j ];
            int m = (m1 < m2)? (m1 < m3 ? i - 1 : i + 1) : (m2 < m3 ? i : i + 1);
            seam[j] = m;
            i = m;
            j--;
        }
        if(seam.length >= 2)
            seam[0] = seam[1];
        //seam[pic.height() - 1] = seam[pic.height() - 2];
        return seam;
    }
    public    void removeVerticalSeam(int[] seam)
    {
        if(seam == null)
            throw new java.lang.IllegalArgumentException();
        
        for(int i = 1; i < seam.length; i++)
            if(seam[i] < 0 || seam[i] > width - 1 || Math.abs(seam[i] - seam[i - 1]) > 1)
            throw new java.lang.IllegalArgumentException();
        
        int p = 0;
        for(int j = 0; j < height; j++) 
        {
            for(int i = 0; i < width; i++)
            {
                if ( i == seam[p])
                {
                    for(int k = i + 1; k < width; k++)
                        col[k - 1][j] = col[k][j];
                    break;
                }
                
            }
            p++;
        }
        width -= 1;
    }
    public    void removeHorizontalSeam(int[] seam) 
    {
        if(seam == null)
            throw new java.lang.IllegalArgumentException();
        for(int i = 1; i < seam.length; i++)
            if(seam[i] < 0 || seam[i] > height - 1 || Math.abs(seam[i] - seam[i - 1]) > 1)
            throw new java.lang.IllegalArgumentException();
        
        int p = 0;
        for(int i = 0; i < width; i++) 
        {
            for(int j = 0; j < height; j++)
            {
                if ( j == seam[p])
                {
                    for(int k = j + 1; k < height; k++)
                        col[i][k - 1] = col[i][k];
                    break;
                }
                
            }
            p++;
        }
        height -= 1;
    }
    
}    
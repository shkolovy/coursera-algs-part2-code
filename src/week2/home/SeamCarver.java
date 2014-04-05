package week2.home;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.introcs.Picture;
import org.w3c.dom.css.RGBColor;

import java.awt.*;

/**
 * Created by Superman Petrovich on 4/3/14.
 */
public class SeamCarver {
    private final Picture picture;
    private final double borderPixelEnergy = 195075;
    private double[][] E;
    private int width;
    private int height;
    private boolean isTransposed;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        this.E = new double[this.height][this.width];
        this.isTransposed = false;
        fillEnergy();
    }

    public Picture picture(){
        //Picture p = new Picture(1,1);
        //p.set(1,1, new Color(1,1,1));

        return picture;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public double energy(int x, int y) {
        if(x < 0 || x > width() - 1 || y < 0 || y > height() - 1){
            throw new IndexOutOfBoundsException();
        }

        if(x == 0 || y == 0 || x == width() - 1 || y == height() - 1){
            return borderPixelEnergy;
        }

        return xGradient(x, y) + yGradient(x, y);
    }

    public int[] findHorizontalSeam() {
        if(!isTransposed){
            transpose();
        }

        return verticalSeam();
    }

    public int[] findVerticalSeam() {
        if(isTransposed){
            transpose();
        }

        return verticalSeam();
    }

    public void removeHorizontalSeam(int[] a) {
        if(width() <= 0 | height() <= 0){
            throw new IllegalArgumentException();
        }
    }

    public void removeVerticalSeam(int[] a) {
        if(width() <= 0 | height() <= 0){
            throw new IllegalArgumentException();
        }
    }

    private double yGradient(int x, int y){
        Color pTop = picture.get(x, y + 1);
        Color pBottom = picture.get(x, y - 1);

        return  Math.pow(pTop.getRed() - pBottom.getRed(), 2) +
                Math.pow(pTop.getGreen() - pBottom.getGreen(), 2) +
                Math.pow(pTop.getBlue() - pBottom.getBlue(), 2);
    }

    private double xGradient(int x, int y){
        Color pRight = picture.get(x + 1, y);
        Color pLeft = picture.get(x - 1, y);

        return  Math.pow(pRight.getRed() - pLeft.getRed(), 2) +
                Math.pow(pRight.getGreen() - pLeft.getGreen(), 2) +
                Math.pow(pRight.getBlue() - pLeft.getBlue(), 2);
    }

    private void fillEnergy(){
        for(int h = 0; h < this.height; h++){
            for(int w = 0; w < this.width; w++){
                E[h][w] = energy(w, h);
            }
        }
    }

    private void transpose(){
        int h = this.E[0].length;
        int w = this.E.length;

        double[][] transposeEnergy = new double[h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                transposeEnergy[i][j] = E[j][i];
            }
        }

        E = transposeEnergy;
        this.isTransposed = !this.isTransposed;
    }

    private int[] verticalSeam() {
        int w = this.E[0].length;
        int h = this.E.length;

        int[][] edgeTo = new int[h][w];
        double[][] energy = new double[h][w];

        for(int i = 0; i < h - 1; i++){
            for(int j = 0; j < w; j++){
                if(j-1 >= 0){
                    double tEnergy = this.E[i+1][j-1] + energy[i][j];
                    if(energy[i+1][j-1] == 0 || tEnergy < energy[i+1][j-1]){
                        edgeTo[i+1][j-1] = j;
                        energy[i+1][j-1] = tEnergy;
                    }
                }

                if(j+1 < w){
                    double tEnergy = this.E[i+1][j+1] + energy[i][j];
                    if(energy[i+1][j+1] == 0 || tEnergy < energy[i+1][j+1]){
                        edgeTo[i+1][j+1] = j;
                        energy[i+1][j+1] = tEnergy;
                    }
                }

                double tEnergy = this.E[i+1][j] + energy[i][j];
                if(energy[i+1][j] == 0 || tEnergy < energy[i+1][j]){
                    edgeTo[i+1][j] = j;
                    energy[i+1][j] = tEnergy;
                }
            }
        }

        double minEnergy = Integer.MAX_VALUE;
        int minLastP = 0;
        for(int i = 0; i < w; i++){
            if(energy[h - 1][i] < minEnergy){
                minEnergy = energy[h - 1][i];
                minLastP = i;
            }
        }

        int[] result = new int[h];
        int a = minLastP;

        for(int i = h - 1; i >= 0; i--){
            result[i] = a;
            a = edgeTo[i][a];
        }

        return result;
    }
}

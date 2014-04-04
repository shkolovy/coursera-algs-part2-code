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

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.E = new double[height()][width()];

        for(int h = 0; h < height(); h++){
            for(int w = 0; w < width(); w++){
                E[h][w] = energy(w, h);
            }
        }
    }

    public Picture picture(){
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
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

    public int[] findHorizontalSeam() {
        double[][] transposeEnergy = new double[width()][height()];
        for(int i = 0; i < width(); i++){
            for(int j = 0; j< height(); j++){
                transposeEnergy[i][j] = E[j][i];
            }
        }

        return findVerticalSeam(transposeEnergy, height(), width());
    }

    private int[] findVerticalSeam(double[][] energies, int width, int height) {
        int[][] edgeTo = new int[height][width];
        double[][] energy = new double[height][width];

        for(int h = 0; h < height - 1; h++){
            for(int w = 0; w < width; w++){
                if(w-1 >= 0){
                    double tEnergy = energies[h+1][w-1] + energy[h][w];
                    if(energy[h+1][w-1] == 0 || tEnergy < energy[h+1][w-1]){
                        edgeTo[h+1][w-1] = w;
                        energy[h+1][w-1] = tEnergy;
                    }
                }

                if(w+1 < width){
                    double tEnergy = energies[h+1][w+1] + energy[h][w];
                    if(energy[h+1][w+1] == 0 || tEnergy < energy[h+1][w+1]){
                        edgeTo[h+1][w+1] = w;
                        energy[h+1][w+1] = tEnergy;
                    }
                }

                double tEnergy = energies[h+1][w] + energy[h][w];
                if(energy[h+1][w] == 0 || tEnergy < energy[h+1][w]){
                    edgeTo[h+1][w] = w;
                    energy[h+1][w] = tEnergy;
                }
            }
        }

        double minEnergy = 1000000;
        int minLastP = 0;
        for(int i = 0; i < width; i++){
            if(energy[height - 1][i] < minEnergy){
                minEnergy = energy[height - 1][i];
                minLastP = i;
            }
        }

        int[] result = new int[height];
        int a = minLastP;

        for(int i = height - 1; i >= 0; i--){
            result[i] = a;
            a = edgeTo[i][a];
        }

        return result;
    }

    public int[] findVerticalSeam() {
        return findVerticalSeam(E, width(), height());
    }

    public void removeHorizontalSeam(int[] a) {
    }

    public void removeVerticalSeam(int[] a) {
    }
}

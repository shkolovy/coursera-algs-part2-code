package week2.home;

import edu.princeton.cs.introcs.Picture;

import java.awt.*;

public class SeamCarver {
    private final double borderPixelEnergy = 195075;
    private double[][] E;
    private Color[][] M;
    private boolean isTransposed;

    public SeamCarver(Picture picture) {
        this.E = new double[picture.height()][picture.width()];
        this.M = new Color[this.height()][this.width()];
        this.isTransposed = false;
        fillM(picture);
        fillEnergy();
    }

    public Picture picture() {
        if (isTransposed) {
            transpose();
        }

        int w = width();
        int h = height();

        Picture newPicture = new Picture(w, h);

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                newPicture.set(j, i, M[i][j]);
            }
        }

        return newPicture;
    }

    public int width() {
        return this.E[0].length;
    }

    public int height() {
        return this.E.length;
    }

    public double energy(int x, int y) {
        int h = height();
        int w = width();

        if (x < 0 || x > w - 1 || y < 0 || y > h - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (x == 0 || y == 0 || x == w - 1 || y == h - 1) {
            return borderPixelEnergy;
        }

        return xGradient(x, y) + yGradient(x, y);
    }

    public int[] findHorizontalSeam() {
        if (!isTransposed) {
            transpose();
        }

        return verticalSeam();
    }

    public int[] findVerticalSeam() {
        if (isTransposed) {
            transpose();
        }

        return verticalSeam();
    }

    public void removeHorizontalSeam(int[] a) {
        if (!isTransposed) {
            transpose();
        }

        vs(a);

        rVerticalSeam(a);
    }

    public void removeVerticalSeam(int[] a) {
        if (isTransposed) {
            transpose();
        }

        vs(a);

        rVerticalSeam(a);
    }

    private void vs(int[] seam){
        if (seam.length != this.E.length) {
            throw new java.lang.IllegalArgumentException();
        }

        Integer prev = seam[0];
        for (int i = 0; i < this.E.length; i++) {
            if (Math.abs(seam[i] - prev) > 1)
                throw new java.lang.IllegalArgumentException();
            prev = seam[i];
        }
    }

    private void rVerticalSeam(int[] a){
        int h = this.E.length;
        int w = this.E[0].length;

        for(int i = 0; i < h; i++){
            Color[] m = new Color[w-1];
            System.arraycopy(this.M[i], 0, m, 0, a[i]);
            System.arraycopy(this.M[i], a[i] + 1, m, a[i], m.length - a[i]);
            this.M[i] = m;

            double[] e = new double[w-1];
            System.arraycopy(this.E[i], 0, e, 0, a[i]);
            System.arraycopy(this.E[i], a[i] + 1, e, a[i], e.length - a[i]);
            this.E[i] = e;
        }

        refreshEnergy(a);
    }

    private void refreshEnergy(int[] a) {
        int h = height();
        int w = width();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if(j == a[i] || j == a[i] - 1){
                    E[i][j] = energy(j, i);
                }
            }
        }
    }

    private double yGradient(int x, int y) {
        Color pTop = this.M[y + 1][x];
        Color pBottom = this.M[y - 1][x];

        return Math.pow(pTop.getRed() - pBottom.getRed(), 2) +
                Math.pow(pTop.getGreen() - pBottom.getGreen(), 2) +
                Math.pow(pTop.getBlue() - pBottom.getBlue(), 2);
    }

    private double xGradient(int x, int y) {
        Color pRight = this.M[y][x + 1];
        Color pLeft = this.M[y][x - 1];

        return Math.pow(pRight.getRed() - pLeft.getRed(), 2) +
                Math.pow(pRight.getGreen() - pLeft.getGreen(), 2) +
                Math.pow(pRight.getBlue() - pLeft.getBlue(), 2);
    }

    private void fillM(Picture picture) {
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                M[h][w] = picture.get(w, h);
            }
        }
    }

    private void fillEnergy() {
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                E[h][w] = energy(w, h);
            }
        }
    }

    private void transpose() {
        int h = this.E[0].length;
        int w = this.E.length;

        double[][] transposeEnergy = new double[h][w];
        Color[][] transposeM = new Color[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                transposeEnergy[i][j] = E[j][i];
                transposeM[i][j] = M[j][i];
            }
        }

        E = transposeEnergy;
        M = transposeM;
        this.isTransposed = !this.isTransposed;
    }

    private int[] verticalSeam() {
        int w = width();
        int h = height();

        int[][] edgeTo = new int[h][w];
        double[][] energy = new double[h][w];

        for (int i = 0; i < h - 1; i++) {
            for (int j = 0; j < w; j++) {
                if(i == 0){
                    energy[i][j] = borderPixelEnergy;
                }

                if (j - 1 >= 0) {
                    double tEnergy = this.E[i + 1][j - 1] + energy[i][j];
                    if (energy[i + 1][j - 1] == 0 || tEnergy < energy[i + 1][j - 1]) {
                        edgeTo[i + 1][j - 1] = j;
                        energy[i + 1][j - 1] = tEnergy;
                    }
                }

                if (j + 1 < w) {
                    double tEnergy = this.E[i + 1][j + 1] + energy[i][j];
                    if (energy[i + 1][j + 1] == 0 || tEnergy < energy[i + 1][j + 1]) {
                        edgeTo[i + 1][j + 1] = j;
                        energy[i + 1][j + 1] = tEnergy;
                    }
                }

                double tEnergy = this.E[i + 1][j] + energy[i][j];
                if (energy[i + 1][j] == 0 || tEnergy < energy[i + 1][j]) {
                    edgeTo[i + 1][j] = j;
                    energy[i + 1][j] = tEnergy;
                }
            }
        }

        double minEnergy = Double.POSITIVE_INFINITY;
        int minLastP = 0;
        for (int i = 0; i < w; i++) {
            if (energy[h - 1][i] < minEnergy) {
                minEnergy = energy[h - 1][i];
                minLastP = i;
            }
        }

        int[] result = new int[h];
        int a = minLastP;

        for (int i = h - 1; i >= 0; i--) {
            result[i] = a;
            a = edgeTo[i][a];
        }

        return result;
    }
}
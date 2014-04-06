package week2.home;

import edu.princeton.cs.introcs.Picture;

import java.awt.Color;

public class SeamCarver {
    private final double BORDER_ENERGY = 195075;
    private double[][] energies;
    private int[][] colors;

    public SeamCarver(Picture pic) {
        this.energies = new double[pic.height()][pic.width()];
        this.colors = new int[pic.height()][pic.width()];

        fillColors(pic);
        fillEnergies();
    }

    public Picture picture() {
        Picture pic = new Picture(width(), height());

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                pic.set(j, i, new Color(colors[i][j]));
            }
        }

        return pic;
    }

    public int width() {
        return this.colors[0].length;
    }

    public int height() {
        return this.colors.length;
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return BORDER_ENERGY;
        }

        return xGradient(x, y) + yGradient(x, y);
    }

    public int[] findVerticalSeam() {
        int w = width();
        int h = height();

        int[][] edgeTo = new int[h][w];
        double[][] weights = new double[h][w];

        for (int i = 0; i < h - 1; i++) {
            for (int j = 0; j < w; j++) {
                if (j - 1 >= 0) {
                    double tEnergy = this.energies[i + 1][j - 1] + weights[i][j];
                    if (weights[i + 1][j - 1] == 0 || tEnergy < weights[i + 1][j - 1]) {
                        edgeTo[i + 1][j - 1] = j;
                        weights[i + 1][j - 1] = tEnergy;
                    }
                }

                if (j + 1 < w) {
                    double tEnergy = this.energies[i + 1][j + 1] + weights[i][j];
                    if (weights[i + 1][j + 1] == 0 || tEnergy < weights[i + 1][j + 1]) {
                        edgeTo[i + 1][j + 1] = j;
                        weights[i + 1][j + 1] = tEnergy;
                    }
                }

                double tEnergy = this.energies[i + 1][j] + weights[i][j];
                if (weights[i + 1][j] == 0 || tEnergy < weights[i + 1][j]) {
                    edgeTo[i + 1][j] = j;
                    weights[i + 1][j] = tEnergy;
                }
            }
        }

        double minEnergy = Double.POSITIVE_INFINITY;
        int minWidthIndex = 0;
        for (int i = 0; i < w; i++) {
            if (weights[h - 1][i] < minEnergy) {
                minEnergy = weights[h - 1][i];
                minWidthIndex = i;
            }
        }

        int[] seam = new int[h];
        int nextWidthIndex = minWidthIndex;

        for (int i = h - 1; i >= 0; i--) {
            seam[i] = nextWidthIndex;
            nextWidthIndex = edgeTo[i][nextWidthIndex];
        }

        return seam;
    }

    public int[] findHorizontalSeam() {
        int w = width();
        int h = height();

        int[][] edgeTo = new int[h][w];
        double[][] weights = new double[h][w];

        for (int i = 0; i < w - 1; i++) {
            for (int j = 0; j < h; j++) {
                if (j - 1 >= 0) {
                    double possibleWeight = this.energies[j - 1][i + 1] + weights[j][i];
                    if (weights[j - 1][i + 1] == 0 || possibleWeight < weights[j - 1][i + 1]) {
                        edgeTo[j - 1][i + 1] = j;
                        weights[j - 1][i + 1] = possibleWeight;
                    }
                }

                if (j + 1 < h) {
                    double possibleWeight = this.energies[j + 1][i + 1] + weights[j][i];
                    if (weights[j + 1][i + 1] == 0 || possibleWeight < weights[j + 1][i + 1]) {
                        edgeTo[j + 1][i + 1] = j;
                        weights[j + 1][i + 1] = possibleWeight;
                    }
                }

                double possibleWeight = this.energies[j][i + 1] + weights[j][i];
                if (weights[j][i + 1] == 0 || possibleWeight < weights[j][i + 1]) {
                    edgeTo[j][i + 1] = j;
                    weights[j][i + 1] = possibleWeight;
                }
            }
        }

        double minEnergy = Double.POSITIVE_INFINITY;
        int minHeightIndex = 0;

        for (int i = 0; i < h; i++) {
            if (weights[i][w - 1] < minEnergy) {
                minEnergy = weights[i][w - 1];
                minHeightIndex = i;
            }
        }

        int[] seam = new int[w];
        int nextHeightIndex = minHeightIndex;

        for (int i = w - 1; i >= 0; i--) {
            seam[i] = nextHeightIndex;
            nextHeightIndex = edgeTo[nextHeightIndex][i];
        }

        return seam;
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }

        //either an entry is outside its prescribed range or two adjacent entries differ by more than 1
        int prev = seam[0];
        for (int i = 0; i < height(); i++) {
            if (Math.abs(seam[i] - prev) > 1)
                throw new IllegalArgumentException();
            prev = seam[i];
        }

        int[][] c = new int[height()][width() - 1];
        double[][] e = new double[height()][width() - 1];

        for (int i = 0; i < height(); i++) {
            for (int j = 0, t = 0; j < width(); j++) {
                if (j != seam[i]) {
                    c[i][t] = this.colors[i][j];
                    e[i][t] = this.energies[i][j];
                    t++;
                }
            }
        }

        this.colors = c;
        this.energies = e;

        //refresh energies
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width() - 1; j++) {
                if(j == seam[i] || j == seam[i] - 1){
                    energies[i][j] = energy(j, i);
                }
            }
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }

        //either an entry is outside its prescribed range or two adjacent entries differ by more than 1
        int prev = seam[0];
        for (int i = 0; i < width(); i++) {
            if (Math.abs(seam[i] - prev) > 1)
                throw new IllegalArgumentException();
            prev = seam[i];
        }

        int[][] c = new int[height() - 1][width()];
        double[][] e = new double[height() - 1][width()];

        for (int i = 0; i < width(); i++) {
            for (int j = 0, t = 0; j < height(); j++) {
                if (j != seam[i]) {
                    c[t][i] = this.colors[j][i];
                    e[t][i] = this.energies[j][i];
                    t++;
                }
            }
        }

        this.colors = c;
        this.energies = e;

        //refresh energies
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height() - 1; j++) {
                if(j == seam[i] || j == seam[i] - 1){
                    energies[j][i] = energy(i, j);
                }
            }
        }
    }

    private double yGradient(int x, int y) {
        Color pTop = new Color(this.colors[y + 1][x]);
        Color pBottom = new Color(this.colors[y - 1][x]);

        return Math.pow(pTop.getRed() - pBottom.getRed(), 2) +
                Math.pow(pTop.getGreen() - pBottom.getGreen(), 2) +
                Math.pow(pTop.getBlue() - pBottom.getBlue(), 2);
    }

    private double xGradient(int x, int y) {
        Color pRight = new Color(this.colors[y][x + 1]);
        Color pLeft = new Color(this.colors[y][x - 1]);

        return Math.pow(pRight.getRed() - pLeft.getRed(), 2) +
                Math.pow(pRight.getGreen() - pLeft.getGreen(), 2) +
                Math.pow(pRight.getBlue() - pLeft.getBlue(), 2);
    }

    private void fillEnergies() {
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                energies[h][w] = energy(w, h);
            }
        }
    }

    private void fillColors(Picture pic) {
        for (int h = 0; h < height(); h++) {
            for (int w = 0; w < width(); w++) {
                this.colors[h][w] = pic.get(w, h).getRGB();
            }
        }
    }
}
package com.example.dndterraingen;

import java.util.ArrayList;
import java.util.Random;

public class Terrain {
    int sizex; // Size along X-axis
    int sizey; // Size along Y-axis
    int mode; // Generation mode
    long seed; // Seed for random number generator
    ArrayList<ArrayList<Integer>> terrainArray = new ArrayList<>(); // 2D array to store terrain

    // Terrain types
    public static final int WATER = 0;
    public static final int FOREST = 1;
    public static final int PLAIN = 2;
    public static final int MOUNTAIN = 3;

    Terrain() {
        sizex = 3;
        sizey = 3;
        mode = 0;
        seed = System.currentTimeMillis(); // Default seed is current time
    }

    Terrain(int sizex, int sizey, int mode, long seed) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.mode = mode;
        this.seed = seed;
    }

    public void Generator() {

        terrainArray.clear(); // Clear existing terrain data

        // Generate basic terrain using Perlin noise
        for (int i = 0; i < sizey; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < sizex; j++) {
                double x = j * 0.1;
                double y = i * 0.1;
                double noiseValue = perlinNoise(x, y);
                int terrainType = getTerrainType(noiseValue);
                row.add(terrainType);
            }
            terrainArray.add(row);
        }
    }

    private int getTerrainType(double noiseValue) {
        if (noiseValue < -0.3) {
            return WATER;
        } else if (noiseValue < 0.0) {
            return FOREST;
        } else if (noiseValue < 0.3) {
            return PLAIN;
        } else {
            return MOUNTAIN;
        }
    }

    private double perlinNoise(double x, double y) {
        int x0 = (int) Math.floor(x);
        int x1 = x0 + 1;
        int y0 = (int) Math.floor(y);
        int y1 = y0 + 1;

        double sx = x - (double) x0;
        double sy = y - (double) y0;

        double n0, n1, ix0, ix1, value;
        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = interpolate(n0, n1, sx);
        n0 = dotGridGradient(x0, y1, x, y);
        n1 = dotGridGradient(x1, y1, x, y);
        ix1 = interpolate(n0, n1, sx);
        value = interpolate(ix0, ix1, sy);

        return value;
    }

    private double dotGridGradient(int ix, int iy, double x, double y) {
        double dx = x - (double) ix;
        double dy = y - (double) iy;
        return (dx * randomGradient(ix, iy).x + dy * randomGradient(ix, iy).y);
    }

    private Vector2 randomGradient(int ix, int iy) {
        Random random = new Random(seed + (ix * 1741L + iy * 3079L));
        double angle = random.nextDouble() * 2 * Math.PI;
        return new Vector2(Math.cos(angle), Math.sin(angle));
    }

    private double interpolate(double a0, double a1, double w) {
        return (a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0;
    }

    private static class Vector2 {
        double x, y;
        Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}

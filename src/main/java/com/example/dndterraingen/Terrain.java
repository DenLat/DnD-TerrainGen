package com.example.dndterraingen;

import java.util.ArrayList;
import java.util.Random;

public class Terrain {
    // Class fields
    int sizex; // Size along X-axis
    int sizey; // Size along Y-axis
    int mode; // Generation mode
    long seed; // Seed for random number generator
    ArrayList<ArrayList<Integer>> terrainArray = new ArrayList<>(); // 2D array to store terrain

    // Default constructor
    Terrain() {
        sizex = 3;
        sizey = 3;
        mode = 0;
        seed = System.currentTimeMillis(); // Default seed is current time
    }

    // Parameterized constructor
    Terrain(int sizex, int sizey, int mode, long seed) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.mode = mode;
        this.seed = seed;
    }

    // Method to generate terrain using Perlin noise
    public void Generator() {
        Random random = new Random(seed);
        double frequency = 0.1;
        double amplitude = 10;

        terrainArray.clear(); // Clear existing terrain data

        for (int i = 0; i < sizey; i++) { // Note: sizey is used for rows
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < sizex; j++) { // Note: sizex is used for columns
                double x = j * frequency;
                double y = i * frequency;
                int value = (int) ((perlinNoise(x, y) + 1) * 0.5 * amplitude); // Normalize to 0-10 range
                row.add(value);
            }
            terrainArray.add(row);
        }
    }

    // Perlin noise implementation
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
        // Compute the distance vector
        double dx = x - (double) ix;
        double dy = y - (double) iy;

        // Compute the dot-product
        return (dx * randomGradient(ix, iy).x + dy * randomGradient(ix, iy).y);
    }

    private Vector2 randomGradient(int ix, int iy) {
        // Use the class seed to initialize the random generator
        Random random = new Random(seed + (ix * 1741L + iy * 3079L));
        double angle = random.nextDouble() * 2 * Math.PI;
        return new Vector2(Math.cos(angle), Math.sin(angle));
    }

    private double interpolate(double a0, double a1, double w) {
        // Smoothstep
        return (a1 - a0) * ((w * (w * 6.0 - 15.0) + 10.0) * w * w * w) + a0;
    }

    // Helper class for 2D vector
    private static class Vector2 {
        double x, y;
        Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Method to output terrain
    public String Output() {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Integer> row : terrainArray) {
            for (Integer value : row) {
                sb.append(String.format("%2d ", value));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

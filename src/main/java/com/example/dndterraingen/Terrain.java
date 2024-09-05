package com.example.dndterraingen;

import java.util.ArrayList;

public class Terrain{
    int sizex;
    int sizey;
    int mode;
    ArrayList<ArrayList<Integer>> terrainArray = new ArrayList<>();

    Terrain(){
        sizex = 3;
        sizey = 3;
        mode = 0;
    }
    Terrain(int sizex, int sizey, int mode){
        this.sizex = sizex;
        this.sizey = sizey;
        this.mode = mode;
    }
    public void Generator(){
        for (int i = 0; i < sizex; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < sizey; j++) {
                row.add(0); // Initialize each element with 0
            }
            terrainArray.add(row);
        }
    }
    public void Output(){
        for (int i = 0; i < terrainArray.size(); i++) {
            for (int j = 0; j < terrainArray.get(i).size(); j++) {
                System.out.print(terrainArray.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}

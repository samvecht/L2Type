package com.sam.l2type;

import android.support.annotation.StringRes;
import android.util.Log;

/**
 * A single pack of levels made to be played together
 * Created by Sam on 11/2/2015.
 */
public class LevelPack {

    private int id;
    private String name;
    private int numLevels;
    private String[] levels;

    //Default Constructor
    public LevelPack() {
        this(0, "", 0, new String[10]);
    }

    /**
     * Constructor
     * @param id the packs id#
     * @param name the packs name
     * @param numLevels the number of levels in the pack
     * @param levels each String in the array is a single level
     * */
    public LevelPack(int id, String name, int numLevels, String[] levels) {
        this.id = id;
        this.name = name;
        this.numLevels = numLevels;
        this.levels = new String[10];
        for(int i = 0; i < levels.length; i++) {
            this.levels[i] = levels[i];
        }
    }

    //Getters

    /**
     * Gets a packs ID number
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a packs name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets a packs number of levels
     * @return numLevels
     */
    public int getNumLevels() {
        return numLevels;
    }

    /**
     * Gets a level(0 through numLevels-1 inclusive from the pack)
     * @return a single level
     */
    public String getLevel(int i) {
        if(i >= 0 && i < numLevels) {
            return levels[i];
        }
        else return "";
    }

    //setters

    /**
     * Sets the packs ID
     * @param id this packs unique id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the packs name
     * @param name this packs name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the packs numLevels
     * @param numLevels the number of levels the  pack has
     */
    public void setNumLevels(int numLevels) {
        this.numLevels = numLevels;
    }

    /**
     * Sets a single level of the pack
     * @param index a level 0-9 inclusive
     * @param level the levels text
     */
    public void setLevel(int index, String level){
        if(index >= 0 && index <= 9) {
            levels[index] = level;
        }
    }

    @Override
    public String toString() {
        String result = "Pack #" + id + " " + name + " levels: " + numLevels;
        for(int i = 0; i < numLevels; i++) {
            result += ("{" + levels[i] + "}");
        }
        return result;
    }
}

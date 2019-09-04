// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.server;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {

    private ConcurrentHashMap<String, ArrayList<String>> dictionary = new ConcurrentHashMap<String, ArrayList<String>>();
    private Gson gson = new Gson();

    public Dictionary(File file){

        try {
            FileReader in = new FileReader(file);
            this.dictionary = gson.fromJson(in, dictionary.getClass());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean ContainsWord(String word){
        return dictionary.containsKey(word);
    }

    public ArrayList<String> QueryWord(String word){
        return this.dictionary.get(word);
    }

    public void DeleteWord(String word){
        this.dictionary.remove(word);
    }

    public void AddWord(String word, ArrayList<String> definition){
        this.dictionary.put(word, definition);
    }

    public String SerializeDictionary(){
        return gson.toJson(dictionary);
    }







}

package ctatli.server;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {

    private ConcurrentHashMap<String, String> dictionary = new ConcurrentHashMap<String, String>();
    private Gson gson = new Gson();

    public Dictionary(File file){

        try {
            FileReader in = new FileReader(file);
            this.dictionary = gson.fromJson(in, dictionary.getClass());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String queryWord(String word){
        return this.dictionary.get(word);
    }

    public void deleteWord(String word){
        this.dictionary.remove(word);
    }

    public void addWord(String word, String definition){
        this.dictionary.put(word, definition);
    }







}

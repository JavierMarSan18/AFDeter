package edu.jarkvin.model;

import com.google.gson.Gson;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileManager extends Thread{
    private static final String PATH = "src/main/resources/files/";
    private File file = null;
    private Automata a = null;
    private FileReader fr = null;
    private BufferedReader br = null;
    private Gson gson = new Gson();

    @Override
    public void run() {
        file = new File(PATH);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        if (!file.exists()){
            file.mkdirs();
        }
    }

    public FileManager(){
        start();
    }

    public void save(Automata a) {
        try {
            file = new File(PATH+a.getNombre()+".txt");
            ThreadFileWriter afw = new ThreadFileWriter(a,file);
            afw.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> readAllFiles(){
        file = new File(PATH);
        return Arrays.stream(Objects.requireNonNull(file.list())).toList();
    }

    public Optional<Automata> readFile(String fileName){
        Optional<Automata> o = Optional.empty();
        file = new File(PATH+fileName+".txt");
        if (file.exists()){
            try{
                StringBuilder json = new StringBuilder();
                fr = new FileReader(file);
                br = new BufferedReader(fr);

                String str;
                while ((str=br.readLine()) != null){
                    json.append(str);
                }

                if (json.toString().length() > 0){
                    o = Optional.of(jsonToObject(json.toString()));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return o;
    }

    private Automata jsonToObject(String json){
        return gson.fromJson(json, Automata.class);
    }
}

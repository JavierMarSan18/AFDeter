package edu.jarkvin.model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ThreadFileWriter extends Thread{
    private final Automata a;
    private final File file;
    private FileWriter fw = null;
    private PrintWriter pw = null;
    private final Gson gson = new Gson();

    public ThreadFileWriter(Automata a, File file){
        this.a = a;
        this.file = file;
    }

    @Override
    public void run() {
        super.run();
        try{
            file.createNewFile();
            fw = new FileWriter(file);
            pw = new PrintWriter(fw);
            pw.println(gson.toJson(a));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fw.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

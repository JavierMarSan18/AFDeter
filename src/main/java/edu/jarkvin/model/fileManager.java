package edu.jarkvin.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
    private FileWriter archivo = null;
    private PrintWriter escritor = null;

    public Writer() throws IOException {
        try{
            archivo = new FileWriter("src/main/resources/txtFiles/automatas.txt");
            escritor = new PrintWriter(archivo);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert archivo != null;
            archivo.close();
        }
    }

    public void write(Automata a){
        escritor.
    }
}

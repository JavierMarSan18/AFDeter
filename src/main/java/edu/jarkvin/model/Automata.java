package edu.jarkvin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Automata {
    private String nombre;
    private List<Estado> estados;

    public Automata(String nombre){
        this.nombre = nombre;
        this.estados = new ArrayList<>();
    }

    public Automata(){
        this.estados = new ArrayList<>();
    }
}

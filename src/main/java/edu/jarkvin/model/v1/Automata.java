package edu.jarkvin.model.v1;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Automata {
    private boolean esDeterminista;
    private List<Estado> estados;

    public Automata(boolean esDeterminista) {
        this.esDeterminista = esDeterminista;
        this.estados = new ArrayList<>();
    }

    public Automata() {
        this.estados = new ArrayList<>();
    }

    public void agregarEstado(int index, Estado e){
        this.estados.add(index,e);
    }

    public void eliminarEstado(int index){
        this.estados.remove(index);
    }

    public void agregarEstado(Estado e){
        if(estados.stream().noneMatch(thisE -> thisE.isEInicial() == e.isEInicial())){
            estados.add(e);
        }
    }

    public void eliminarEstado(Estado e){
        this.estados.remove(e);
    }
}

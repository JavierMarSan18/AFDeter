package edu.jarkvin.model.v1;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@EqualsAndHashCode
public class Estado {
    private boolean eInicial;
    private boolean eFinal;
    private List<Transicion> transiciones;

    public Estado() {
        this.transiciones = new ArrayList<>();
    }

    public Estado(boolean esInicial, boolean esFinal) {
        this.eInicial = esInicial;
        this.eFinal = eFinal;
        this.transiciones = new ArrayList<>();
    }

    public Optional<Transicion> getTransicion(String c){
        return transiciones.stream().findFirst().filter(t -> t.getValor().equals(c));
    }

    public boolean existeTransicion(String c){
        return getTransicion(c).isPresent();
    }

    public void agregarTransicion(int index, Transicion t){
        this.transiciones.add(index,t);
    }

    public void eliminarTransicion(int index){
        this.transiciones.remove(index);
    }

    public void agregarTransicion(Transicion t){
        if(transiciones.stream()
                .noneMatch(thisT -> thisT.getValor().equals(t.getValor()) && thisT.getOrigen().equals(t.getOrigen()))){
            transiciones.add(t);
        }
    }

    public void eliminarTransicion(Transicion t){
        this.transiciones.remove(t);
    }
}

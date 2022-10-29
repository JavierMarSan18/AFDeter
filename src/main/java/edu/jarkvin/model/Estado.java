package edu.jarkvin.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@EqualsAndHashCode
public class Estado {

    private String nombre;
    private boolean eInicial;
    private boolean eFinal;
    private Map<String, String> salidas;


    public Estado() {
        this.salidas = new HashMap<>();
    }

    public Estado(String nombre, boolean eInicial, boolean eFinal) {
        this.nombre = nombre;
        this.eInicial = eInicial;
        this.eFinal = eFinal;
        this.salidas = new HashMap<>();
    }

    public void agregarSalida(String sq, String se){
        salidas.put(sq, se);
    }

    public void agregarSalida(Transicion t){
        salidas.put(t.getValor(), t.getDestino());
    }
}

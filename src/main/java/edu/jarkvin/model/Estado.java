package edu.jarkvin.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@EqualsAndHashCode
public class Estado {

    private String nombre;
    private boolean eInicial;
    private boolean eFinal;
    private Map<String, String> transiciones;


    public Estado() {
        this.transiciones = new HashMap<>();
    }

    public Estado(String nombre, boolean eInicial, boolean eFinal) {
        this.nombre = nombre;
        this.eInicial = eInicial;
        this.eFinal = eFinal;
        this.transiciones = new HashMap<>();
    }

    public void agregarTransicion(String sq, String se){
        if(transiciones.containsKey(sq)){
            System.out.println("--------------------------------------------------");
            System.out.println("Este estado ya contiene una transición con '"+sq+"'");
            System.out.println(nombre+"--"+sq+"-->"+transiciones.get(sq));
            System.out.println("--------------------------------------------------");
        }
        transiciones.put(sq, se);
    }

    public void agregarTransicion(Transicion t){
        agregarTransicion(t.getValor(), t.getDestino());
    }
}

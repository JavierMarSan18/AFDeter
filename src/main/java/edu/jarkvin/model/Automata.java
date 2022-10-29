package edu.jarkvin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Automata {
    private final Scanner scanner = new Scanner(System.in);
    public Automata(){
    }

    public void start() {
        System.out.println("-------------------");
        System.out.println("       AFDeter     ");
        System.out.println("-------------------");
        System.out.println("Menu:              ");
        System.out.println("-------------------");
        System.out.println("1. Crear AFD       ");
        System.out.println("0. Salir           ");
        System.out.println("-------------------");
        System.out.print(".: ");
        switch (scanner.nextLine()){
            case "0":
                break;
            case "1":
                agregarEstados();
                break;
            default:
                start();
                break;
        }
    }
    private void agregarEstados() {
        List<Estado> estados = new ArrayList<>();
        do {
            estados.add(ingresarNuevoEstado());
        }while (ingresarNuevoEstado_Mensaje());

        if (!(existeEstadoInicial(estados) && existeEstadoFinal(estados))){
            System.out.println("No existe estado INICIAL y/o FINAL");
            start();
        }

        ingresarPalabra(estados);
    }
    private Estado ingresarNuevoEstado() {
        Estado e = new Estado();
        System.out.println("-----------------------------");
        System.out.println("Ingresa el nombre del estado");
        System.out.println("-----------------------------");
        System.out.print(".:");
        e.setNombre(scanner.nextLine());
        System.out.println("-----------------------------");
        System.out.println("¿Es estado INICIAL? Sí(Y) No(N)");
        System.out.println("-----------------------------");
        System.out.print(".:");
        e.setEInicial(scanner.nextLine().equalsIgnoreCase("Y"));
        System.out.println("-----------------------------");
        System.out.println("¿Es estado FINAL? Sí(Y) No(N)");
        System.out.println("-----------------------------");
        System.out.print(".:");
        e.setEFinal(scanner.nextLine().equalsIgnoreCase("Y"));
        do {
            e.agregarSalida(ingresarNuevaTransicion());
        }while (ingresarNuevaTransicion_Mensaje());
        return e;
    }

    private Transicion ingresarNuevaTransicion() {
        Transicion t = new Transicion();
        System.out.println("-----------------------------");
        System.out.println("Ingresa una transición       ");
        System.out.println("-----------------------------");
        System.out.println("Valor:                       ");
        System.out.println("-----------------------------");
        System.out.print(".:");
        t.setValor(scanner.nextLine());
        System.out.println("-----------------------------");
        System.out.println("Estado de destino:           ");
        System.out.println("-----------------------------");
        System.out.print(".:");
        t.setDestino(scanner.nextLine());
        return t;
    }

    private  boolean existeEstadoFinal(List<Estado> estados) {
        return estados.stream().anyMatch(Estado::isEFinal);
    }

    private  boolean existeEstadoInicial(List<Estado> estados) {
        return estados.stream().anyMatch(Estado::isEInicial);
    }

    private  Optional<Estado> obtenerEstadoInicial(List<Estado> estados) {
        return estados.stream().filter(Estado::isEInicial).findFirst();
    }

    public void ingresarPalabra(List<Estado> estados) {
        String palabra;
        do {
            System.out.println("-------------------");
            System.out.println("Ingresa una palabra");
            System.out.println("-------------------");
            palabra = scanner.nextLine();

            System.out.println("\n\n");
            imprimirTablaDeTransiciones(estados);

            if(esValida(estados, palabra)){
                System.out.println("La palabra '"+palabra+"' es válida");
            }else {
                System.out.println("La palabra '"+palabra+"' NO es válida");
            }
        }while (ingresarNuevaPalabra_Mensaje());
        start();
    }

    private  Optional<Estado> obtenerSiguienteEstado(List<Estado> estados, Estado eActual, String t) {
        return estados
                .stream()
                .filter(e ->  e.getNombre().equals(eActual.getSalidas().get(t)))
                .findFirst();
    }

    public boolean esValida(List<Estado> estados, String p) {
        Optional<Estado> estado = obtenerEstadoInicial(estados);
        List<String> pList = convertirAListaDeCaracteres(p);
        boolean esValida = false;
        int pLongitud = pList.size();

        AtomicInteger i = new AtomicInteger(0);

        while (pLongitud >= i.get()){
            if (pLongitud > i.get()){
                if (estado.get().getSalidas().containsKey(pList.get(i.get()))){
                    estado = obtenerSiguienteEstado(estados, estado.get() , pList.get(i.get()));
                }
            }

            if (pLongitud == i.get() && estado.get().isEFinal()){
                esValida = true;
                break;
            }
            i.incrementAndGet();
        }
        return esValida;
    }

    private  void imprimirTablaDeTransiciones(List<Estado> estados) {
        System.out.println("=====================");
        estados.forEach(e -> {
            if (e.isEInicial()) System.out.println("INICIAL --> "+e.getNombre());
            if (e.isEFinal()) System.out.println("FINAL   --> "+e.getNombre());
        });
        System.out.println("=====================");
        estados.forEach(e -> {
            e.getSalidas().forEach((k,v) -> {
                System.out.println(e.getNombre()+"--"+k+"-->"+v);

            });
        });
        System.out.println("=====================");
    }

    private boolean ingresarNuevoEstado_Mensaje() {
        System.out.println("¿Desea ingresar un nuevo ESTADO? Sí(Y) No(N)");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private  boolean ingresarNuevaTransicion_Mensaje() {
        System.out.println("¿Desea ingresar una nueva TRANSICIÓN? Sí(Y) No(N)");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private  boolean ingresarNuevaPalabra_Mensaje() {
        System.out.println("¿Desea ingresar una nueva palabra? Sí(Y) No(N)");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private  List<String> convertirAListaDeCaracteres(String s) {
        char[] chars = s.toCharArray();
        List<String> lista = new ArrayList<>();
        for (char c:
                chars) {
            lista.add(String.valueOf(c));
        }
        return lista;
    }
}

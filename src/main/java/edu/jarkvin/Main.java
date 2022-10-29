package edu.jarkvin;

import edu.jarkvin.model.v1.Automata;
import edu.jarkvin.model.v1.Estado;
import edu.jarkvin.model.v1.Transicion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Automata automata = new Automata();
    public static void main(String[] args) {
        init();
    }

    private static void init() {
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
                crearAutomata();
                break;
            default:
                init();
                break;
        }
    }

    private static void crearAutomata() {
        do {
            Estado q0 = new Estado(true, false);
            q0.agregarTransicion(Transicion
                    .builder()
                    .valor("a")
                    .destino("q1")
                    .origen("q0")
                    .build());

            Estado q1 = new Estado(false, true);
            q1.agregarTransicion(Transicion
                    .builder()
                    .valor("a")
                    .destino("q0")
                    .origen("q1")
                    .build());

            automata.agregarEstado(q0);
            automata.agregarEstado(q1);
        }while (!(existeEstadoInicial() && existeEstadoFinal()) && ingresarNuevoEstado());
        realizarPrueba();
    }

    private static void realizarPrueba() {
        String palabra;
        do {
            System.out.println("-------------------");
            System.out.println("Ingresa una palabra");
            System.out.println("-------------------");
            palabra = scanner.nextLine();

            if(isValida(palabra)){
                System.out.println("La palabra '"+palabra+"' es válida");
            }else {
                System.out.println("La palabra '"+palabra+"' NO es válida");
            }
        }while (ingresarNuevaPalabra());
        automata.getEstados().removeAll(automata.getEstados());
        init();
    }

    private static boolean isValida(String s) {
        boolean esValida = false;
        List<String> palabra = convertirAListaDeCaracteres(s);

        Estado estado = automata.getEstados().stream().findFirst().filter(Estado::isEInicial).get();
        while (!palabra.isEmpty()){

            if(palabra.isEmpty() && estado.isEFinal()){
                esValida =  true;
                break;
            }
            if(palabra.isEmpty() && !estado.isEFinal()){
                break;
            }


        }

        return esValida;
    }

    private static List<String> recorrerPalabra(List<String> p, Estado e) {
        return new ArrayList<>();
    }

    private static boolean ingresarNuevoEstado() {
        System.out.println("¿Desea ingresar un nuevo estado? Sí(Y) No(N)");
        return scanner.nextLine().equals("Y");
    }

    private static boolean ingresarNuevaPalabra() {
        System.out.println("¿Desea ingresar una nueva palabra? Sí(Y) No(N)");
        return scanner.nextLine().equals("Y");
    }

    private static boolean existeEstadoFinal() {
        return automata.getEstados().stream().anyMatch(Estado::isEFinal);
    }

    private static boolean existeEstadoInicial() {
        return automata.getEstados().stream().anyMatch(Estado::isEInicial);
    }

    private static boolean existenEstados() {
        if(automata.getEstados().isEmpty()){
            System.out.println("No existen estados en el autómata.");
            return false;
        }
        return true;
    }

    private static List<String> convertirAListaDeCaracteres(String s) {
        char[] chars = s.toCharArray();
        List<String> lista = new ArrayList<>();
        for (char c:
             chars) {
            lista.add(String.valueOf(c));
        }
        return lista;
    }
}

package edu.jarkvin;

import edu.jarkvin.model.Automata;
import edu.jarkvin.model.Estado;
import edu.jarkvin.model.FileManager;
import edu.jarkvin.model.Transicion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final FileManager fileManager = new FileManager();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        start();
    }
    public static void start() {
        System.out.println("--------------------");
        System.out.println("       AFDeter      ");
        System.out.println("--------------------");
        System.out.println("Menu:               ");
        System.out.println("--------------------");
        System.out.println("1. Crear AFD        ");
        System.out.println("2. Ver AFD guardados");
        System.out.println("0. Salir            ");
        System.out.println("--------------------");
        System.out.print(".: ");
        switch (scanner.nextLine()){
            case "0":
                System.exit(0);
                break;
            case "1":
                crearAutomata();
                break;
            case "2":
                verAutomatas();
                break;
            default:
                start();
                break;
        }
    }

    private static void verAutomatas() {
        List<String> files = fileManager.readAllFiles()
                                        .stream()
                                        .map(f -> f.replace(".txt",""))
                                        .toList();

        if (!files.isEmpty()){
            System.out.println("------------------------");
            System.out.println("|| Automatas guardados ||");
            System.out.println("-------------------------");
            files.forEach(f -> System.out.println("--> "+f));
            System.out.println("-------------------------");
            System.out.println("Ingresa el nombre del AFD");
            System.out.println("-------------------------");
            System.out.print(".:");
            Optional<Automata> o = fileManager.readFile(scanner.nextLine());
            if (o.isEmpty()){
                System.out.println("-----------------------------");
                System.out.println("El AFD seleccionado no existe");
                System.out.println("o esta vacio.                ");
                System.out.println("-----------------------------");
                start();
            }
            imprimirTablaDeTransiciones(o.get().getEstados());
            ingresarPalabra(o.get().getEstados());
        }else {
            System.out.println("-----------------------------------");
            System.out.println("|| No existe automatas guardados ||");
            System.out.println("-----------------------------------");
            start();
        }
    }

    private static void crearAutomata() {
        Automata automata = new Automata();
        do {
            automata.getEstados().add(ingresarNuevoEstado());
        }while (ingresarNuevoEstado_Mensaje());

        if (!(existeEstadoInicial(automata.getEstados()) && existeEstadoFinal(automata.getEstados()))){
            System.out.println("---------------------------------------");
            System.out.println("|| No existe estado INICIAL y/o FINAL||");
            System.out.println("---------------------------------------");
            start();
        }
        guardarAutomata(automata);
        ingresarPalabra(automata.getEstados());
    }

    private static void guardarAutomata(Automata automata) {
        System.out.println("----------------------------------------");
        System.out.println("Desea guardar este automata? Si(Y) No(N)");
        System.out.println("----------------------------------------");
        System.out.print(".:");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            System.out.println("------------------------------");
            System.out.println("Ingresa el nombre del automata");
            System.out.println("------------------------------");
            System.out.print(".:");
            automata.setNombre(scanner.nextLine());
            fileManager.save(automata);
        }
    }

    private static Estado ingresarNuevoEstado() {
        Estado e = new Estado();
        System.out.println("-----------------------------");
        System.out.println("Ingresa el nombre del estado");
        System.out.println("-----------------------------");
        System.out.print(".:");
        e.setNombre(scanner.nextLine());
        System.out.println("-----------------------------");
        System.out.println("Es estado INICIAL? Si(Y) No(N)");
        System.out.println("-----------------------------");
        System.out.print(".:");
        e.setEInicial(scanner.nextLine().equalsIgnoreCase("Y"));
        System.out.println("-----------------------------");
        System.out.println("Es estado FINAL? Si(Y) No(N)");
        System.out.println("-----------------------------");
        System.out.print(".:");
        e.setEFinal(scanner.nextLine().equalsIgnoreCase("Y"));
        while (ingresarNuevaTransicion_Mensaje()){
            e.agregarTransicion(ingresarNuevaTransicion());
        }
        return e;
    }

    private static Transicion ingresarNuevaTransicion() {
        Transicion t = new Transicion();
        System.out.println("-----------------------------");
        System.out.println("Ingresa una transicion       ");
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

    private static boolean existeEstadoFinal(List<Estado> estados) {
        return estados.stream().anyMatch(Estado::isEFinal);
    }

    private static boolean existeEstadoInicial(List<Estado> estados) {
        return estados.stream().anyMatch(Estado::isEInicial);
    }

    private static Optional<Estado> obtenerEstadoInicial(List<Estado> estados) {
        return estados.stream().filter(Estado::isEInicial).findFirst();
    }

    public static void ingresarPalabra(List<Estado> estados) {
        String palabra;
        do {
            System.out.println("-------------------");
            System.out.println("Ingresa una palabra");
            System.out.println("-------------------");
            palabra = scanner.nextLine();

            imprimirTablaDeTransiciones(estados);

            if(esValida(estados, palabra)){
                System.out.println("La palabra '"+palabra+"' es valida");
            }else {
                System.out.println("La palabra '"+palabra+"' NO es valida");
            }
        }while (ingresarNuevaPalabra_Mensaje());
        start();
    }

    private static Optional<Estado> obtenerSiguienteEstado(List<Estado> estados, Estado eActual, String t) {
        return estados
                .stream()
                .filter(e -> e.getNombre().equals(eActual.getTransiciones().get(t)))
                .findFirst();
    }

    public static boolean esValida(List<Estado> estados, String p) {
        Optional<Estado> estado = obtenerEstadoInicial(estados);
        boolean esValida = false;

        if(estado.isPresent() && estado.get().getTransiciones().size() > 0){
            List<String> pList = convertirAListaDeCaracteres(p);
            int pLongitud = pList.size();
            AtomicInteger i = new AtomicInteger(0);

            while (pLongitud >= i.get()){
                if (pLongitud > i.get()){
                    if (estado.isPresent() && estado.get().getTransiciones().containsKey(pList.get(i.get()))){
                        estado = obtenerSiguienteEstado(estados, estado.get() , pList.get(i.get()));
                    }
                }

                if (pLongitud == i.get() && estado.isPresent() && estado.get().isEFinal()){
                    esValida = true;
                    break;
                }
                i.incrementAndGet();
            }
        }
        return esValida;
    }

    private static void imprimirTablaDeTransiciones(List<Estado> estados) {
        System.out.println("\n\n=====================");
        estados.forEach(e -> {
            if (e.isEInicial()) System.out.println("INICIAL --> "+e.getNombre());
            if (e.isEFinal()) System.out.println("FINAL   --> "+e.getNombre());
        });
        System.out.println("=====================");
        estados.forEach(e -> {
            e.getTransiciones().forEach((k,v) -> {
                System.out.println(e.getNombre()+"--"+k+"-->"+v);

            });
        });
        System.out.println("=====================");
    }

    private static boolean ingresarNuevoEstado_Mensaje() {
        System.out.println("Desea ingresar un nuevo ESTADO? Si(Y) No(N)");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private static boolean ingresarNuevaTransicion_Mensaje() {
        System.out.println("Desea ingresar una nueva TRANSICION? Si(Y) No(N)");
        return scanner.nextLine().equalsIgnoreCase("Y");
    }

    private static boolean ingresarNuevaPalabra_Mensaje() {
        System.out.println("Desea ingresar una nueva palabra? Si(Y) No(N)");
        return scanner.nextLine().equalsIgnoreCase("Y");
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

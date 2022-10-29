package edu.jarkvin;

import edu.jarkvin.model.Estado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Main2 {
    public static void main(String[] args) {
        Estado q0 = new Estado("q0", true, false);
        q0.agregarSalida("a","q1");

        Estado q1 = new Estado("q1", false, false);
        q1.agregarSalida("a","q2");

        Estado q2 = new Estado("q2", false, true);
        q2.agregarSalida("a","q1");

        List<Estado> estados = new ArrayList<>();
        estados.add(q0);
        estados.add(q1);
        estados.add(q2);

        String palabra = "aaaaaaaa";

        if(validarEntrada(estados, palabra)){
            System.out.println("Es Valida");
        }else {
            System.out.println("No es Valida");
        }
    }

    private static boolean validarEntrada(List<Estado> estados, String p) {
        Optional<Estado> estado = obtenerEstadoInicial(estados);
        List<String> pList = convertirAListaDeCaracteres(p);
        boolean esValida = false;
        int pLongitud = pList.size();


        AtomicInteger i = new AtomicInteger(0);

        while (pLongitud >= i.get()){
            System.out.println(i.get()+":"+estado.get().getNombre()+":"+estado.get().isEFinal());
            System.out.println(pLongitud+":"+i.get());

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
//        imprimirTablaDeTransiciones(estados);
        return esValida;
    }

    private static void imprimirTablaDeTransiciones(List<Estado> estados) {
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

    private static Optional<Estado> obtenerEstadoInicial(List<Estado> estados) {
        return estados.stream().filter(Estado::isEInicial).findFirst();
    }

    private static Optional<Estado> obtenerSiguienteEstado(List<Estado> estados, Estado eActual, String t) {
        return estados
                .stream()
                .filter(e ->  e.getNombre().equals(eActual.getSalidas().get(t)))
                .findFirst();
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

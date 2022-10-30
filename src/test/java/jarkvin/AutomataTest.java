package jarkvin;

import edu.jarkvin.Main;
import edu.jarkvin.model.Estado;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
public class AutomataTest {
    private List<Estado> estados;

    @Before
    public void before(){
        estados = new ArrayList<>();

        Estado q0 = new Estado("q0", true, false);
        q0.agregarTransicion("a", "q1");

        Estado q1 = new Estado("q1", false, false);
        q1.agregarTransicion("a", "q2");

        Estado q2 = new Estado("q2", false, true);
        q2.agregarTransicion("a", "q1");

        estados.add(q0);
        estados.add(q1);
        estados.add(q2);
    }

    @Test
    public void esValida_True_Test(){
        String palabra = "aaaa";
        Assert.assertTrue(Main.esValida(estados, palabra));
    }

    @Test
    public void esValida_False_Test(){
        String palabra = "aaaaa";
        Assert.assertFalse(Main.esValida(estados, palabra));
    }
}

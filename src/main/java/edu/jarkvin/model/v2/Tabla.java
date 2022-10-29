package edu.jarkvin.model.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor
public class Tabla {
    List<Estado> estados;
    List<Transicion> transiciones;
}

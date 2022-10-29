package edu.jarkvin.model.v1;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Transicion {
    private String valor;
    private String destino;
    private String origen;
}

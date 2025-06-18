package org.sistema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private Integer idCliente;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String dni;
    private Prestamo prestamo;
}

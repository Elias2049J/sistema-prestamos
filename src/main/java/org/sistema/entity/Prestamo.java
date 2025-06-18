package org.sistema.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {
    private Integer idPrestamo;
    private double monto;
    private Integer nroCuotas;
    private double tasaInteres;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Cliente cliente;
    private String estado;










    public double getMontoMensual(){
        double tasaMensual = tasaInteres / 100;
        return monto * (
                (tasaMensual * Math.pow(1 + tasaMensual, nroCuotas)) /
                        (Math.pow(1 + tasaMensual, nroCuotas) - 1)
        );
    }

    public boolean esValido(){
        if (nroCuotas < 3 || nroCuotas > 36) return false;
        if (tasaInteres <= 0) return false;
        if (monto <= 0) return false;
        return true;
    }
}
package org.sistema.use_case;

import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;

import java.util.List;

public interface PrestamoUseCase {
    boolean create(double monto, Integer nroCuotas, double tasaInteres, Cliente cliente);
    boolean update();
    boolean delete(Integer id);
    Prestamo getById(Integer id);
    Object[][] calcDatosCronograma(Prestamo p);
}
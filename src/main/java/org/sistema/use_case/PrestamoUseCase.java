package org.sistema.use_case;

import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;

public interface PrestamoUseCase {
    boolean create(double monto, Integer nroCuotas, double tasaInteres, Cliente cliente);
    boolean update();

    boolean delete(Integer id);

    Prestamo getById(Integer id);
    Object[][] getCronogramaByDni(String dni);
    boolean updateCronograma(Object[][] datos);
    Object[][] calcDatosCronograma(Prestamo p);
    boolean registrarPago(Prestamo p, Integer nroCuota);
}
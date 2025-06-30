package org.sistema.use_case;

import org.sistema.entity.Prestamo;

import java.util.List;

public interface PersistenceUseCase {
    boolean exportarCronograma(Object[][] datos, String dni);
    boolean importarLista(List<Prestamo> lista);
    boolean importarCronograma(String dni);
    boolean exportarLista(List<Prestamo> lista);
    boolean exportarHistorialPago(Integer nroCuota, String dni);
}

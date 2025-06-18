package org.sistema.use_case;

import org.sistema.entity.Prestamo;

import java.util.List;

public interface PersistenceUseCase {
    boolean exportarCronograma(Object[][] datos);
    boolean guardarPrestamos(List<Prestamo> lista);
    boolean leerPrestamos(List<Prestamo> lista);
    boolean leerCronograma(Integer idPrestamo);
}

package org.sistema.interfaces;

import org.sistema.entity.Prestamo;

import java.util.List;

public interface PersistenceInterface {
    boolean exportarCronograma(Object[][] datos, String dni);
    boolean importarLista(List<Prestamo> lista);
    Object[][] importarCronograma(String dni);
    boolean exportarLista(List<Prestamo> lista);
    boolean exportarHistorialPago(Integer nroCuota, String dni, String fechaPago);
}

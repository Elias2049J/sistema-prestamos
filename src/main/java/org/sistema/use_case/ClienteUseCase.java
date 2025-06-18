package org.sistema.use_case;

import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;

public interface ClienteUseCase {
    Cliente create(String nombre, String apellido, Integer edad, String dni);
    boolean update(String nombre, String apellido, Integer edad, String dni, Prestamo prestamo);
    boolean delete(Integer id);
    Cliente getById(Integer id);
}

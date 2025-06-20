package org.sistema.repository;

import lombok.Getter;
import lombok.Setter;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.interfaces.RepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public class PrestamoRepository implements RepositoryInterface<Prestamo, Integer> {

    @Getter
    @Setter
    private static List<Prestamo> prestamos = new ArrayList<>();
    @Getter
    @Setter
    private static List<Cliente> clientes  = new ArrayList<>();
    @Getter
    @Setter
    private static Object[][] datosCronograma;

    public static boolean agregarPrestamo(Prestamo prestamo){
        prestamos.add(prestamo);
        return true;
    }

    public static boolean agregarCliente(Cliente cliente){
        clientes.add(cliente);
        return true;
    }

    @Override
    public boolean save(Prestamo prestamo) {
        return prestamos.add(prestamo);
    }

    @Override
    public Prestamo getById(Integer id) {
        for (Prestamo p: prestamos) {
            if (p.getIdPrestamo().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Prestamo> findAll() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public boolean delete(Integer id) {
        return prestamos.removeIf(p -> p.getIdPrestamo().equals(id));
    }
}

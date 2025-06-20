package org.sistema.repository;

import lombok.Getter;
import lombok.Setter;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
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
}

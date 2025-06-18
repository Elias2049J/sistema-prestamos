package org.sistema.model;

import lombok.Data;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.repository.DataRepository;

import java.util.ArrayList;

@Data
public class ClienteModel implements ClienteUseCase {
    @Override
    public Cliente create(String nombre, String apellido, Integer edad, String dni) {
        Integer id;
        if (DataRepository.getClientes().isEmpty()) {
            DataRepository.setClientes(new ArrayList<>());
            id = 1;
        } else {
            id = DataRepository.getClientes().getLast().getIdCliente()+1;
        }
        Cliente nC = new Cliente(id, nombre, apellido, edad, dni, null);
        DataRepository.agregarCliente(nC);
        return nC;
    }

    @Override
    public boolean update(String nombre, String apellido, Integer edad, String dni, Prestamo prestamo) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Cliente c = getById(id);
        DataRepository.getClientes().remove(c);
        return true;
    }

    @Override
    public Cliente getById(Integer id) {
        for (Cliente c: DataRepository.getClientes()) {
            if (c.getIdCliente().equals(id)) {
               return c;
            }
        }
        return null;
    }
}

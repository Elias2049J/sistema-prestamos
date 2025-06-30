package org.sistema.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.repository.DataRepository;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClienteModel extends ManageModel<Cliente, Integer> implements ClienteUseCase {
    @Override
    public Cliente create(String nombre, String apellido, Integer edad, String dni) {
        Cliente nC = new Cliente(nombre, apellido, edad, dni, null);
        DataRepository.agregarCliente(nC);
        return nC;
    }

    @Override
    public boolean update(String nombre, String apellido, Integer edad, String dni, Prestamo prestamo) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Cliente c = getByPrestamoId(id);
        DataRepository.getClientes().remove(c);
        return true;
    }

    @Override
    public Cliente getById(Integer integer) {
        return null;
    }

    @Override
    public Cliente getByPrestamoId(Integer id) {
        for (Prestamo p : DataRepository.getPrestamos()) {
            if (p.getIdPrestamo().equals(id)) {
                return p.getCliente();
            }
        }
        return null;
    }
}

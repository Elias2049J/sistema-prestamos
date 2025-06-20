package org.sistema.repository;

import org.sistema.entity.Cliente;
import org.sistema.interfaces.RepositoryInterface;

import java.util.List;

public class ClienteRepository implements RepositoryInterface<Cliente, Integer> {

    @Override
    public boolean save(Cliente entity) {
        return false;
    }

    @Override
    public Cliente getById(Integer integer) {
        return null;
    }

    @Override
    public List<Cliente> findAll() {
        return List.of();
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }
}

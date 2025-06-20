package org.sistema.use_case;

public interface CrudUseCase<T, ID> {
    T create();
    boolean update();
    boolean delete(ID id);
    T getById(ID id);
}

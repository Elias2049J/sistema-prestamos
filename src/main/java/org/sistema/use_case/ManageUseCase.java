package org.sistema.use_case;

public interface ManageUseCase<T, ID> {
    T getById(ID id);
}

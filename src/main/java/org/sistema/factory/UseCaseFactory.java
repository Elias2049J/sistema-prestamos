package org.sistema.factory;

import org.sistema.model.ClienteModel;
import org.sistema.model.PrestamoModel;
import org.sistema.persistencia.PersistencePrestamo;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.interfaces.PersistenceInterface;
import org.sistema.use_case.PrestamoUseCase;

public class UseCaseFactory {
    public static PrestamoUseCase createPrestamoUseCase() {
        return new PrestamoModel(createPersistenceUseCase(), createClienteUseCase());
    }

    public static ClienteUseCase createClienteUseCase() {
        return new ClienteModel();
    }

    public static PersistenceInterface createPersistenceUseCase() {
        return new PersistencePrestamo();
    }
}

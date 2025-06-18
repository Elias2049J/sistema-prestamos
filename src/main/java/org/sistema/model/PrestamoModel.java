package org.sistema.model;

import lombok.Data;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.use_case.PersistenceUseCase;
import org.sistema.use_case.PrestamoUseCase;
import org.sistema.persistencia.PersistencePrestamo;
import org.sistema.repository.DataRepository;

import java.time.LocalDate;
@Data
public class PrestamoModel implements PrestamoUseCase {
    private PersistenceUseCase persistencePrestamo = new PersistencePrestamo();
    private ClienteUseCase clienteModel = new ClienteModel();

    @Override
    public boolean create(double monto, Integer nroCuotas, double tasaInteres, Cliente cliente) {
        Integer id;
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaVencimiento = fechaInicio.plusMonths(nroCuotas);
        String estado = "activo";
        if (DataRepository.getPrestamos().isEmpty()) {
            id = 1;
        } else {
            id = DataRepository.getPrestamos().getLast().getIdPrestamo()+1;
        }
        Prestamo nP = new Prestamo(id, monto, nroCuotas, tasaInteres, fechaInicio, fechaVencimiento, cliente, estado);
        if (!nP.esValido()) {
            return false;
        }
        DataRepository.agregarPrestamo(nP);
        DataRepository.setDatosCronograma(calcDatosCronograma(nP));
        persistencePrestamo.guardarPrestamos(DataRepository.getPrestamos());
        return persistencePrestamo.exportarCronograma(DataRepository.getDatosCronograma());
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Prestamo p = getById(id);
        DataRepository.getClientes().remove(p);
        return true;
    }

    @Override
    public Prestamo getById(Integer id) {
        for (Prestamo p: DataRepository.getPrestamos()) {
            if(p.getIdPrestamo().equals(id)){
                return p;
            }
        }
        return null;
    }

    @Override
    public Object[][] calcDatosCronograma(Prestamo p){
        String[] columnas = {"Nro. Cuota", "Cuota", "Fecha Vencimiento", "Estado"};
        Object[][] cronogramaDatos;

        int n = p.getNroCuotas();
        cronogramaDatos = new Object[n][columnas.length];
        int contadorCuota = 1;
        for (int i = 0; i < p.getNroCuotas(); i++) {
            cronogramaDatos[i][0] = contadorCuota;
            cronogramaDatos[i][1] = Math.round(p.getMontoMensual() * 1000000.0) / 1000000.0;
            cronogramaDatos[i][2] = p.getFechaInicio().plusMonths(contadorCuota+1);
            cronogramaDatos[i][3] = "Pendiente";
            contadorCuota++;
        }
        return cronogramaDatos;
    }
}

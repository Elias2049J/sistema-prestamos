package org.sistema.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.use_case.PersistenceUseCase;
import org.sistema.use_case.PrestamoUseCase;
import org.sistema.persistencia.PersistencePrestamo;
import org.sistema.repository.DataRepository;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = false)
@Data
public class PrestamoModel extends ManageModel<Prestamo, Integer> implements PrestamoUseCase {
    private PersistenceUseCase persistencePrestamo = new PersistencePrestamo();
    private ClienteUseCase clienteModel = new ClienteModel();

    public PrestamoModel(){
        this.persistencePrestamo.importarLista(DataRepository.getPrestamos());
    }

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
        return persistencePrestamo.exportarLista(DataRepository.getPrestamos()) &&
                persistencePrestamo.exportarCronograma(DataRepository.getDatosCronograma(), nP.getCliente().getDni()) &&
                persistencePrestamo.importarLista(DataRepository.getPrestamos());
    }
    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Prestamo p = getById(id);
        DataRepository.getPrestamos().remove(p);
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
    public Object[][] getCronogramaByDni(String dni){
        if (DataRepository.getDatosCronograma() == null) {
            DataRepository.setDatosCronograma(new Object[0][4]);
        }
        persistencePrestamo.importarCronograma(dni);
        return DataRepository.getDatosCronograma();
    }

    @Override
    public boolean updateCronograma(Object[][] datos) {
        return false;
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

    @Override
    public boolean registrarPago(Prestamo p, Integer nroCuota) {
        Object[][] cronograma = DataRepository.getDatosCronograma();
        for (Object[] fila : cronograma) {
            if (Integer.parseInt(fila[0].toString()) == nroCuota) {
                fila[3] = "Pagado";
            }
        }
        DataRepository.setDatosCronograma(cronograma);
        boolean cronogramaActualizado = persistencePrestamo.exportarCronograma(cronograma, p.getCliente().getDni());
        boolean historialActualizado = persistencePrestamo.exportarHistorialPago(nroCuota, p.getCliente().getDni());
        return cronogramaActualizado && historialActualizado;
    }
}

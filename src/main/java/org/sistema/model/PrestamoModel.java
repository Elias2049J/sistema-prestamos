package org.sistema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.interfaces.PersistenceInterface;
import org.sistema.use_case.PrestamoUseCase;
import org.sistema.repository.PrestamoRepository;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoModel extends ManageModel<Prestamo, Integer> implements PrestamoUseCase {
    private PersistenceInterface persistencePrestamo;
    private ClienteUseCase clienteModel;

    @Override
    public boolean create(double monto, Integer nroCuotas, double tasaInteres, Cliente cliente) {
        Integer id;
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaVencimiento = fechaInicio.plusMonths(nroCuotas);
        String estado = "activo";
        if (PrestamoRepository.getPrestamos().isEmpty()) {
            id = 1;
        } else {
            id = PrestamoRepository.getPrestamos().getLast().getIdPrestamo()+1;
        }
        Prestamo nP = new Prestamo(id, monto, nroCuotas, tasaInteres, fechaInicio, fechaVencimiento, cliente, estado);
        if (!nP.esValido()) {
            return false;
        }
        PrestamoRepository.agregarPrestamo(nP);
        PrestamoRepository.setDatosCronograma(calcDatosCronograma(nP));
        return persistencePrestamo.exportarLista(PrestamoRepository.getPrestamos()) &&
                persistencePrestamo.exportarCronograma(PrestamoRepository.getDatosCronograma(), nP.getCliente().getDni()) &&
                persistencePrestamo.importarLista(PrestamoRepository.getPrestamos());
    }
    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Prestamo p = getById(id);
        PrestamoRepository.getPrestamos().remove(p);
        return true;
    }

    @Override
    public Prestamo getById(Integer id) {
        for (Prestamo p: PrestamoRepository.getPrestamos()) {
            if(p.getIdPrestamo().equals(id)){
                return p;
            }
        }
        return null;
    }

    @Override
    public Object[][] getCronogramaByDni(String dni){
        if (PrestamoRepository.getDatosCronograma() == null) {
            PrestamoRepository.setDatosCronograma(persistencePrestamo.importarCronograma(dni));
        }
        return PrestamoRepository.getDatosCronograma();
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
        Object[][] cronograma = PrestamoRepository.getDatosCronograma();
        for (Object[] fila : cronograma) {
            if (Integer.parseInt(fila[0].toString()) == nroCuota) {
                fila[3] = "Pagado";
            }
        }
        PrestamoRepository.setDatosCronograma(cronograma);
        return persistencePrestamo.exportarCronograma(cronograma, p.getCliente().getDni()) &&
                persistencePrestamo.exportarHistorialPago(nroCuota, p.getCliente().getDni(), LocalDate.now().toString());
    }

    public void setPersistencePrestamo(PersistenceInterface persistencePrestamo) {
        this.persistencePrestamo = persistencePrestamo;
        this.persistencePrestamo.importarLista(PrestamoRepository.getPrestamos());
    }
}

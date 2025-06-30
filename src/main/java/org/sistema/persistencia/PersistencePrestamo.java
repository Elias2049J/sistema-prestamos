package org.sistema.persistencia;

import org.sistema.entity.Cliente;
import org.sistema.entity.Prestamo;
import org.sistema.repository.DataRepository;
import org.sistema.use_case.PersistenceUseCase;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class PersistencePrestamo implements PersistenceUseCase {
    @Override
    public boolean exportarCronograma(Object[][] datosCronograma, String dni) {
        try {
            File archivo = new File("src/main/java/org/sistema/data/cronograma_pagos_"+ dni+".txt");
            PrintWriter writer = new PrintWriter(new FileWriter(archivo, false));
            writer.printf("%-12s %-10s %10s%n %n","=================","CRONOGRAMA DE PAGOS", "==================");
            writer.printf("%-12s %-14s %-20s %-12s%n","Nro. Cuota", "Cuota (S/)","Fecha Vencimiento","Estado");
            for (Object[] fila: datosCronograma) {
                writer.printf("%-12s %-14s %-20s %-12s%n", fila[0], fila[1], fila[2], fila[3]);
            }
            writer.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean importarLista(List<Prestamo> lista) {
        lista.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/org/sistema/data/prestamos.txt"));
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.trim().split("\\s{2,}");
                Cliente c = new Cliente(
                        campos[1],
                        campos[2],
                        Integer.parseInt(campos[3]),
                        campos[4],
                        null
                );
                Prestamo p = new Prestamo(
                        Integer.parseInt(campos[0]),
                        Double.parseDouble(campos[5]),
                        Integer.parseInt(campos[6]),
                        Double.parseDouble(campos[7]),
                        LocalDate.parse(campos[8]),
                        LocalDate.parse(campos[9]),
                        c,
                        campos[10]
                        );
                lista.add(p);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean importarCronograma(String dni) {
        try{
            File archivo = new File("src/main/java/org/sistema/data/cronograma_pagos_"+dni+".txt");
            BufferedReader contarFilas = new BufferedReader(new FileReader(archivo));
            contarFilas.readLine();
            contarFilas.readLine();
            contarFilas.readLine();
            int numL = 0;
            while (contarFilas.readLine() != null) {
                numL++;
            }
            contarFilas.close();
            Object[][] nuevoArray = new Object[numL][4];
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            br.readLine();
            br.readLine();
            br.readLine();
            String linea;
            int contadorFila = 0;
            while ((linea = br.readLine()) != null && contadorFila < numL) {
                String[] campos = linea.trim().split("\\s{2,}");
                for (int i = 0; i < campos.length && i < 4; i++) {
                    nuevoArray[contadorFila][i] = campos[i];
                }
                contadorFila++;
            }
            br.close();
            DataRepository.setDatosCronograma(nuevoArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean exportarLista(List<Prestamo> lista) {
        try {
            File archivo = new File("src/main/java/org/sistema/data/prestamos.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(archivo, false));
            writer.printf("%-12s %-15s %-15s %-6s %-10s %-18s %-14s %-14s %-20s %-20s %-10s%n"
                    ,"IdPrestamo","Nombre","Apellido","Edad","Dni","Monto","Cant. Cuotas","Tasa Mensual","Fecha Inicio","Fecha Vencimiento", "Estado");
            for (Prestamo p: lista) {
                writer.printf("%-12s %-15s %-15s %-6s %-10s %-18s %-14s %-14s %-20s %-20s %-10s%n",
                        p.getIdPrestamo(),
                        p.getCliente().getNombre(),
                        p.getCliente().getApellido(),
                        p.getCliente().getEdad(),
                        p.getCliente().getDni(),
                        p.getMonto(),
                        p.getNroCuotas(),
                        p.getTasaInteres(),
                        p.getFechaInicio(),
                        p.getFechaVencimiento(),
                        p.getEstado()
                        );
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean exportarHistorialPago(Integer nroCuota, String dni) {
        try {
            File archivo = new File("src/main/java/org/sistema/data/pagos_"+dni+".txt");
            boolean archivoExiste = archivo.exists();
            PrintWriter writer = new PrintWriter(new FileWriter(archivo, true));
            if (!archivoExiste) {
                writer.printf("%-12s %-10s %10s%n %n","=================","HISTORIAL DE PAGOS", "==================");
                writer.printf("%-15s %-15s %-20s %-15s%n", "DNI", "Nro. Cuota", "Fecha de Pago", "Monto Pagado");
            }
            String cuota = "";
            Object[][] cronograma = DataRepository.getDatosCronograma();
            for (Object[] fila : cronograma) {
                if (Integer.parseInt(fila[0].toString()) == nroCuota) {
                    cuota = fila[1].toString();
                    break;
                }
            }
            writer.printf("%-15s %-15s %-20s %-15s%n", dni, nroCuota, LocalDate.now(), cuota);
            writer.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}


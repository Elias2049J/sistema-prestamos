package org.sistema.persistencia;

import org.sistema.entity.Prestamo;
import org.sistema.use_case.PersistenceUseCase;
import org.sistema.repository.DataRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class PersistencePrestamo implements PersistenceUseCase {
    @Override
    public boolean exportarCronograma(Object[][] datosCronograma) {
        try {
            File archivo = new File("src/main/java/org/sistema/data/cronograma_pagos_"+ DataRepository.getPrestamos().getLast().getIdPrestamo()+".txt");
            PrintWriter writer = new PrintWriter(new FileWriter(archivo, false));
            // cabecera
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
    public boolean guardarPrestamos(List<Prestamo> lista) {
        try {
            File archivo = new File("src/main/java/org/sistema/data/listaPrestamos.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(archivo, false));
            // cabecera
            writer.println("IdPrestamo,Nombre,Apellido,Edad,Dni,Monto,Cant. Cuotas,Tasa Mensual,Fecha Inicio,Fecha Vencimiento");
            for (Prestamo p: lista) {
                writer.println(
                        p.getIdPrestamo()+","+
                        p.getCliente().getNombre()+","+
                        p.getCliente().getApellido()+","+
                        p.getCliente().getEdad()+","+
                        p.getCliente().getDni()+","+
                        p.getMonto()+","+
                        p.getNroCuotas()+","+
                        p.getTasaInteres()+","+
                        p.getFechaInicio().toString()+","+
                        p.getFechaVencimiento().toString()
                );
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean leerPrestamos(List<Prestamo> lista) {
        return false;
    }

    @Override
    public boolean leerCronograma(Integer idPrestamo) {
        return false;
    }

    public boolean raa(List<Prestamo> lista) {
        try {
            File archivo = new


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


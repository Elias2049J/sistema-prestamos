package org.sistema.vista;

import lombok.Getter;
import org.sistema.entity.Prestamo;
import org.sistema.model.ClienteModel;
import org.sistema.model.PrestamoModel;
import org.sistema.repository.DataRepository;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.use_case.PrestamoUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class VentanaPagos extends JFrame {
    private LienzoCentral lienzoCentral;
    private LienzoFooter lienzoFooter = new LienzoFooter();
    private Object[][] cronograma;
    private String dni;

    public VentanaPagos(Object[][] cronograma, String dni) {
        super();
        this.dni = dni;
        this.cronograma = cronograma;
        this.lienzoCentral = new LienzoCentral(this.cronograma, this.dni);
        this.setTitle("Gestión de pagos");
        this.setSize(800, 600);
        this.setLocationRelativeTo(rootPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(lienzoCentral, BorderLayout.CENTER);
        this.add(lienzoFooter, BorderLayout.SOUTH);
    }
    @Getter
    class LienzoCentral extends JPanel {
        private ClienteUseCase clienteModel = new ClienteModel();
        private PrestamoUseCase prestamoModel = new PrestamoModel();

        private JPanel panelSuperior = new JPanel(new GridBagLayout());

        private JLabel lblSelCliente = new JLabel("Seleccione el Nro. de Cuota a Pagar: ");
        private JComboBox<Integer> cboCuota = new JComboBox<>();
        private JButton btnConfirmar = new JButton("Confirmar Pago");

        private JPanel panelResultados = new JPanel(new BorderLayout());

        private String[] columnasPrestamo = {"Nro. Cuota", "Cuota (S/)","Fecha Vencimiento","Estado"};
        private Object[][] datosPrestamo;
        @Getter
        private DefaultTableModel modeloDatosPrestamo;
        private JTable tabla;
        private JScrollPane scpResultados;
        private String dni;

        public LienzoCentral(Object[][] cronograma, String dni) {
            super();
            this.setLayout(new GridBagLayout());
            this.dni = dni;
            datosPrestamo = cronograma;
            modeloDatosPrestamo = new DefaultTableModel(datosPrestamo, columnasPrestamo);
            tabla = new JTable(modeloDatosPrestamo);
            scpResultados = new JScrollPane(tabla);
            panelResultados.removeAll();
            panelResultados.add(scpResultados);

            for (Object[] fila: datosPrestamo) {
                Integer nroCuota = Integer.parseInt(fila[0].toString());
                cboCuota.addItem(nroCuota);
            }

            GridBagConstraints gbcPadre = new GridBagConstraints();
            gbcPadre.insets = new Insets(10, 20, 10, 10);
            panelResultados.add(scpResultados);

            GridBagConstraints gbcSuperior = new GridBagConstraints();
            gbcSuperior.insets = new Insets(2, 2, 2, 2);
            gbcSuperior.fill = GridBagConstraints.BOTH;
            gbcSuperior.weightx = 1.0;

            int gridy = 0;
            gbcSuperior.gridx = 0;
            gbcSuperior.gridy = gridy++;
            gbcSuperior.gridwidth = 5;
            gbcSuperior.gridheight = 1;
            gbcSuperior.weighty = 0;
            gbcSuperior.weightx = 0;
            gbcSuperior.fill = GridBagConstraints.HORIZONTAL;
            panelSuperior.add(Box.createVerticalStrut(10), gbcSuperior);

            gbcSuperior.gridx = 0;
            gbcSuperior.gridy = gridy++;
            gbcSuperior.gridwidth = 1;
            gbcSuperior.gridheight = 1;
            gbcSuperior.weighty = 1;
            gbcSuperior.weightx = 0;
            gbcSuperior.fill = GridBagConstraints.NONE;
            gbcSuperior.anchor = GridBagConstraints.EAST;
            lblSelCliente.setFont(new Font("Arial", Font.BOLD, 16));
            panelSuperior.add(lblSelCliente, gbcSuperior);

            gbcSuperior.gridx = 3;
            gbcSuperior.gridy = gridy - 1;
            gbcSuperior.gridwidth = 1;
            gbcSuperior.gridheight = 1;
            gbcSuperior.weighty = 1;
            gbcSuperior.weightx = 0;
            gbcSuperior.fill = GridBagConstraints.HORIZONTAL;
            gbcSuperior.anchor = GridBagConstraints.CENTER;
            panelSuperior.add(cboCuota, gbcSuperior);

            gbcSuperior.gridx = 4;
            panelSuperior.add(btnConfirmar, gbcSuperior);

            gbcPadre.gridx = 0;
            gbcPadre.gridy = gridy++;
            gbcPadre.weightx = 1;
            gbcPadre.fill = GridBagConstraints.HORIZONTAL;
            gbcPadre.anchor = GridBagConstraints.NORTH;
            this.add(panelSuperior, gbcPadre);

            gbcPadre.gridx = 0;
            gbcPadre.gridy = gridy++;
            gbcPadre.weightx = 1;
            gbcPadre.weighty = 2;
            gbcPadre.fill = GridBagConstraints.BOTH;
            this.add(panelResultados, gbcPadre);

            btnConfirmar.addActionListener(e -> {
                Integer nroCuota = Integer.parseInt(cboCuota.getSelectedItem().toString());
                String dniCliente = this.dni;

                Prestamo prestamo = null;
                for (Prestamo p : DataRepository.getPrestamos()) {
                    if (p.getCliente().getDni().equals(dniCliente)) {
                        prestamo = p;
                        break;
                    }
                }
                if (prestamoModel.registrarPago(prestamo, nroCuota)) {
                    JOptionPane.showMessageDialog(this, "Pago registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla(prestamoModel.getCronogramaByDni(dniCliente));
                } else {
                    JOptionPane.showMessageDialog(this, "Hubo un error al realziar el pago", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        public boolean actualizarTabla(Object[][] nuevoCronograma) {
            datosPrestamo = nuevoCronograma;
            modeloDatosPrestamo.setRowCount(0);
            for (Object[] fila : nuevoCronograma) {
                modeloDatosPrestamo.addRow(fila);
            }
            tabla.repaint();
            return true;
        }
    }

    class LienzoFooter extends JPanel{
        private JButton btnSalir = new JButton("Salir");
        public LienzoFooter(){
            super();
            this.setLayout(new BorderLayout());
            this.setBackground(new Color(33, 122, 210));
            this.setForeground(Color.WHITE);
            this.add(btnSalir, BorderLayout.EAST);
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            btnSalir.addActionListener(e -> {
                dispose();
            });
        }
    }
}


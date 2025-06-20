package org.sistema.vista;

import lombok.Getter;
import org.sistema.entity.Cliente;
import org.sistema.model.ClienteModel;
import org.sistema.model.PrestamoModel;
import org.sistema.repository.DataRepository;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.use_case.PrestamoUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPagos extends JFrame {
    private LienzoCentral lienzoCentral = new LienzoCentral();
    private LienzoFooter lienzoFooter = new LienzoFooter();

    public VentanaPagos() {
        super();
        this.setTitle("Gestión de Pacientes");
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

        private JPanel panelBusqueda = new JPanel(new GridBagLayout());

        private JLabel lblBusqueda = new JLabel("Seleccione un Cliente");
        private JButton btnSelCliente = new JButton("Confirmar");

        private JPanel panelResultados = new JPanel(new BorderLayout());

        private String[] columnasCliente = {"Nombre", "Apellido", "Edad", "DNI"};
        private Object[][] datosCliente;

        private String[] columnasPrestamo = {"Nro. Cuota", "Cuota (S/)","Fecha Vencimiento","Estado"};
        private Object[][] datosPrestamo;
        @Getter
        private DefaultTableModel modelo;
        private JTable tabla;
        private JScrollPane scpResultados;

        public LienzoCentral() {
            super();
            this.setLayout(new GridBagLayout());

            datosCliente = new Object[DataRepository.getPrestamos().size()][4];
            for (int i = 0; i < DataRepository.getPrestamos().size(); i++) {
                Cliente c = DataRepository.getPrestamos().get(i).getCliente();
                datosCliente[i][0] = c.getNombre();
                datosCliente[i][1] = c.getApellido();
                datosCliente[i][2] = c.getEdad();
                datosCliente[i][3] = c.getDni();
            }
            modelo = new DefaultTableModel(datosCliente, columnasCliente);
            tabla = new JTable(modelo);
            scpResultados = new JScrollPane(tabla);
            panelResultados.removeAll();
            panelResultados.add(scpResultados);

            GridBagConstraints gbcPadre = new GridBagConstraints();
            gbcPadre.insets = new Insets(10, 20, 10, 10);
            panelResultados.add(scpResultados);

            GridBagConstraints gbcBusqueda = new GridBagConstraints();
            gbcBusqueda.insets = new Insets(2, 2, 2, 2);
            gbcBusqueda.fill = GridBagConstraints.BOTH;
            gbcBusqueda.weightx = 1.0;

            int gridy = 0;
            gbcBusqueda.gridx = 0;
            gbcBusqueda.gridy = gridy++;
            gbcBusqueda.gridwidth = 5;
            gbcBusqueda.gridheight = 1;
            gbcBusqueda.weighty = 0;
            gbcBusqueda.weightx = 0;
            gbcBusqueda.fill = GridBagConstraints.HORIZONTAL;
            panelBusqueda.add(Box.createVerticalStrut(10), gbcBusqueda);

            gbcBusqueda.gridx = 0;
            gbcBusqueda.gridy = gridy++;
            gbcBusqueda.gridwidth = 1;
            gbcBusqueda.gridheight = 1;
            gbcBusqueda.weighty = 1;
            gbcBusqueda.weightx = 0;
            gbcBusqueda.fill = GridBagConstraints.NONE;
            gbcBusqueda.anchor = GridBagConstraints.EAST;
            lblBusqueda.setFont(new Font("Arial", Font.BOLD, 16));
            panelBusqueda.add(lblBusqueda, gbcBusqueda);

            gbcBusqueda.gridx = 3;
            gbcBusqueda.gridy = gridy - 1;
            gbcBusqueda.gridwidth = 1;
            gbcBusqueda.gridheight = 1;
            gbcBusqueda.weighty = 1;
            gbcBusqueda.weightx = 0;
            gbcBusqueda.fill = GridBagConstraints.HORIZONTAL;
            gbcBusqueda.anchor = GridBagConstraints.CENTER;
            panelBusqueda.add(btnSelCliente, gbcBusqueda);

            gbcPadre.gridx = 0;
            gbcPadre.gridy = gridy++;
            gbcPadre.weightx = 1;
            gbcPadre.fill = GridBagConstraints.HORIZONTAL;
            gbcPadre.anchor = GridBagConstraints.NORTH;
            this.add(panelBusqueda, gbcPadre);

            gbcPadre.gridx = 0;
            gbcPadre.gridy = gridy++;
            gbcPadre.weightx = 1;
            gbcPadre.weighty = 2;
            gbcPadre.fill = GridBagConstraints.BOTH;
            this.add(panelResultados, gbcPadre);

            btnSelCliente.addActionListener(e -> {
                String dni = modelo.getValueAt(tabla.getSelectedRow(), 3).toString().trim();
                datosPrestamo = prestamoModel.getCronogramaByDni(dni);
                DefaultTableModel modeloPrestamo = new DefaultTableModel(datosPrestamo, columnasPrestamo);
                tabla.setModel(modeloPrestamo);
            });
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


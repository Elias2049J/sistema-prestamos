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
import java.util.Objects;

public class VentanaClientes extends JFrame {
    private VentanaPagos ventanaPagos;
    private LienzoCentral lienzoCentral = new LienzoCentral();
    private LienzoFooter lienzoFooter = new LienzoFooter();

    public VentanaClientes() {
        super();
        this.setTitle("Clientes");
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

        private JLabel lblSelCliente = new JLabel("Seleccione un Cliente: ");
        private JComboBox<String> cboClienteDni = new JComboBox<>();
        private JButton btnConfirmar = new JButton("Confirmar");

        private JPanel panelResultados = new JPanel(new BorderLayout());

        private String[] columnasCliente = {"Nombre", "Apellido", "Edad", "DNI"};
        private Object[][] datosCliente;

        @Getter
        private DefaultTableModel modelo;
        private JTable tabla;
        private JScrollPane scpResultados;

        private String dniSeleccionado = null;

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

            for (int i = 0; i < DataRepository.getPrestamos().size(); i++) {
                String dni = DataRepository.getPrestamos().get(i).getCliente().getDni();
                cboClienteDni.addItem(dni);
            }

            scpResultados = new JScrollPane(tabla);
            panelResultados.removeAll();
            panelResultados.add(scpResultados);

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
            panelSuperior.add(cboClienteDni, gbcSuperior);

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

            btnConfirmar.addActionListener(e-> {
                String dni = Objects.requireNonNull(cboClienteDni.getSelectedItem()).toString();
                ventanaPagos = new VentanaPagos(prestamoModel.getCronogramaByDni(dni), dni);
                ventanaPagos.setVisible(true);
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


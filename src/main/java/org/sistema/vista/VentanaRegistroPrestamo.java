package org.sistema.vista;

import org.sistema.model.ClienteModel;
import org.sistema.model.PrestamoModel;
import org.sistema.use_case.ClienteUseCase;
import org.sistema.use_case.PrestamoUseCase;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPrestamo extends JFrame{
    private LienzoCentral lienzoCentral = new LienzoCentral();

    public VentanaRegistroPrestamo() {
        super();
        this.setTitle("Registro de Préstamos");
        this.setSize(600, 450);
        this.setLocationRelativeTo(rootPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(lienzoCentral, BorderLayout.CENTER);
    }

    class LienzoCentral extends JPanel {
        private ClienteUseCase clienteModel = new ClienteModel();
        private PrestamoUseCase prestamoModel = new PrestamoModel();

        private JLabel lblRegistro = new JLabel("Registrar nuevo prestamo", SwingConstants.CENTER);

        private JLabel lblNombre = new JLabel("Nombres:");
        private JTextField jtfNombre = new JTextField(20);
        private JLabel lblApellido = new JLabel("Apellidos:");
        private JTextField jtfApellido = new JTextField(20);
        private JLabel lblEdad = new JLabel("Edad:");
        private JTextField jtfEdad = new JTextField(20);
        private JLabel lblDni = new JLabel("DNI:");
        private JTextField jtfDni = new JTextField(20);

        private JLabel lblMonto = new JLabel("Total Préstamo:");
        private JTextField jtfMonto = new JTextField(20);
        private JLabel lblNroCuotas = new JLabel("Nro. Cuotas:");
        private JTextField jtfNroCuotas = new JTextField(20);
        private JLabel lblInteres = new JLabel("Interés Mensual (%) :");
        private JTextField jtfInteres = new JTextField(20);

        private JButton btnRegistrar = new JButton("Registrar");

        public LienzoCentral() {
            super();
            this.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 5;
            gbc.anchor = GridBagConstraints.CENTER;
            lblRegistro.setFont(new Font("Arial", Font.BOLD, 18));
            this.add(lblRegistro, gbc);

            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridwidth = 1;

            gbc.gridx = 0;
            gbc.gridy = 1;
            this.add(lblNombre, gbc);
            gbc.gridy++;
            this.add(lblApellido, gbc);
            gbc.gridy++;
            this.add(lblEdad, gbc);
            gbc.gridy++;
            this.add(lblDni, gbc);
            gbc.gridy++;
            this.add(lblMonto, gbc);
            gbc.gridy++;
            this.add(lblNroCuotas, gbc);
            gbc.gridy++;
            this.add(lblInteres, gbc);
            gbc.gridy++;

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridheight = 7;
            this.add(Box.createHorizontalStrut(30), gbc);
            gbc.gridheight = 1;

            gbc.gridx = 2;
            gbc.gridy = 1;
            this.add(jtfNombre, gbc);
            gbc.gridy++;
            this.add(jtfApellido, gbc);
            gbc.gridy++;
            this.add(jtfEdad, gbc);
            gbc.gridy++;
            this.add(jtfDni, gbc);
            gbc.gridy++;
            this.add(jtfMonto, gbc);
            gbc.gridy++;
            this.add(jtfNroCuotas, gbc);
            gbc.gridy++;
            this.add(jtfInteres, gbc);
            gbc.gridy++;

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(btnRegistrar, gbc);

            btnRegistrar.addActionListener(e -> {
                String nombre = jtfNombre.getText().trim();
                String apellido = jtfApellido.getText().trim();
                Integer edad = Integer.parseInt(jtfEdad.getText().trim());
                String dni = jtfDni.getText().trim();

                double monto = Double.parseDouble(jtfMonto.getText().trim());
                Integer numCuotas = Integer.parseInt(jtfNroCuotas.getText().trim());
                double interes = Double.parseDouble(jtfInteres.getText().trim());
                if(prestamoModel.create(monto, numCuotas, interes, clienteModel.create(nombre, apellido, edad, dni))) {
                    JOptionPane.showMessageDialog(this, "Préstamo registrado con éxito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Hubo un error", "Error", JOptionPane.ERROR_MESSAGE);
                }
             });
        }
    }
}
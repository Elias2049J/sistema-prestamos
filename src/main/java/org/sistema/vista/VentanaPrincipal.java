package org.sistema.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private VentanaRegistroPrestamo ventanaRegistroPrestamo;

    private LienzoCentral lienzoCentral = new LienzoCentral();
    private LienzoFooter lienzoFooter = new LienzoFooter();

    public VentanaPrincipal() throws HeadlessException {
        super();
        this.setTitle("Sistema Clínico");
        this.setSize(500, 400);
        this.setLocationRelativeTo(rootPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(lienzoCentral, BorderLayout.CENTER);
        this.add(lienzoFooter, BorderLayout.SOUTH);
    }

    class LienzoCentral extends JPanel {
        private JLabel lblTitulo = new JLabel("Préstamos");

        private JButton btnVentReg = new JButton("Registrar Préstamo");
        private JButton btnVentGest = new JButton("Administrar Préstamo");
        public LienzoCentral() {
            super();
            this.setLayout(new GridBagLayout());
            setBackground(new Color(240, 245, 255));
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 1;

            JPanel panelCentro = new JPanel(new GridBagLayout());
            panelCentro.setBackground(Color.WHITE);
            GridBagConstraints gbcP = new GridBagConstraints();
            gbcP.insets = new Insets(10, 10, 10, 10);
            gbcP.gridx = 0;
            gbcP.fill = GridBagConstraints.HORIZONTAL;

            lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
            gbcP.gridy = 0;
            panelCentro.add(lblTitulo, gbcP);
            gbcP.gridy = 1;
            panelCentro.add(btnVentReg, gbcP);
            gbcP.gridy = 2;
            panelCentro.add(btnVentGest, gbcP);

            gbc.gridx = 1;
            gbc.weightx = 1;
            this.add(panelCentro, gbc);

            btnVentReg.addActionListener(e -> {
                ventanaRegistroPrestamo = new VentanaRegistroPrestamo();
                ventanaRegistroPrestamo.setVisible(true);
            });
        }
    }

    class LienzoFooter extends JPanel{
        private JButton btnSalir = new JButton("Salir");
        public LienzoFooter (){
            super();
            this.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.setBackground(new Color(124, 186, 219, 126));
            this.setForeground(Color.WHITE);
            this.add(btnSalir);
            this.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

            btnSalir.addActionListener(e -> {
                dispose();
            });
        }
    }
}
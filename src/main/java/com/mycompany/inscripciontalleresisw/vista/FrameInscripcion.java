/*
 * Pantalla de inscripcion con paneles que van cambiando
 */
package com.mycompany.inscripciontalleresisw.vista;

import control.ControlInscripcion;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Alumno;
import modelo.Inscripcion;
import modelo.Taller;

/**
 *
 * @author Fercho
 */
public class FrameInscripcion extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameInscripcion.class.getName());

    private static final Color MORADO = new Color(92, 60, 150);
    private static final Color MORADO_OSCURO = new Color(68, 42, 116);
    private static final Color AZUL_CLARO = new Color(214, 234, 248);
    private static final Color GRIS_DESHABILITADO = new Color(230, 230, 230);

    private static final String CARD_VACIO = "vacio";
    private static final String CARD_DATOS = "datos";
    private static final String CARD_TICKET = "ticket";

    private final ControlInscripcion control;
    
    private boolean ticketMostrado = false;

    private JLabel lblCurso;
    private JTextField txtId;
    private JButton btnContinuar;
    private JButton btnConfirmar;

    private CardLayout cardLayout;
    private JPanel panelCambiante;

    // Labels de  "datos del alumno"
    private JLabel lblDatosNombre;
    private JLabel lblDatosId;
    private JLabel lblDatosSemestre;
    private JLabel lblDatosPrograma;

    // Labels de "ticket"
    private JLabel lblTicketFolio;
    private JLabel lblTicketAlumno;
    private JLabel lblTicketTaller;
    private JLabel lblTicketInstructor;
    private JLabel lblTicketFecha;
    /**
     * Creates new form FrameInscricion
     */
    public FrameInscripcion(ControlInscripcion control) {
        this.control = control;
        inicializarComponentes();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Inscripción");
        setSize(420, 480);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void inicializarComponentes() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        getContentPane().add(crearHeader(), BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        contenido.setBackground(Color.WHITE);

        contenido.add(crearCajaCurso());
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(crearZonaId());
        contenido.add(Box.createVerticalStrut(14));

        cardLayout = new CardLayout();
        panelCambiante = new JPanel(cardLayout);
        panelCambiante.setOpaque(false);
        panelCambiante.add(new JPanel(), CARD_VACIO);
        panelCambiante.add(crearZonaDatosAlumno(), CARD_DATOS);
        panelCambiante.add(crearZonaTicket(), CARD_TICKET);
        contenido.add(panelCambiante);

        contenido.add(Box.createVerticalGlue());
        contenido.add(crearBotonConfirmar());

        getContentPane().add(contenido, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MORADO);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel titulo = new JLabel("Inscripción");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);
        return header;
    }

    private JPanel crearCajaCurso() {
        JPanel caja = new JPanel(new BorderLayout());
        caja.setBackground(AZUL_CLARO);
        caja.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        caja.setAlignmentX(Component.LEFT_ALIGNMENT);
        caja.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        lblCurso = new JLabel("CURSO: --");
        lblCurso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        caja.add(lblCurso, BorderLayout.WEST);
        return caja;
    }

    private JPanel crearZonaId() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel("Ingrese su ID:");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtId = new JTextField();
        txtId.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnContinuar = new JButton("CONTINUAR");
        estilizarBotonPrimario(btnContinuar);
        btnContinuar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnContinuar.addActionListener(e -> control.manejarContinuar(txtId.getText().trim()));

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtId);
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnContinuar);
        return panel;
    }

    private JPanel crearZonaDatosAlumno() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Datos del alumno:");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblDatosNombre = crearLabelDato("Nombre: --");
        lblDatosId = crearLabelDato("ID: --");
        lblDatosSemestre = crearLabelDato("Semestre: --");
        lblDatosPrograma = crearLabelDato("Programa: --");

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblDatosNombre);
        panel.add(lblDatosId);
        panel.add(lblDatosSemestre);
        panel.add(lblDatosPrograma);
        return panel;
    }

    private JPanel crearZonaTicket() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        lblTicketFolio = new JLabel("TICKET DE INSCRIPCION (--)");
        lblTicketFolio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTicketFolio.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblTicketAlumno = crearLabelDato("Alumno: --");
        lblTicketTaller = crearLabelDato("Taller: --");
        lblTicketInstructor = crearLabelDato("Instructor: --");
        lblTicketFecha = crearLabelDato("Fecha: --");

        panel.add(lblTicketFolio);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblTicketAlumno);
        panel.add(lblTicketTaller);
        panel.add(lblTicketInstructor);
        panel.add(lblTicketFecha);
        return panel;
    }

    private JButton crearBotonConfirmar() {
        btnConfirmar = new JButton("CONFIRMAR INSCRIPCION");
        estilizarBotonPrimario(btnConfirmar);
        btnConfirmar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> {
            if (ticketMostrado) {
                dispose();
            } else {
                control.manejarConfirmar();
            }
        });
        return btnConfirmar;
    }

    private JLabel crearLabelDato(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void estilizarBotonPrimario(JButton boton) {
        boton.setBackground(MORADO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
    }
    
    /**
     * Primer estado: se abre la ventana lista para que el alumno
     * escriba su ID. Se usa cuando el Control ya confirmo que hay cupo.
     */
    public void mostrarSolicitudID(Taller taller) {
        lblCurso.setText("CURSO: " + taller.getNombre());
        txtId.setText("");
        txtId.setEnabled(true);
        btnContinuar.setEnabled(true);
        btnConfirmar.setEnabled(false);
        cardLayout.show(panelCambiante, CARD_VACIO);
    }

    /**
     * Segundo estado: ya se encontro al alumno. Se muestran sus datos
     * y se "congela" el campo de ID (como se ve grisado en el Figma).
     */
    public void mostrarDatosAlumno(Alumno alumno) {
        lblDatosNombre.setText("Nombre: " + alumno.getNombre());
        lblDatosId.setText("ID: " + alumno.getIdAlumno());
        lblDatosSemestre.setText("Semestre: " + alumno.getSemestre() + "to");
        lblDatosPrograma.setText("Programa: " + alumno.getPrograma());

        txtId.setEnabled(false);
        txtId.setBackground(GRIS_DESHABILITADO);
        btnContinuar.setEnabled(false);
        btnConfirmar.setEnabled(true);

        cardLayout.show(panelCambiante, CARD_DATOS);
    }

    /**
     * Tercer estado: la inscripcion ya se confirmo. Se reemplaza la
     * zona de datos del alumno por el ticket generado.
     */
    public void mostrarTicket(Inscripcion inscripcion) {
        lblTicketFolio.setText("TICKET DE INSCRIPCION (" + inscripcion.getFolio() + ")");
        lblTicketAlumno.setText("Alumno: " + inscripcion.getAlumno().getNombre());
        lblTicketTaller.setText("Taller: " + inscripcion.getTaller().getNombre());
        lblTicketInstructor.setText("Instructor: " + inscripcion.getTaller().getInstructor());
        lblTicketFecha.setText("Fecha: " + inscripcion.getTaller().getFecha());

        btnConfirmar.setText("SALIR");
        ticketMostrado = true;
        cardLayout.show(panelCambiante, CARD_TICKET);
    }

    /**
     * Muestra un mensaje de error simple (por ejemplo, "alumno no
     * encontrado").
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

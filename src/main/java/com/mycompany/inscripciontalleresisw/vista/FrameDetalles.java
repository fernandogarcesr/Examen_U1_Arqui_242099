/*
 * Pantalla principal de la aplicacion. Muestra la lista de talleres disponibles a la
 * izquierda y, al seleccionar uno, sus detalles a la derecha.
 */
package com.mycompany.inscripciontalleresisw.vista;

import control.ControlInscripcion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import modelo.ModeloInscripcion;
import modelo.Taller;

/**
 *
 * @author Fercho
 */
public class FrameDetalles extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameDetalles.class.getName());

    private static final Color MORADO = new Color(92, 60, 150);
    private static final Color MORADO_OSCURO = new Color(68, 42, 116);
    private static final Color FONDO = new Color(245, 245, 248);
    private static final Color ROJO_SELECCION = new Color(196, 40, 40);

    private final ControlInscripcion control;

    private JList<Taller> listaTalleres;
    private DefaultListModel<Taller> modeloLista;
    private JLabel lblBadgeCantidad;
    private JLabel lblCupo;

    private JLabel lblNombreTaller;
    private JLabel lblInstructor;
    private JLabel lblFecha;
    private JLabel lblHorario;
    private JTextArea txtDescripcion;
    private JButton btnInscribir;

    /**
     * Creates new form FrameDetalles
     */
    public FrameDetalles(ControlInscripcion control) {
        this.control = control;
        // Nos registramos con el Control para que después nos pueda
        // mandar a actualizar (mostrarListaTalleres, mostrarDetalleTaller, etc.)
        control.setFrameDetalles(this);

        inicializarComponentes();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Inscripciones - Talleres (semana ISW)");
        setSize(820, 480);
        setMinimumSize(new Dimension(760, 420));
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(FONDO);

        getContentPane().add(crearHeader(), BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 16, 0));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panelCentral.setBackground(FONDO);
        panelCentral.add(crearPanelCursosDisponibles());
        panelCentral.add(crearPanelDetalleTaller());

        getContentPane().add(panelCentral, BorderLayout.CENTER);
    }

    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(MORADO);
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel titulo = new JLabel("Inscripciones - Talleres (semana ISW)");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        header.add(titulo, BorderLayout.WEST);
        return header;
    }

    private JPanel crearPanelCursosDisponibles() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 230)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        JLabel titulo = new JLabel("Cursos disponibles");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        encabezado.add(titulo, BorderLayout.WEST);

        lblBadgeCantidad = new JLabel("0 Talleres");
        lblBadgeCantidad.setOpaque(true);
        lblBadgeCantidad.setBackground(new Color(234, 229, 245));
        lblBadgeCantidad.setForeground(MORADO_OSCURO);
        lblBadgeCantidad.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBadgeCantidad.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        encabezado.add(lblBadgeCantidad, BorderLayout.EAST);

        panel.add(encabezado, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaTalleres = new JList<>(modeloLista);
        listaTalleres.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaTalleres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTalleres.setCellRenderer(new RenderizadorTaller());
        // Cada vez que cambia la seleccion, le avisamos al Control.
        listaTalleres.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listaTalleres.getSelectedValue() != null) {
                control.manejarSeleccionTaller(listaTalleres.getSelectedValue().getIdTaller());
            }
        });

        panel.add(new JScrollPane(listaTalleres), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelDetalleTaller() {
        JPanel panel = new JPanel(new BorderLayout(0, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 230)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        JLabel titulo = new JLabel("Detalles - Taller");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel datos = new JPanel();
        datos.setOpaque(false);
        datos.setLayout(new BoxLayout(datos, BoxLayout.Y_AXIS));

        lblNombreTaller = crearLabelTitulo("Selecciona un taller de la lista");
        lblInstructor = crearLabelDato("Instructor: --");
        lblFecha = crearLabelDato("Fecha: --");
        lblHorario = crearLabelDato("Horario: --");
        lblCupo = crearLabelDato("Cupo disponible: --");

        datos.add(lblNombreTaller);
        datos.add(Box.createVerticalStrut(10));
        datos.add(lblInstructor);
        datos.add(lblFecha);
        datos.add(lblHorario);
        datos.add(lblCupo);
        datos.add(Box.createVerticalStrut(10));

        JLabel lblDescTitulo = crearLabelDato("Descripción:");
        datos.add(lblDescTitulo);

        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        datos.add(txtDescripcion);

        panel.add(datos, BorderLayout.CENTER);

        btnInscribir = new JButton("INSCRIBIR");
        estilizarBotonPrimario(btnInscribir);
        btnInscribir.setEnabled(false);
        btnInscribir.addActionListener(e -> control.manejarClickInscribir());

        JPanel panelBoton = new JPanel(new BorderLayout());
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelBoton.add(btnInscribir, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel crearLabelTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
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
     * Pinta la lista de talleres recibida del Control y actualiza el contado.
     */
    public void mostrarListaTalleres(List<Taller> talleres) {
        modeloLista.clear();
        for (Taller taller : talleres) {
            modeloLista.addElement(taller);
        }
        lblBadgeCantidad.setText(talleres.size() + " Talleres");
    }

    /**
     * Llena el panel de la derecha con los datos del taller seleccionado y
     * habilita el boton de Inscribir.
     */
    public void mostrarDetalleTaller(Taller taller) {
        if (taller == null) {
            return;
        }
        lblNombreTaller.setText(taller.getNombre());
        lblInstructor.setText("Instructor: " + taller.getInstructor());
        lblFecha.setText("Fecha: " + taller.getFecha());
        lblHorario.setText("Horario: " + taller.getHorario());
        int disponibles = taller.getCupoMaximo() - taller.getInscritosActuales();
        lblCupo.setText("Cupo disponible: " + disponibles + " / " + taller.getCupoMaximo());
        txtDescripcion.setText(taller.getDescripcion());
        btnInscribir.setEnabled(taller.tieneCupoDisponible());
    }

    /**
     * Muestra un mensaje de error simple (por ejemplo, cuando ya no hay cupo en
     * el taller).
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Le dice como pintar cada fila de la lista de talleres: el taller
     * seleccionado se ve resaltado en rojo.
     */
    private static class RenderizadorTaller extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Taller) {
                Taller t = (Taller) value;
                int disponibles = t.getCupoMaximo() - t.getInscritosActuales();
                label.setText(t.getNombre() + "   (" + disponibles + "/" + t.getCupoMaximo() + " cupos)");
            }
            label.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            if (isSelected) {
                label.setForeground(ROJO_SELECCION);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            }
            return label;
        }
    }

    public void actualizarListaTalleres() {
    listaTalleres.repaint();
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
            .addGap(0, 817, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


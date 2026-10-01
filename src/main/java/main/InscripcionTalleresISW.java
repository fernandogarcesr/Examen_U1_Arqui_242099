/*clase para correr la aplicacion*/
package main;

import com.mycompany.inscripciontalleresisw.vista.FrameDetalles;
import control.ControlInscripcion;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import modelo.ModeloInscripcion;

/**
 *
 * @author Fercho
 */
public class InscripcionTalleresISW {
     public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
        }

        SwingUtilities.invokeLater(() -> {
            ModeloInscripcion modelo = new ModeloInscripcion();
            ControlInscripcion control = new ControlInscripcion(modelo);
            FrameDetalles frameDetalles = new FrameDetalles(control);

            control.iniciar();
            frameDetalles.setVisible(true);
        });
    }
}

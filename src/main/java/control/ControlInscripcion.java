/**
 * Control orquestador entre las pantallas  y
 * los datos. Las pantallas nunca tocan al Modelo directamente,
 * y el Modelo nunca sabe que existen las pantallas.
 * Cada metodo de esta clase corresponde a una interacción del usuario
 * (seleccionar un taller, dar clic en "Inscribir", etc.)
 */
package control;

import com.mycompany.inscripciontalleresisw.vista.FrameDetalles;
import com.mycompany.inscripciontalleresisw.vista.FrameInscripcion;
import modelo.Alumno;
import modelo.Inscripcion;
import modelo.ModeloInscripcion;
import modelo.Taller;

/**
 *
 * @author Fercho
 */
public class ControlInscripcion {
    private final ModeloInscripcion modelo;
    private FrameDetalles frameDetalles;
    private FrameInscripcion frameInscripcion;

    // Guardamos aquí el taller y el alumno  mientras el usuario
    // avanza por las pantallas
    private Taller tallerSeleccionado;
    private Alumno alumnoEncontrado;

    public ControlInscripcion(ModeloInscripcion modelo) {
        this.modelo = modelo;
    }

    /**
     * La pantalla principal se registra aqui apenas se crea, para que
     * el Control pueda llamarla despues cuando necesite actualizarla.
     */
    public void setFrameDetalles(FrameDetalles frameDetalles) {
        this.frameDetalles = frameDetalles;
    }

    /**
     * Se llama una sola vez, al arrancar la aplicacion: le pide al
     * Modelo la lista completa de talleres y se la manda a pintar
     * a la pantalla principal.
     */
    public void iniciar() {
        frameDetalles.mostrarListaTalleres(modelo.obtenerTalleres());
    }

    /**
     * El usuario dio clic en un taller de la lista. Buscamos sus datos
     * completos en el Modelo y se los mandamos a la Vista para que
     * los muestre en el panel de la derecha.
     */
    public void manejarSeleccionTaller(String idTaller) {
        Taller taller = modelo.buscarTallerPorId(idTaller);
        this.tallerSeleccionado = taller;
        frameDetalles.mostrarDetalleTaller(taller);
    }

    /**
     * El usuario dio clic en el botón "Inscribir". Antes de abrir la
     * ventana para pedir el ID, checamos que todavía haya cupo.
     */
    public void manejarClickInscribir() {
        if (tallerSeleccionado == null) {
            return;
        }
        boolean hayCupo = modelo.tieneCupoDisponible(tallerSeleccionado.getIdTaller());
        if (!hayCupo) {
            frameDetalles.mostrarError("Este taller ya no tiene cupo disponible.");
            return;
        }
        // Se crea la ventana de inscripcion aquí, ya con cupo confirmado.
        frameInscripcion = new FrameInscripcion(this);
        frameInscripcion.mostrarSolicitudID(tallerSeleccionado);
        frameInscripcion.setVisible(true);
    }

    /**
     * El alumno escribio su ID y dio clic en "Continuar". Buscamos
     * si ese ID corresponde a algun alumno de la lista mockeada.
     */
    public void manejarContinuar(String idAlumno) {
        Alumno alumno = modelo.buscarAlumnoPorId(idAlumno);
        if (alumno == null) {
            frameInscripcion.mostrarError("No se encontro ningun alumno con ese ID.");
            return;
        }
        if (modelo.yaEstaInscrito(idAlumno, tallerSeleccionado.getIdTaller())) {
            frameInscripcion.mostrarError("Ya estas inscrito en este taller.");
            return;
        }
        this.alumnoEncontrado = alumno;
        frameInscripcion.mostrarDatosAlumno(alumno);
    }

    /**
     * El alumno confirmo su inscripcion: le pedimos al Modelo que
     * genere el ticket (le suma uno al contador de
     * inscritos del taller) y se lo mandamos a mostrar a la pantalla.
     */
    public void manejarConfirmar() {
        if (alumnoEncontrado == null || tallerSeleccionado == null) {
            return;
        }
        Inscripcion inscripcion = modelo.registrarInscripcion(alumnoEncontrado, tallerSeleccionado);
        frameInscripcion.mostrarTicket(inscripcion);
        frameDetalles.actualizarListaTalleres();
        frameDetalles.mostrarDetalleTaller(tallerSeleccionado);
    }
    
    
}

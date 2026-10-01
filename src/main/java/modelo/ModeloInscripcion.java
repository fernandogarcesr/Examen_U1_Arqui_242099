/**
 * Aquí esta toda la información del sistema (la lista de talleres, la lista de alumnos 
 * y las inscripciones que se van generando) y las operaciones para consultarla o modificarla.
 * de donde el control saca todos los datos
 * Datos mockeados
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Fercho
 */
public class ModeloInscripcion {
     private final List<Taller> talleres;
    private final List<Alumno> alumnos;
    private final List<Inscripcion> inscripciones;
    private final Random generadorFolios;

    public ModeloInscripcion() {
        this.talleres = new ArrayList<>();
        this.alumnos = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
        this.generadorFolios = new Random();
        cargarTalleresDePrueba();
        cargarAlumnosDePrueba();
    }

    /**
     * Carga los 6 talleres disponibles (mock).
     * Todos arrancan con cupo maximo de 15 alumnos.
     */
    private void cargarTalleresDePrueba() {
        talleres.add(new Taller("T1", "DevOps", "Ana Torres", "15/nov/2026",
                "9am - 12pm", "Introducción a prácticas DevOps: integración y despliegue continuo.", 15));
        talleres.add(new Taller("T2", "Ciberseguridad Aplicada", "Carlos Alberto", "15/nov/2026",
                "9am-12pm / 1pm-4pm / 5pm-8pm",
                "Fundamentos de ciberseguridad aplicados a proyectos reales: amenazas, buenas prácticas y herramientas básicas de defensa.", 15));
        talleres.add(new Taller("T3", "Automatización con IA", "Luis Fernández", "16/nov/2026",
                "10am - 1pm", "Cómo usar herramientas de inteligencia artificial para automatizar tareas del día a día.", 15));
        talleres.add(new Taller("T4", "Datawarehouse", "Marcela Ruiz", "16/nov/2026",
                "4pm - 7pm", "Conceptos básicos de almacenes de datos y su uso en análisis de información.", 15));
        talleres.add(new Taller("T5", "Diseño de Microservicios", "Héctor Valenzuela", "17/nov/2026",
        "9am - 12pm", "Fundamentos de arquitecturas desacopladas, comunicación vía REST/gRPC y diseño de APIs robustas.", 15));
        talleres.add(new Taller("T6", "Frontend web", "Paola Nuñez", "17/nov/2026",
                "1pm - 4pm", "Construcción de interfaces web modernas con HTML, CSS y JavaScript.", 15));
    }

    /**
     * Carga alumnos de prueba
     */
    private void cargarAlumnosDePrueba() {
        alumnos.add(new Alumno("242099", "Fernando Garcés Rodriguez", 7, "ISW 2023"));
        alumnos.add(new Alumno("245935", "Andrea Castro Gonzales", 7, "ISW 2023"));
        alumnos.add(new Alumno("244150", "Luis Gerardo Gutierrez Mendez", 5, "ISW 2024"));
        alumnos.add(new Alumno("245077", "Daniela Soto Perales", 6, "ISW 2023"));
        alumnos.add(new Alumno("245510", "Jesus Daniel Nuñez", 3, "ISW 2025"));
    }

    /**
     * Regresa la lista completa de talleres, para pintarla en la
     * pantalla principal.
     */
    public List<Taller> obtenerTalleres() {
        return talleres;
    }

    /**
     * Busca un taller por su ID. Si no lo encuentra regresa null
     */
    public Taller buscarTallerPorId(String idTaller) {
        for (Taller taller : talleres) {
            if (taller.getIdTaller().equals(idTaller)) {
                return taller;
            }
        }
        return null;
    }

    /**
     * Revisa si un taller todavía tiene lugar disponible.
     * Esto se checa ANTES de pedirle el ID al alumno.
     */
    public boolean tieneCupoDisponible(String idTaller) {
        Taller taller = buscarTallerPorId(idTaller);
        return taller != null && taller.tieneCupoDisponible();
    }

    /**
     * Busca un alumno por su ID dentro de la lista mockeada.
     * Si el ID no coincide con ninguno de los alumnos de prueba,
     * regresa null (eso dispara el mensaje de "alumno no encontrado").
     */
    public Alumno buscarAlumnoPorId(String idAlumno) {
        for (Alumno alumno : alumnos) {
            if (alumno.getIdAlumno().equals(idAlumno)) {
                return alumno;
            }
        }
        return null;
    }

    /**
     * Genera el ticket de inscripcion: crea el folio, arma el objeto
     * Inscripcion, lo guarda en la lista de inscripciones y le suma
     * uno al contador de inscritos del taller correspondiente.
     */
    public Inscripcion registrarInscripcion(Alumno alumno, Taller taller) {
        String folio = generarFolio();
        Inscripcion inscripcion = new Inscripcion(folio, "30/sep/2026", alumno, taller);
        inscripciones.add(inscripcion);
        taller.incrementarInscritos();
        return inscripcion;
    }

    /**
     * Crea un folio corto tipo "AF576JK"
     */
    private String generarFolio() {
        String letras = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        String numeros = "0123456789";
        StringBuilder folio = new StringBuilder();
        folio.append(letras.charAt(generadorFolios.nextInt(letras.length())));
        folio.append(letras.charAt(generadorFolios.nextInt(letras.length())));
        for (int i = 0; i < 3; i++) {
            folio.append(numeros.charAt(generadorFolios.nextInt(numeros.length())));
        }
        folio.append(letras.charAt(generadorFolios.nextInt(letras.length())));
        folio.append(letras.charAt(generadorFolios.nextInt(letras.length())));
        return folio.toString();
    }

    /**
     * Revisa si este alumno ya tiene una inscripcion registrada para este mismo
     * taller, para no dejarlo inscribirse dos veces.
     */
    public boolean yaEstaInscrito(String idAlumno, String idTaller) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getAlumno().getIdAlumno().equals(idAlumno)
                    && inscripcion.getTaller().getIdTaller().equals(idTaller)) {
                return true;
            }
        }
        return false;
    }
}

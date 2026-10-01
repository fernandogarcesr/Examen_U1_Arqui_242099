/**
 * Representa un taller de la Semana ISW como Ciberseguridad Aplicada.
 * Esta clase solo guarda la información de UN taller
 */
package modelo;

/**
 *
 * @author Fercho
 */
public class Taller {

    private final String idTaller;
    private final String nombre;
    private final String instructor;
    private final String fecha;
    private final String horario;
    private final String descripcion;
    private final int cupoMaximo;
    private int inscritosActuales;

    public Taller(String idTaller, String nombre, String instructor, String fecha,
                  String horario, String descripcion, int cupoMaximo) {
        this.idTaller = idTaller;
        this.nombre = nombre;
        this.instructor = instructor;
        this.fecha = fecha;
        this.horario = horario;
        this.descripcion = descripcion;
        this.cupoMaximo = cupoMaximo;
        this.inscritosActuales = 0;
    }

    public String getIdTaller() {
        return idTaller;
    }

    public String getNombre() {
        return nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHorario() {
        return horario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public int getInscritosActuales() {
        return inscritosActuales;
    }

    /**
     * Checa si todavía caben mas alumnos en este taller.
     * Se usa justo antes de pedirle el ID al alumno, para no dejarlo
     * llenar el formulario si de todos modos ya no hay lugar.
     */
    public boolean tieneCupoDisponible() {
        return inscritosActuales < cupoMaximo;
    }

    /**
     * Suma un inscrito mas al contador. Se llama una sola vez,
     * justo cuando se confirma una inscripcion nueva a este taller.
     */
    public void incrementarInscritos() {
        inscritosActuales++;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

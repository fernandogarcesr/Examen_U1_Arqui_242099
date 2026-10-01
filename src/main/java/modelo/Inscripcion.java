/**
 * Es el comprobante que se genera cuando un alumno confirma su
 * inscripcion a un taller: guarda el folio del ticket, la fecha en que
 * se genero, y a quien/para que taller pertenece.
 */
package modelo;

/**
 *
 * @author Fercho
 */
public class Inscripcion {
    private final String folio;
    private final String fecha;
    private final Alumno alumno;
    private final Taller taller;

    public Inscripcion(String folio, String fecha, Alumno alumno, Taller taller) {
        this.folio = folio;
        this.fecha = fecha;
        this.alumno = alumno;
        this.taller = taller;
    }

    public String getFolio() {
        return folio;
    }

    public String getFecha() {
        return fecha;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Taller getTaller() {
        return taller;
    }
}

/**
 * Representa a un alumno de ITSON. Igual que Taller, es solo un
 * "contenedor" de datos: nombre, ID, semestre y programa educativo.
 */
package modelo;

/**
 *
 * @author Fercho
 */
public class Alumno {
    
    private final String idAlumno;
    private final String nombre;
    private final int semestre;
    private final String programa;

    public Alumno(String idAlumno, String nombre, int semestre, String programa) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.semestre = semestre;
        this.programa = programa;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSemestre() {
        return semestre;
    }

    public String getPrograma() {
        return programa;
    }
}

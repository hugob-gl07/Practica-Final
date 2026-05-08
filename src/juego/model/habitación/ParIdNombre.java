package juego.model.habitación;

class ParIdNombre implements Comparable<ParIdNombre> {
    private int idHabitacion;
    private String nombreNodo;

    public ParIdNombre(int id, String nombre) {
        this.idHabitacion = id;
        this.nombreNodo = nombre;
    }
    public int getIdHabitacion() {
        return idHabitacion;
    }
    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
    public String getNombreNodo() {
        return nombreNodo;
    }
    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }
    @Override
    public int compareTo(ParIdNombre o) {
        return Integer.compare(this.idHabitacion, o.idHabitacion);
    }
}


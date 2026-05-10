package juego.model.habitación;

class ParNombreHabitacion implements Comparable<ParNombreHabitacion> {
    private String nombreNodo;
    private Habitacion habitacion;

    public ParNombreHabitacion(String nombreNodo, Habitacion habitacion) {
        this.nombreNodo = nombreNodo;
        this.habitacion = habitacion;
    }
    public String getNombreNodo() {
        return nombreNodo;
    }
    public void setNombreNodo(String nombreNodo) {
        this.nombreNodo = nombreNodo;
    }
    public Habitacion getHabitacion() {
        return habitacion;
    }
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    @Override
    public int compareTo(ParNombreHabitacion o) {
        return this.nombreNodo.compareTo(o.nombreNodo);
    }
}

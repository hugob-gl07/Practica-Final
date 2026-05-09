package juego.model.entidades;

public class Objeto implements Comparable<Objeto>{
    protected int id;
    protected String nombre;
    protected TipoObjeto tipo;
    protected Rareza rareza;
    protected String descripcion;
    protected String [] nombreEstadisticas;
    protected int[] valorEstadisticas;
    protected int numeroEstadisticas;
    protected String efecto;
    protected float peso;
    public Objeto(int id, String nombre, TipoObjeto tipo , Rareza rareza) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.nombreEstadisticas = new String[10];
        this.valorEstadisticas = new int[10];
        this.numeroEstadisticas = 0;
    }
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public TipoObjeto getTipo() {
        return tipo;
    }
    public Rareza getRareza() {
        return rareza;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getEfecto() {
        return efecto;
    }
    public float getPeso() {
        return peso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
    public void addEstadistica(String nombre, int valor) {
        nombreEstadisticas[numeroEstadisticas] = nombre;
        valorEstadisticas[numeroEstadisticas] = valor;
        numeroEstadisticas++;
    }
    public int getValorEstadisticas(String nombre) {
        for (int i = 0; i < numeroEstadisticas; i++) {
            if(nombreEstadisticas[i].equals(nombre)){
                return valorEstadisticas[i];
            }
        }
        return 0;
    }
    @Override
    public String toString() {
        return "Objeto{ id= " + id + ", nombre= " + nombre + ", tipo= " + tipo + ", rareza= " + rareza + ", descripción= " + descripcion + "}";
    }
    @Override
    public int compareTo(Objeto o) {
        return this.id - o.id;
    }

}

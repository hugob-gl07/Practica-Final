package juego.model.entidades;

/**
 * Representa un objeto del juego que el jugador puede obtener, equipar o consumir.
 * Cada objeto tiene un tipo, una rareza y un conjunto de estadísticas que modifican al portador.
 * Los objetos se ordenan por su identificador numérico al implementar Comparable.
 */
public class Objeto implements Comparable<Objeto>{
    protected int id;                       // Identificador único del objeto
    protected String nombre;               // Nombre del objeto mostrado en el inventario
    protected TipoObjeto tipo;             // Categoría funcional del objeto (ARMA, ARMADURA, etc.)
    protected Rareza rareza;               // Nivel de rareza que escala las estadísticas
    protected String descripcion;          // Descripción del objeto para el jugador
    protected String [] nombreEstadisticas; // Nombres de cada estadística que modifica el objeto
    protected int[] valorEstadisticas;     // Valores correspondientes a cada estadística
    protected int numeroEstadisticas;      // Número de estadísticas actualmente registradas
    protected String efecto;              // Descripción del efecto especial del objeto
    protected float peso;                 // Peso del objeto en el inventario

    /**
     * Crea un objeto con su identificador, nombre, tipo y rareza.
     * Las estadísticas se añaden posteriormente con {@link #addEstadistica}.
     * @param id     identificador único del objeto
     * @param nombre nombre visible del objeto
     * @param tipo   categoría funcional del objeto
     * @param rareza nivel de rareza del objeto
     */
    public Objeto(int id, String nombre, TipoObjeto tipo , Rareza rareza) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.nombreEstadisticas = new String[10];  // Capacidad máxima de 10 estadísticas
        this.valorEstadisticas = new int[10];
        this.numeroEstadisticas = 0; // El objeto empieza sin estadísticas registradas
    }

    /**
     * Devuelve el identificador único del objeto.
     * @return id del objeto
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el nombre del objeto.
     * @return nombre del objeto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el tipo del objeto.
     * @return tipo funcional del objeto
     */
    public TipoObjeto getTipo() {
        return tipo;
    }

    /**
     * Devuelve la rareza del objeto.
     * @return nivel de rareza del objeto
     */
    public Rareza getRareza() {
        return rareza;
    }

    /**
     * Devuelve la descripción del objeto.
     * @return descripción del objeto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve el efecto especial del objeto.
     * @return efecto especial del objeto
     */
    public String getEfecto() {
        return efecto;
    }

    /**
     * Devuelve el peso del objeto.
     * @return peso del objeto
     */
    public float getPeso() {
        return peso;
    }

    /**
     * Establece la descripción del objeto.
     * @param descripcion nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece el efecto especial del objeto.
     * @param efecto texto que describe el efecto
     */
    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    /**
     * Establece el peso del objeto.
     * @param peso nuevo peso en unidades de carga
     */
    public void setPeso(float peso) {
        this.peso = peso;
    }

    /**
     * Registra una nueva estadística en el objeto.
     * Cada objeto puede tener hasta 10 estadísticas; llamar más de 10 veces puede causar un error de array.
     * @param nombre nombre de la estadística (por ejemplo "vida", "ataque")
     * @param valor  valor numérico de la estadística
     */
    public void addEstadistica(String nombre, int valor) {
        nombreEstadisticas[numeroEstadisticas] = nombre; // Guardamos el nombre en la posición actual
        valorEstadisticas[numeroEstadisticas] = valor;   // Guardamos el valor en la misma posición
        numeroEstadisticas++;                            // Incrementamos el contador de estadísticas
    }

    /**
     * Busca y devuelve el valor de la estadística con el nombre indicado.
     * Recorre el array de estadísticas hasta encontrar una coincidencia.
     * @param nombre nombre de la estadística a buscar
     * @return valor de la estadística, o 0 si no existe o el nombre es null
     */
    public int getValorEstadisticas(String nombre) {
        if (nombre == null) {
            return 0; // No buscamos estadísticas nulas
        }
        for (int i = 0; i < numeroEstadisticas; i++) {
            if(nombreEstadisticas[i] != null && nombreEstadisticas[i].equals(nombre)){
                return valorEstadisticas[i]; // Encontrada: devolvemos el valor
            }
        }
        return 0; // La estadística no está registrada en este objeto
    }

    /**
     * Devuelve una representación en texto del objeto con sus atributos principales.
     * @return cadena con id, nombre, tipo, rareza y descripción del objeto
     */
    @Override
    public String toString() {
        return "Objeto{ id= " + id + ", nombre= " + nombre + ", tipo= " + tipo + ", rareza= " + rareza + ", descripción= " + descripcion + "}";
    }

    /**
     * Compara este objeto con otro por su identificador numérico.
     * @param o objeto con el que comparar
     * @return diferencia de ids: negativo si este va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(Objeto o) {
        return this.id - o.id; // Ordenamos por id ascendente
    }
}

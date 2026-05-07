package EstructurasUtilizadas.Grafos;

/**
 * Representa una arista dirigida del grafo.
 * Conecta un nodo origen con un nodo destino y lleva asociada una etiqueta (relación)
 * que puede interpretarse como peso numérico si su valor es un número entero.
 */
public class Arista implements Comparable<Arista>{

    private Nodo origen;    // Nodo desde el que sale la arista
    private Nodo destino;   // Nodo al que llega la arista
    private String etiqueta; // Etiqueta o relación que identifica la arista
    private int peso;        // Peso numérico explícito (alternativa a parsear la etiqueta)

    /**
     * Crea una arista dirigida desde el nodo origen hasta el nodo destino con la etiqueta indicada.
     * @param origen   nodo de partida de la arista
     * @param etiqueta etiqueta o relación que describe la arista
     * @param destino  nodo de llegada de la arista
     */
    public Arista(Nodo origen, String etiqueta, Nodo destino) {
        this.origen = origen;
        this.destino = destino;
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve el nodo al que llega la arista.
     * @return nodo destino
     */
    public Nodo getDestino() {
        return destino;
    }

    /**
     * Establece el nodo destino de la arista.
     * @param destino nuevo nodo destino
     */
    public void setDestino(Nodo destino) {
        this.destino = destino;
    }

    /**
     * Devuelve la etiqueta de la arista.
     * @return etiqueta o relación de la arista
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Establece la etiqueta de la arista.
     * @param etiqueta nueva etiqueta
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Devuelve el nodo desde el que sale la arista.
     * @return nodo origen
     */
    public Nodo getOrigen() {
        return origen;
    }

    /**
     * Establece el nodo origen de la arista.
     * @param origen nuevo nodo origen
     */
    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }

    /**
     * Devuelve el peso numérico de la arista interpretando su etiqueta como entero.
     * Si la etiqueta no es un número válido devuelve 1 como valor por defecto.
     * @return peso de la arista como entero
     */
    public int getPeso() {
        int respuesta = 0;
        try {
            respuesta = Integer.parseInt(etiqueta); // Intentamos parsear la etiqueta como entero
        } catch (NumberFormatException e) {
            respuesta = 1; // La etiqueta no es numérica: usamos 1 como peso por defecto
        }
        return respuesta;
    }

    /**
     * Establece el peso numérico explícito de la arista.
     * @param peso nuevo peso de la arista
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * Compara esta arista con otra por su etiqueta en orden alfabético.
     * @param otra arista con la que comparar
     * @return valor negativo si esta va antes, 0 si son iguales, positivo si va después
     */
    @Override
    public int compareTo(Arista otra) {
        return this.etiqueta.compareTo(otra.etiqueta); // Delegamos la comparación en el orden natural de String
    }
}

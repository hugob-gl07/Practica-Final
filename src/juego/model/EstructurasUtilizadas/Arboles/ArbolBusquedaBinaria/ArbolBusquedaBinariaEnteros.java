package juego.model.EstructurasUtilizadas.Arboles.ArbolBusquedaBinaria;
import juego.model.EstructurasUtilizadas.LSE.Iterador;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
/**
 * Subclase de ArbolBusquedaBinaria especializada para trabajar con números enteros.
 * Añade la operación de calcular la suma de todos los elementos insertados.
 */
public class ArbolBusquedaBinariaEnteros extends ArbolBusquedaBinaria<Integer> {
    /**
     * Calcula la suma de todos los elementos almacenados en el árbol.
     * Recorre los elementos en orden central y los acumula.
     * @return suma de todos los enteros del árbol, 0 si el árbol está vacío
     */
    public int getSuma() {
        ListaSimplementeEnlazada<Integer> lista = getListaOrdenadaCentral(); // Obtenemos todos los elementos en orden central (inorden)
        Iterador<Integer> iterador = lista.getIterador();                    // Creamos un iterador para recorrer la lista
        int suma = 0;                                                        // Inicializamos el acumulador a 0
        while (iterador.hasNext()) {
            suma += iterador.next(); // Sumamos cada elemento al acumulador
        }
        return suma; // Devolvemos la suma total de todos los elementos
    }
}

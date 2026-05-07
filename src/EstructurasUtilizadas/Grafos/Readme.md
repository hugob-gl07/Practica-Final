# Ejercicios sobre EstructurasUtilizadas.Grafos de Conocimiento

## Ejercicio 1: Cálculo del camino mínimo
En nuestra implementación, el concepto de "camino mínimo" entre dos entidades A y B puede calcularse de dos formas, dependiendo del criterio:
1. **Por menor cantidad de saltos (aristas):** Utilizando el método `caminoMínimo(String origen, String destino)`. Este método implementa una búsqueda en anchura (BFS) y devuelve la ruta más corta en términos de nodos recorridos.
2. **Por menor coste o peso:** Utilizando el método `dijkstra(String origen, String destino)`. Este algoritmo utiliza una cola de prioridad mínima para encontrar la ruta cuya suma de pesos numéricos en las aristas sea la menor posible.

## Ejercicio 4 (Teoría): Lugares de nacimiento de Premios Nobel
Si añadimos a una persona normal (ej. Antonio) al grafo, no podemos simplemente buscar todos los destinos de la relación `nace_en`, ya que nos devolvería también el lugar de nacimiento de Antonio, que no es un Premio Nobel.
**Caminos necesarios a recorrer para obtener la respuesta correcta:**
1. Buscar qué nodos (sujetos) tienen una relación `gana_premio` apuntando al nodo `premio:Nobel`.
2. Solo para los sujetos recuperados en el paso 1, buscar el destino de su relación `nace_en`.

## Ejercicio 5: Tipos de nodos en el grafo
El tipo de un nodo está determinado por la etiqueta (prefijo) que aparece antes de los dos puntos (`:`) en su nombre (según el método `getTiposdeNodos()`). El grafo tendrá tantos tipos como prefijos se utilicen (por ejemplo: `persona`, `lugar`, `ciudad`, `profesion`, `premio`).

## Ejercicio 6: Ontologías
* **¿Qué es?** Una ontología es una representación formal y estructurada del conocimiento dentro de un dominio específico. Define clases, propiedades y reglas semánticas.
* **Relación con grafos:** Los grafos de conocimiento se construyen sobre ontologías. El grafo tiene los datos reales, y la ontología define el esquema permitido.
* **Aplicación a nuestro problema:** Nuestra regla de prefijos (ej. `persona:`) es una ontología básica. Podríamos expandirla para validar datos (ej. impedir que una ciudad "nazca en" una persona) o para hacer inferencia lógica (saber que un físico es una subclase de científico).
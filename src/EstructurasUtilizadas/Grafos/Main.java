package EstructurasUtilizadas.Grafos;
import EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- EJERCICIO 2: GRAFO DISJUNTO ---");
        ejercicioGrafoDisjunto();

        System.out.println("\n--- EJERCICIO 3: FÍSICO NACIDO EN CIUDAD DE EINSTEIN ---");
        ejercicioNobelFisica();

        System.out.println("\n--- EJERCICIO 4: AÑADIR TRIPLETA ANTONIO ---");
        ejercicioAnadirAntonio();
    }

    // --- CÓDIGO DEL EJERCICIO 2 ---
    public static void ejercicioGrafoDisjunto() {
        Grafo grafoConexo = new Grafo(1);
        // Asegúrate de que la ruta al archivo es correcta en tu proyecto
        grafoConexo.cargarDesdeJson("conexo.json");
        System.out.println("¿Es disjunto el grafo 'conexo.json'?: " + grafoConexo.esDisjunto());

        Grafo grafoDisjunto = new Grafo(1);
        grafoDisjunto.cargarDesdeJson("disjunto.json");
        System.out.println("¿Es disjunto el grafo 'disjunto.json'?: " + grafoDisjunto.esDisjunto());
    }

    // --- CÓDIGO DEL EJERCICIO 3 ---
    public static void ejercicioNobelFisica() {
        Grafo grafo = new Grafo(1);
        grafo.cargarDesdeJson("nobel.json");

        // 1. Obtenemos la ciudad de Einstein
        String ciudadEinstein = grafo.getValor("persona:Albert_Einstein", "nace_en");
        System.out.println("Einstein nació en: " + ciudadEinstein);

        // 2. Buscamos todos los que nacieron en esa ciudad
        ListaSimplementeEnlazada<String> nacidosEnMismaCiudad = grafo.buscarPorRelacionYValor("nace_en", ciudadEinstein);

        // 3. Filtramos los que son físicos y que no sean el propio Einstein
        System.out.println("Físicos famosos nacidos en " + ciudadEinstein + " (excluyendo a Einstein):");
        for (int i = 0; i < nacidosEnMismaCiudad.getSize(); i++) {
            String nombrePersona = nacidosEnMismaCiudad.getAt(i);

            if (!nombrePersona.equals("persona:Albert_Einstein")) {
                String profesion = grafo.getValor(nombrePersona, "profesion");
                if (profesion != null && profesion.equals("profesion:Fisico")) {
                    System.out.println("- " + nombrePersona);
                }
            }
        }
    }

    // --- CÓDIGO DEL EJERCICIO 4 (Parte práctica) ---
    public static void ejercicioAnadirAntonio() {
        Grafo grafo = new Grafo(1);
        // Añadimos la tripleta indicada
        grafo.agregarArista("persona:Antonio", "nace_en", "lugar:Villarrubia de los Caballeros");

        System.out.println("Tripleta de Antonio añadida con éxito.");
        System.out.println("Lugar de nacimiento de Antonio: " + grafo.getValor("persona:Antonio", "nace_en"));
    }
}
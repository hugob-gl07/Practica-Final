# Ejemplos de Uso y Casos de Prueba

## 1. Ejemplos de Uso

### Crear una Habitación Simple

```java
// Crear habitación 10x10
Habitacion sala = new Habitacion(1, "Sala Principal", 10, 10);

// Acceder a la matriz
MatrizHabitacion matriz = sala.getMatriz();

// Obtener una celda
Celda celda = matriz.getCelda(5, 5);

// Verificar si está vacía
if (celda.estaVacia()) {
    System.out.println("Celda vacía");
}

// Asignar un objeto
Objeto pocion = new Pocion("Poción de Vida", "Recupera 50 HP");
celda.setObjeto(pocion);

// Verificar contenido
System.out.println("Objeto: " + celda.getObjeto().getNombre());
```

### Crear Puertas Entre Habitaciones

```java
// Crear dos habitaciones
Habitacion sala1 = new Habitacion(1, "Sala 1", 10, 10);
Habitacion sala2 = new Habitacion(2, "Sala 2", 15, 15);

// Crear puertas
Puerta puerta1a2 = new Puerta(2, "Puerta Norte");
Puerta puerta2a1 = new Puerta(1, "Puerta Sur");

// Añadir puertas a habitaciones
sala1.agregarPuerta(puerta1a2);
sala2.agregarPuerta(puerta2a1);

// Abrir puerta
puerta1a2.abrir();

// Verificar estado
if (puerta1a2.estaAbierta()) {
    System.out.println("Puerta abierta hacia habitación " + puerta1a2.getHabitacionDestino());
}
```

### Crear Grafo de Habitaciones

```java
// Crear grafo
GrafoHabitaciones mapa = new GrafoHabitaciones();

// Crear 3 habitaciones
Habitacion h1 = new Habitacion(1, "Entrada", 10, 10);
Habitacion h2 = new Habitacion(2, "Pasillo", 10, 20);
Habitacion h3 = new Habitacion(3, "Salida", 10, 10);

// Agregar al grafo
mapa.agregarHabitacion(h1);
mapa.agregarHabitacion(h2);
mapa.agregarHabitacion(h3);

// Conectar habitaciones
mapa.conectarHabitaciones(h1, h2, "Puerta Este");
mapa.conectarHabitaciones(h2, h3, "Puerta Norte");

// Cambiar habitación actual
mapa.cambiarHabitacion(1);

// Obtener camino mínimo
LSE<Habitacion> camino = mapa.caminoMinimoHabitaciones(1, 3);
// Resultado: [Habitacion(1), Habitacion(2), Habitacion(3)]

// Obtener camino con Dijkstra
LSE<Habitacion> caminoOptimo = mapa.dijkstraHabitaciones(1, 3);
```

### Filtrar Celdas por Contenido

```java
// Obtener todas las celdas con enemigos
LSE<Celda> celdasConEnemigos = sala.obtenerEnemigos();

// Iterar sobre ellas
for (Celda c : celdasConEnemigos) {
    Enemigo e = (Enemigo) c.getEntidad();
    System.out.println("Enemigo en (" + c.getFila() + ", " + c.getColumna() + ")");
}

// Obtener celdas alcanzables desde posición del jugador
LSE<Celda> alcanzables = matriz.obtenerCeldasAlcanzables(5, 5, 3);
// Retorna todas las celdas a distancia Manhattan <= 3 desde (5,5)
```

---

## 2. Casos de Prueba (JUnit)

### Test: Celda

```java
@Test
public void testCeldaVacia() {
    Celda celda = new Celda(0, 0);
    assertTrue(celda.estaVacia());
    assertEquals(TipoCelda.VACIA, celda.getTipo());
}

@Test
public void testCeldaConTipo() {
    Celda celda = new Celda(0, 0, TipoCelda.PUERTA);
    assertTrue(celda.tienePuerta());
    assertFalse(celda.tieneTrampa());
}

@Test
public void testAsignarEntidad() {
    Celda celda = new Celda(0, 0);
    Enemigo enemigo = new Enemigo(...);
    celda.setEntidad(enemigo);
    assertFalse(celda.estaVacia());
    assertEquals(enemigo, celda.getEntidad());
}

@Test
public void testAsignarObjeto() {
    Celda celda = new Celda(0, 0);
    Objeto pocion = new Pocion(...);
    celda.setObjeto(pocion);
    assertEquals(pocion, celda.getObjeto());
}

@Test
public void testLimpiar() {
    Celda celda = new Celda(0, 0);
    celda.setEntidad(new Enemigo(...));
    celda.setObjeto(new Pocion(...));
    celda.limpiar();
    assertTrue(celda.estaVacia());
    assertNull(celda.getEntidad());
    assertNull(celda.getObjeto());
}
```

### Test: MatrizHabitacion

```java
@Test
public void testCrearMatriz() {
    MatrizHabitacion matriz = new MatrizHabitacion(10, 10);
    assertEquals(10, matriz.getNumFilas());
    assertEquals(10, matriz.getNumColumnas());
}

@Test
public void testObtenerCelda() {
    MatrizHabitacion matriz = new MatrizHabitacion(10, 10);
    Celda celda = matriz.getCelda(5, 5);
    assertNotNull(celda);
    assertEquals(5, celda.getFila());
    assertEquals(5, celda.getColumna());
}

@Test
public void testAsignarCelda() {
    MatrizHabitacion matriz = new MatrizHabitacion(10, 10);
    Celda nuevaCelda = new Celda(5, 5, TipoCelda.PUERTA);
    matriz.setCelda(5, 5, nuevaCelda);
    Celda recuperada = matriz.getCelda(5, 5);
    assertEquals(TipoCelda.PUERTA, recuperada.getTipo());
}

@Test
public void testObtenerCeldasConEnemigos() {
    MatrizHabitacion matriz = new MatrizHabitacion(10, 10);
    Celda celda = matriz.getCelda(0, 0);
    celda.setEntidad(new Enemigo(...));
    
    LSE<Celda> celdasConEnemigos = matriz.obtenerCeldasConEnemigos();
    assertTrue(celdasConEnemigos.contains(celda));
}

@Test
public void testObtenerCeldasAlcanzables() {
    MatrizHabitacion matriz = new MatrizHabitacion(10, 10);
    LSE<Celda> alcanzables = matriz.obtenerCeldasAlcanzables(5, 5, 2);
    // Debe retornar celdas a distancia Manhattan <= 2 desde (5,5)
    assertNotNull(alcanzables);
    assertTrue(alcanzables.size() > 0);
}

@Test
public void testIndicesFueraDeRango() {
    MatrizHabitacion matriz = new MatrizHabitacion(10, 10);
    assertThrows(ListaIndiceInvalidoExceptions.class, () -> {
        matriz.getCelda(-1, 5);
    });
    assertThrows(ListaIndiceInvalidoExceptions.class, () -> {
        matriz.getCelda(10, 5);
    });
}
```

### Test: Puerta

```java
@Test
public void testCrearPuerta() {
    Puerta puerta = new Puerta(2, "Puerta Norte");
    assertEquals(2, puerta.getHabitacionDestino());
    assertEquals("Puerta Norte", puerta.getEtiqueta());
    assertFalse(puerta.estaAbierta());
}

@Test
public void testAbrirCerrarPuerta() {
    Puerta puerta = new Puerta(2, "Puerta");
    puerta.abrir();
    assertTrue(puerta.estaAbierta());
    puerta.cerrar();
    assertFalse(puerta.estaAbierta());
}
```

### Test: Habitacion

```java
@Test
public void testCrearHabitacion() {
    Habitacion hab = new Habitacion(1, "Sala", 10, 10);
    assertEquals(1, hab.getId());
    assertEquals("Sala", hab.getNombre());
    assertFalse(hab.estaVisitada());
    assertFalse(hab.esExterior());
}

@Test
public void testAgregarPuerta() {
    Habitacion hab = new Habitacion(1, "Sala", 10, 10);
    Puerta puerta = new Puerta(2, "Puerta");
    hab.agregarPuerta(puerta);
    
    Puerta recuperada = hab.obtenerPuerta(2);
    assertNotNull(recuperada);
    assertEquals(2, recuperada.getHabitacionDestino());
}

@Test
public void testMarcarVisitada() {
    Habitacion hab = new Habitacion(1, "Sala", 10, 10);
    assertFalse(hab.estaVisitada());
    hab.marcarVisitada();
    assertTrue(hab.estaVisitada());
}

@Test
public void testObtenerEnemigos() {
    Habitacion hab = new Habitacion(1, "Sala", 10, 10);
    MatrizHabitacion matriz = hab.getMatriz();
    
    Celda celda = matriz.getCelda(0, 0);
    Enemigo enemigo = new Enemigo(...);
    celda.setEntidad(enemigo);
    
    LSE<Enemigo> enemigos = hab.obtenerEnemigos();
    assertTrue(enemigos.contains(enemigo));
}

@Test
public void testTurnsLimit() {
    Habitacion hab = new Habitacion(1, "Sala", 10, 10);
    hab.setTurnsLimit(50);
    assertEquals(50, hab.getTurnsLimit());
}
```

### Test: GrafoHabitaciones

```java
@Test
public void testCrearGrafo() {
    GrafoHabitaciones grafo = new GrafoHabitaciones();
    assertNotNull(grafo);
}

@Test
public void testAgregarHabitacion() {
    GrafoHabitaciones grafo = new GrafoHabitaciones();
    Habitacion hab = new Habitacion(1, "Sala", 10, 10);
    grafo.agregarHabitacion(hab);
    
    Habitacion recuperada = grafo.obtenerHabitacion(1);
    assertNotNull(recuperada);
    assertEquals(1, recuperada.getId());
}

@Test
public void testConectarHabitaciones() {
    GrafoHabitaciones grafo = new GrafoHabitaciones();
    Habitacion h1 = new Habitacion(1, "Sala 1", 10, 10);
    Habitacion h2 = new Habitacion(2, "Sala 2", 10, 10);
    
    grafo.agregarHabitacion(h1);
    grafo.agregarHabitacion(h2);
    grafo.conectarHabitaciones(h1, h2, "Puerta");
    
    // Verificar que h1 tiene puerta a h2
    Puerta puerta = h1.obtenerPuerta(2);
    assertNotNull(puerta);
}

@Test
public void testCaminoMinimo() {
    GrafoHabitaciones grafo = new GrafoHabitaciones();
    Habitacion h1 = new Habitacion(1, "Sala 1", 10, 10);
    Habitacion h2 = new Habitacion(2, "Sala 2", 10, 10);
    Habitacion h3 = new Habitacion(3, "Sala 3", 10, 10);
    
    grafo.agregarHabitacion(h1);
    grafo.agregarHabitacion(h2);
    grafo.agregarHabitacion(h3);
    
    grafo.conectarHabitaciones(h1, h2, "P1");
    grafo.conectarHabitaciones(h2, h3, "P2");
    
    LSE<Habitacion> camino = grafo.caminoMinimoHabitaciones(1, 3);
    assertNotNull(camino);
    assertEquals(3, camino.size()); // [h1, h2, h3]
}

@Test
public void testCambiarHabitacion() {
    GrafoHabitaciones grafo = new GrafoHabitaciones();
    Habitacion h1 = new Habitacion(1, "Sala 1", 10, 10);
    grafo.agregarHabitacion(h1);
    
    grafo.cambiarHabitacion(1);
    assertEquals(h1, grafo.obtenerHabitacionActual());
}

@Test
public void testObtenerHabitacionesAdyacentes() {
    GrafoHabitaciones grafo = new GrafoHabitaciones();
    Habitacion h1 = new Habitacion(1, "Sala 1", 10, 10);
    Habitacion h2 = new Habitacion(2, "Sala 2", 10, 10);
    Habitacion h3 = new Habitacion(3, "Sala 3", 10, 10);
    
    grafo.agregarHabitacion(h1);
    grafo.agregarHabitacion(h2);
    grafo.agregarHabitacion(h3);
    
    grafo.conectarHabitaciones(h1, h2, "P1");
    grafo.conectarHabitaciones(h1, h3, "P2");
    
    LSE<Habitacion> adyacentes = grafo.obtenerHabitacionesAdyacentes(1);
    assertEquals(2, adyacentes.size());
}
```

---

## 3. Escenarios de Prueba Integrados

### Escenario 1: Exploración Básica

```java
@Test
public void testExploracionBasica() {
    // Crear mapa
    GrafoHabitaciones mapa = new GrafoHabitaciones();
    
    // Crear habitaciones
    Habitacion entrada = new Habitacion(1, "Entrada", 10, 10);
    Habitacion pasillo = new Habitacion(2, "Pasillo", 10, 20);
    Habitacion salida = new Habitacion(3, "Salida", 10, 10);
    
    // Agregar al mapa
    mapa.agregarHabitacion(entrada);
    mapa.agregarHabitacion(pasillo);
    mapa.agregarHabitacion(salida);
    
    // Conectar
    mapa.conectarHabitaciones(entrada, pasillo, "Puerta Este");
    mapa.conectarHabitaciones(pasillo, salida, "Puerta Norte");
    
    // Marcar salida como exterior
    salida.setExterior(true);
    
    // Jugador empieza en entrada
    mapa.cambiarHabitacion(1);
    assertEquals(entrada, mapa.obtenerHabitacionActual());
    
    // Calcular camino a salida
    LSE<Habitacion> camino = mapa.caminoMinimoHabitaciones(1, 3);
    assertEquals(3, camino.size());
}
```

### Escenario 2: Combate en Habitación

```java
@Test
public void testCombateEnHabitacion() {
    // Crear habitación
    Habitacion sala = new Habitacion(1, "Sala de Combate", 10, 10);
    MatrizHabitacion matriz = sala.getMatriz();
    
    // Colocar enemigos
    Enemigo e1 = new Enemigo(...);
    Enemigo e2 = new Enemigo(...);
    
    Celda celda1 = matriz.getCelda(2, 2);
    Celda celda2 = matriz.getCelda(8, 8);
    
    celda1.setEntidad(e1);
    celda2.setEntidad(e2);
    
    // Obtener enemigos
    LSE<Enemigo> enemigos = sala.obtenerEnemigos();
    assertEquals(2, enemigos.size());
    
    // Obtener celdas alcanzables desde posición del jugador
    LSE<Celda> alcanzables = matriz.obtenerCeldasAlcanzables(5, 5, 3);
    assertTrue(alcanzables.size() > 0);
}
```

### Escenario 3: Recolección de Objetos

```java
@Test
public void testRecoleccionObjetos() {
    // Crear habitación
    Habitacion sala = new Habitacion(1, "Sala de Tesoros", 10, 10);
    MatrizHabitacion matriz = sala.getMatriz();
    
    // Colocar objetos
    Objeto pocion = new Pocion("Poción", "Recupera 50 HP");
    Objeto arma = new Arma("Espada", "Ataque +10");
    
    Celda celda1 = matriz.getCelda(3, 3);
    Celda celda2 = matriz.getCelda(7, 7);
    
    celda1.setObjeto(pocion);
    celda2.setObjeto(arma);
    
    // Obtener objetos
    LSE<Objeto> objetos = sala.obtenerObjetos();
    assertEquals(2, objetos.size());
    
    // Recoger objeto
    celda1.setObjeto(null);
    objetos = sala.obtenerObjetos();
    assertEquals(1, objetos.size());
}
```

---

## 4. Checklist de Pruebas

### Celda
- [ ] Crear celda vacía
- [ ] Crear celda con tipo
- [ ] Asignar/obtener entidad
- [ ] Asignar/obtener objeto
- [ ] Métodos booleanos (estaVacia, tienePuerta, tieneTrampa)
- [ ] Limpiar celda

### MatrizHabitacion
- [ ] Crear matriz de tamaño N×M
- [ ] Obtener/asignar celda en posición válida
- [ ] Obtener/asignar celda en posición inválida (excepción)
- [ ] Obtener celdas alcanzables (BFS)
- [ ] Filtrar celdas con enemigos/objetos/puertas
- [ ] Iterar filas
- [ ] Limpiar matriz

### Puerta
- [ ] Crear puerta
- [ ] Obtener habitación destino
- [ ] Obtener etiqueta
- [ ] Abrir/cerrar puerta
- [ ] Verificar estado

### Habitacion
- [ ] Crear habitación
- [ ] Agregar/obtener puertas
- [ ] Marcar como visitada
- [ ] Obtener enemigos/objetos de la matriz
- [ ] Establecer límite de turnos
- [ ] Marcar como exterior
- [ ] Limpiar habitación

### GrafoHabitaciones
- [ ] Crear grafo
- [ ] Agregar habitaciones
- [ ] Conectar habitaciones
- [ ] Obtener habitación por ID
- [ ] Cambiar habitación actual
- [ ] Camino mínimo entre habitaciones
- [ ] Dijkstra entre habitaciones
- [ ] Obtener habitaciones adyacentes
- [ ] Cargar/guardar desde JSON

---

**Documento**: Ejemplos de Uso y Casos de Prueba  
**Versión**: 1.0  
**Fecha**: 2026-05-07

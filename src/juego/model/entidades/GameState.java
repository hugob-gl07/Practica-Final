package juego.model.entidades;

import com.google.gson.Gson;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;
import juego.model.habitacion.Celda;
import juego.model.habitacion.TipoCelda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class GameState {
    protected JugadorDTO jugador;
    protected EnemigoDTO[] enemigos;
    protected String nombreJugador;
    protected int vidaJugador, ataqueJugador, defesaJugador, manaJugador;
    protected int nivelJugador, experienciaJugador;
    protected int filaJugador, columnaJugador;
    protected int habitacionActual;
    protected int turnoRestantes;
    protected String estado;
    protected Jugador jugadorReconstruido;
    protected ListaSimplementeEnlazada<Enemigo> enemigoReconstruido;

    public static GameState cargarPartida(String ruta) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ruta));
            Gson gson = new Gson();
            GameState estado= gson.fromJson(reader,GameState.class);
            return estado;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado",e);
        } catch (IOException e) {
        throw new RuntimeException("Error al cargar partida", e);}
    }

    public static Jugador reconstruirJugador(GameState estado) {
        Celda posicion= new Celda(estado.jugador.fila,estado.jugador.columna, TipoCelda.JUGADOR);
        Jugador jugador= new Jugador(estado.jugador.nombre, posicion, estado.habitacionActual);
        jugador.setVidaMaxima(estado.jugador.vida);
        jugador.setNombre(estado.jugador.nombre);
        jugador.setAtaque(estado.jugador.ataque);
        jugador.setDefensa(estado.jugador.defensa);
        jugador.setMana(estado.jugador.mana);
        jugador.setNivel(estado.jugador.nivel);
        jugador.setExperiencia(estado.jugador.experiencia);
        for(int i = 0; i < estado.jugador.inventario.length; i++) {
            ObjetoDTO dto= estado.jugador.inventario[i];
            Objeto obj= new Objeto(dto.id,dto.nombre,TipoObjeto.valueOf(dto.tipo),Rareza.valueOf(dto.rareza));
            jugador.getInventario().addObjeto(obj);
        }

        return jugador;
    }
    public static ListaSimplementeEnlazada<Enemigo> reconstruirEnemigo(GameState estado) {
        ListaSimplementeEnlazada<Enemigo> enemigos= new ListaSimplementeEnlazada<>();
        for(int i=0; i < estado.enemigos.length;i++){
            EnemigoDTO dto=estado.enemigos[i];
            Enemigo e= EnemigoFactory.crearEnemigo(TipoEnemigo.valueOf(dto.tipo),dto.id,dto.fila,dto.columna);
            e.setVidaActual(dto.vida);
            enemigos.add(e);
        }
        return enemigos;
    }

    public static GameState reconstruirEstado(String ruta) {
        GameState estado= cargarPartida(ruta);
        estado.jugadorReconstruido= reconstruirJugador(estado);
        estado.enemigoReconstruido= reconstruirEnemigo(estado);
        return estado;
    }

    public static void guardarPartida(
            String ruta,
            Jugador jugador,
            ListaSimplementeEnlazada<Enemigo> enemigos,
            int habitacionActual,
            int turnosRestantes,
            String estadoJuego
    ) {
        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.nombre = jugador.getNombre();
        jugadorDTO.vida = jugador.getVidaActual();
        jugadorDTO.ataque = jugador.getAtaque();
        jugadorDTO.defensa = jugador.getDefensa();
        jugadorDTO.mana = jugador.getMana();
        jugadorDTO.nivel = jugador.getNivel();
        jugadorDTO.experiencia = jugador.getExperiencia();
        jugadorDTO.habitacionActual = habitacionActual;
        jugadorDTO.fila = jugador.getPosicion().getX();
        jugadorDTO.columna = jugador.getPosicion().getY();

        int tamañoInventario = jugador.getInventario().getTamaño();
        jugadorDTO.inventario = new ObjetoDTO[tamañoInventario];
        for (int i = 0; i < tamañoInventario; i++) {
            Objeto obj = jugador.getInventario().getObjeto(i + 1);
            if (obj != null) {
                ObjetoDTO objDTO = new ObjetoDTO();
                objDTO.id = obj.getId();
                objDTO.nombre = obj.getNombre();
                objDTO.tipo = obj.getTipo().name();
                objDTO.rareza = obj.getRareza().name();
                jugadorDTO.inventario[i] = objDTO;
            }
        }

        int tamañoEnemigos = enemigos.getSize();
        EnemigoDTO[] enemigosDTO = new EnemigoDTO[tamañoEnemigos];
        for (int i = 0; i < tamañoEnemigos; i++) {
            Enemigo e = enemigos.getAt(i);
            EnemigoDTO eDTO = new EnemigoDTO();
            eDTO.id = e.getId();
            eDTO.tipo = e.getTipo().name();
            eDTO.vida = e.getVidaActual();
            eDTO.fila = e.getPosicion().getX();
            eDTO.columna = e.getPosicion().getY();
            enemigosDTO[i] = eDTO;
        }

        GameState estado = new GameState();
        estado.jugador = jugadorDTO;
        estado.enemigos = enemigosDTO;
        estado.turnoRestantes = turnosRestantes;
        estado.estado = estadoJuego;

        Gson gson = new Gson();
        String json = gson.toJson(estado);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la partida", e);
        }
    }
}

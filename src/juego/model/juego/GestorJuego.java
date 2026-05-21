package juego.model.juego;
import juego.model.entidades.Jugador;
import juego.model.entidades.Enemigo;
import juego.model.habitacion.Habitacion;
import juego.model.habitacion.GrafoHabitacion;
import juego.model.habitacion.Celda;
import juego.model.habitacion.TipoCelda;
import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

public class GestorJuego {
    private Jugador jugador;
    private Habitacion habitacion;
    private ListaSimplementeEnlazada<Enemigo> enemigo;
    private GrafoHabitacion grafoHabitacion;
    private int turnoActual;
    private int turnosMaximos;
    private EstadoJuego estado;

    public GestorJuego(Jugador jugador, Habitacion habitacionactual, ListaSimplementeEnlazada<Enemigo> enemigos, GrafoHabitacion grafoHabitacion, int turnosMaximos ) {
        this.jugador = jugador;
        this.habitacion = habitacionactual;
        this.enemigo = enemigos;
        this.grafoHabitacion = grafoHabitacion;
        this.turnoActual = 1;
        this.turnosMaximos = turnosMaximos;
        this.estado = EstadoJuego.JUGANDO;
    }
        public void procesarTurnoJugador(Accion accion, int parametros){
            if(estado!=EstadoJuego.JUGANDO){
                return;
        }

    }
}

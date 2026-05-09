package juego.model.entidades;

import juego.model.EstructurasUtilizadas.ListaCircular.ListaCircular;
import juego.model.habitación.Celda;

public class Jugador {
    private int id;
    private String nombre;
    private int nivel;
    private int experiencia;
    private int vidaActual;
    private int vidaMaxima;
    private int ataque;
    private int defensa;
    private int mana;
    private Celda posicion;
    private Inventario inventario;
    private ListaCircular <String> historialAcciones;

    public Jugador(String nombre, Celda posicion){
        this.id=1;
        this.nombre=nombre;
        this.nivel=1;
        this.experiencia=0;
        this.vidaActual=100;
        this.vidaMaxima=100;
        this.ataque=10;
        this.defensa=5;
        this.mana=50;
        this.posicion=posicion;
        this.inventario=new Inventario(10);
        this.historialAcciones=new ListaCircular<>();
    }

    public void addExperiencia(int exp){
        experiencia+=exp;
        if(experiencia>=100*nivel){
            subirNivel();
        }
    }

    public void subirNivel(){
        this.nivel++;
        experiencia=0;
        vidaMaxima+=20;
        vidaActual=vidaMaxima;
        ataque+=5;
        defensa+=3;
        mana+=10;
        historialAcciones.insertar("Subistes al nivel"+ nivel);
    }

    public void recibirDaño(int daño){
        int dañoreal= daño-defensa;
        if(dañoreal<0){
            dañoreal= 0;
        }
        vidaActual-=dañoreal;
        if(vidaActual<0){
            vidaActual=0;
        }
    }

    public int atacar(Enemigo enemigo){
        enemigo.recibirDaño(this.ataque);
        historialAcciones.insertar("Atacaste a " + enemigo.getNombre());
        return this.ataque;
    }

    public void mover(Celda nuevaCelda){
        if(nuevaCelda.esTransitable()){
            posicion=nuevaCelda;
        }
        historialAcciones.insertar("Moviste a "+ nuevaCelda.toString());
    }

    public void usarObjeto(int idObjeto){
        Objeto objeto= inventario.getObjeto(idObjeto);
        if(objeto==null){
            return;
        }
        switch(objeto.getTipo()){
            case POCIMA_VIDA -> vidaActual+=objeto.getValorEstadisticas("vida");
            case POCIMA_MANA-> mana+=objeto.getValorEstadisticas("mana");
        }
        inventario.removeObjeto(idObjeto);
        historialAcciones.insertar("Usaste "+ objeto.getNombre());
    }

    public Inventario getInventario() {
        return inventario;
    }
    public boolean estaSalud(){
        return vidaActual>0;
    }

    public String getHistorialAcciones(){
        return historialAcciones.toString();
    }




}

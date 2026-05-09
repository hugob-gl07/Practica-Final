package juego.model.entidades;
import juego.model.habitación.Celda;

public class Enemigo {
    protected int id;
    protected String nombre;
    protected TipoEnemigo tipo;
    protected int vidaActual;
    protected int vidamaxima;
    protected int ataque;
    protected int defensa;
    protected Celda posicion;
    protected Objeto[] loot;

    public Enemigo(int id, String nombre, TipoEnemigo tipo, int vidamaxima, int ataque, int defensa) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.vidaActual = vidamaxima;
        this.ataque = ataque;
        this.defensa = defensa;
        this.loot = new Objeto[3];
    }

    public void recibirDaño(int daño){
        if(vidaActual <0){
            vidaActual= 0;
        }
        vidaActual-=daño;
    }
    public int atacar(){
        return ataque;
    }
    public int obtenerDefensa(){
        return defensa;
    }
    public boolean estaMuerto(){
        return vidaActual ==0;
    }
    public Objeto getObjeto(int indice){
        if(indice>=0 && indice<loot.length){
            return loot[indice];
        }
        return null;
    }
    public Celda getPosicion(){
        return posicion;
    }
    public void setPosicion(Celda posicion){
        this.posicion = posicion;
    }

    public String getNombre(){
        return nombre;
    }

    public int getId() {
        return id;
    }

    public TipoEnemigo getTipo() {
        return tipo;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = vidaActual;
    }

    public int getVidamaxima() {
        return vidamaxima;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public Objeto[] getLoot() {
        return loot;
    }

    public void setLoot(Objeto[] loot) {
        this.loot = loot;
    }

    public int getVidaMaxima() {
        return vidamaxima;
    }


}

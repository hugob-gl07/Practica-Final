package juego.model.entidades;

import juego.model.EstructurasUtilizadas.LSE.ListaSimplementeEnlazada;

public class Inventario {
    private int capacidadesmaxima;
    private ListaSimplementeEnlazada<Objeto> objetos;
    private float pesoTotal;

    public Inventario(int capacidad) {
        this.capacidadesmaxima = capacidad;
        this.objetos = new ListaSimplementeEnlazada();
        this.pesoTotal = 0;
    }

    public boolean addObjeto(Objeto objeto) {
        if(estaLleno()) {
            return false;
        }
        if(objeto == null) {
            return false;
        }
        objetos.add(objeto);
        return true;
    }

    public Objeto removeObjeto(int id) {
        Objeto objeto= null;
        for(int i=0;i<objetos.getSize();i++){
            if(objetos.getAt(i).getId() == id) {
                objeto = objetos.getAt(i);
                objetos.removeAt(i);
                pesoTotal -= objeto.getPeso();
            }
        }
        return objeto;
    }
    public Objeto getObjeto(int id) {
        for(int i=0;i<objetos.getSize();i++) {
            if(objetos.getAt(i).getId()==id) {
                return objetos.getAt(i);
            }
        }
        return null;
    }

    public int getTamaño() {
        return objetos.getSize();
    }
    public boolean estaLleno(){
        return objetos.getSize()>=capacidadesmaxima;
    }

    public String listaObjetos(){
        String lista = "";
        for(int i=0;i<objetos.getSize();i++){
            lista += objetos.getAt(i).toString();
            lista += "\n";
        }
        return lista;
    }

    public void vaciar(){
        objetos.clear();
        pesoTotal = 0;
    }
}

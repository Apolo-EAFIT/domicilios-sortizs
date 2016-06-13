import java.util.ArrayList;

public class Nodo{

    int idNodo;
    ArrayList<Integer> nodosHijos = new ArrayList<Integer>();
    ArrayList<Integer> costos = new ArrayList<Integer>();
    ArrayList<Integer> nodosPadres = new ArrayList<Integer>();
    int costoDesdeInicial = -1;
    boolean visita = false;

    public Nodo(int idNodo){
    this.idNodo = idNodo; 
    }

    public void AgregarNodoHijo(int nodo, int costo){
        nodosHijos.add(nodo);
        costos.add(costo);
    }

    public int ObtenerNodo(int posicion){
        return nodosHijos.get(posicion);
    }

    public int ObtenerCosto(int posicion){
        return costos.get(posicion);
    }

    public int CantidadDeNodosAdyacentes(){
        return nodosHijos.size();
    }

    public void AsignarCosto(int costo){
        costoDesdeInicial = costo;
    }

    public int ObtenerCostoDesdeInicial(){
        return costoDesdeInicial;
    }

    public void AgregarNodoPadre( int nodo){
        nodosPadres.add(nodo);
    }

    public int ObtenerNodoPadre(int pos){
        return nodosPadres.get(pos);
    }

    public int CantidadDeNodosPadres(){
        return nodosPadres.size();
    }
    
    public int ObtenerId(){
        return idNodo;
    }
    
    public void Visitado(){
        visita = true;
    }
    
    public boolean aSidoVisitado(){
        return visita;
    }

}
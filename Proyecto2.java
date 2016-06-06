    import java.util.ArrayList;
    import java.util.Vector;
    import java.io.BufferedReader;
    import java.io.FileNotFoundException;
    import java.io.FileReader;
    import java.io.IOException;

public class Proyecto2{

    static String cantidadEsquinas;
    static int cantidadRepartidores;
    static Vector<Nodo> listaDeAdyacencia = new Vector();
    static Vector<Nodo> lista2 = new Vector();
    static ArrayList<Integer> camino = new ArrayList<Integer>();
    static int inicial = 2;
    
    public static void main(String [] args) throws IOException {
        RecibirMapa("/home/juanpablo/Proyecto EDA2/medellin_arcos.txt");
        EncontrarCamino(listaDeAdyacencia,10,101);
        SeguirCamino(lista2,101,10);
    }

    public static void RecibirMapa(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        String separador = " ";
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        cantidadEsquinas = b.readLine();
        listaDeAdyacencia.setSize(Integer.parseInt(cantidadEsquinas));
        lista2.setSize(Integer.parseInt(cantidadEsquinas));
        while((cadena = b.readLine())!=null) {
            String[] infNodo = cadena.split(separador);
            int idNodo = Integer.parseInt(infNodo[0]);
            int idNodoHijo = Integer.parseInt(infNodo[1]);
            int costo = Integer.parseInt(infNodo[2]);


            if(listaDeAdyacencia.get(idNodo)!=null){
                Nodo nodo = listaDeAdyacencia.get(idNodo);
                nodo.AgregarNodoHijo(idNodoHijo,costo);
                listaDeAdyacencia.set(idNodo, nodo);
            }else{
                Nodo nodo = new Nodo(idNodo);
                nodo.AgregarNodoHijo(idNodoHijo, costo);
                listaDeAdyacencia.set(idNodo, nodo);
            }

            if(listaDeAdyacencia.get(idNodoHijo)!=null){
                Nodo nodo = listaDeAdyacencia.get(idNodoHijo);
                nodo.AgregarNodoPadre(idNodo);
                listaDeAdyacencia.set(idNodoHijo, nodo);
            }else{
                Nodo nodo = new Nodo(idNodoHijo);
                nodo.AgregarNodoPadre(idNodo);
                listaDeAdyacencia.set(idNodoHijo, nodo);
            }
        }
        lista2 = listaDeAdyacencia;
        b.close();
    }

    public static void EncontrarCamino(Vector<Nodo> lista, int idNodoActual, int idNodoFinal){
        Nodo nodoActual = lista.get(idNodoActual);
        for(int i = 0; i < nodoActual.CantidadDeNodosAdyacentes(); i++){
            Nodo nodoSiguiente = lista.get(nodoActual.ObtenerNodo(i));
            if(nodoSiguiente.ObtenerId() == idNodoFinal){
                lista2 = lista;
            }
            if((((nodoActual.ObtenerCostoDesdeInicial() + nodoActual.ObtenerCosto(i)) < (nodoSiguiente.ObtenerCostoDesdeInicial())) || (nodoSiguiente.ObtenerCostoDesdeInicial() == -1)) && nodoSiguiente.ObtenerId() != inicial) {
                nodoSiguiente.AsignarCosto(nodoActual.ObtenerCostoDesdeInicial() + nodoActual.ObtenerCosto(i));
                lista.set(nodoSiguiente.ObtenerId(),nodoSiguiente);
                EncontrarCamino(lista,nodoActual.ObtenerNodo(i), idNodoFinal);    
            }
        }
        
    }

    public static void SeguirCamino(Vector<Nodo> lista, int idNodoActual, int idNodoFinal){
        camino.add(idNodoActual);
        int[] nodoConCaminoMenor = new int[2];
        nodoConCaminoMenor[0] = -1;
        nodoConCaminoMenor[1] = -1;
        Nodo nodoActual;
        boolean llegue = false;
        while(llegue==false){
            if(nodoConCaminoMenor[0] == -1){
               nodoActual = lista.get(idNodoActual);
            }else{
               nodoActual = lista.get(nodoConCaminoMenor[0]);
            }
            for(int i = 0; i < nodoActual.CantidadDeNodosPadres(); i++){
                Nodo nodoPadre = lista.get(nodoActual.ObtenerNodoPadre(i));
                if((nodoPadre.ObtenerId() == idNodoFinal) && nodoPadre.aSidoVisitado() == false){
                    for(int j = 0; j < nodoPadre.CantidadDeNodosAdyacentes(); j++){
                        if(nodoPadre.ObtenerNodo(j) == nodoActual.ObtenerId()){
                            if(nodoConCaminoMenor[1] == -1 || (nodoPadre.ObtenerCosto(j) + nodoPadre.ObtenerCostoDesdeInicial()) < nodoConCaminoMenor[1]){
                                nodoConCaminoMenor[0] = nodoPadre.ObtenerId();
                                nodoConCaminoMenor[1] = nodoPadre.ObtenerCostoDesdeInicial()+nodoPadre.ObtenerCosto(j);
                            }
                        }
                    }
                }
            
                else if((nodoConCaminoMenor[1] == -1 || (nodoPadre.ObtenerCostoDesdeInicial() < nodoConCaminoMenor[1])) && nodoPadre.aSidoVisitado() == false){
                    nodoConCaminoMenor[0] = nodoPadre.ObtenerId();
                    nodoConCaminoMenor[1] = nodoPadre.ObtenerCostoDesdeInicial();
                }
            }
            if(nodoConCaminoMenor[0] == idNodoFinal){
                llegue = true;
            }
            Nodo nodoPadre = lista.get(nodoConCaminoMenor[0]);
            nodoPadre.Visitado();
            nodoActual.Visitado();
            lista.set(nodoPadre.ObtenerId(),nodoPadre);
            lista.set(nodoActual.ObtenerId(),nodoActual);
            camino.add(nodoConCaminoMenor[0]);
            nodoConCaminoMenor[1]=-1;
        }
        for(int i = camino.size()-1; i >= 0; i--){
            System.out.println(camino.get(i));
        }
        camino.removeAll(camino);
    }
}
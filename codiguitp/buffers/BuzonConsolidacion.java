package codiguitp.buffers;

import codiguitp.eventos.Evento;
import java.util.LinkedList;
import java.util.Queue;


public class BuzonConsolidacion {

    private final int id;
    private final int capacidad;
    private final Queue<Evento> cola = new LinkedList<>();


    public BuzonConsolidacion(int id, int capacidad) {

        this.id = id;
        this.capacidad = capacidad;
    }



    public synchronized void depositar(Evento e) throws InterruptedException {

        while (cola.size() == capacidad) {
            wait();
        }

        cola.add(e);
        notifyAll();

    }



    public synchronized Evento retirar() throws InterruptedException {

        while (cola.isEmpty()) {
            wait();
        }


        Evento e = cola.poll();
        notifyAll();
        return e;

    }

    public int getId() { return id; }

    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }


}

package codiguitp.buffers;

import codiguitp.eventos.Evento;
import java.util.LinkedList;
import java.util.Queue;



public class BuzonEntrada {

    private final Queue<Evento> cola = new LinkedList<>();


    public synchronized void depositar(Evento e) throws InterruptedException {
        cola.add(e);
        notifyAll();
    }



    public synchronized Evento retirar() throws InterruptedException {

        while (cola.isEmpty()) {

            wait();
        }

        return cola.poll();

    }



    public synchronized boolean estaVacio() {
        return cola.isEmpty();
    }
}

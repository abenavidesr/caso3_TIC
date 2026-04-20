package codiguitp.hilos;

import codiguitp.buffers.BuzonEntrada;
import codiguitp.eventos.Evento;


import java.util.Random;



public class Sensor extends Thread {


    private final int id;
    private final int numEventos;
    private final BuzonEntrada buzonEntrada;
    private final Random random = new Random();
    private final int ns;



    public Sensor(int id, int baseEventos, int ns, BuzonEntrada buzonEntrada) {
        this.id = id;
        this.numEventos = baseEventos * id;
        this.ns = ns;
        this.buzonEntrada = buzonEntrada;


        setName("Sensor-" + id);


    }




    @Override
    public void run() {

        System.out.println("[Sensor-" + id + "] Iniciado. Generará " + numEventos + " eventos.");


        try {
            for (int i = 1; i <= numEventos; i++) {

                String eventoId = "S" + id + "-E" + i;

                int servidor = random.nextInt(ns) + 1;
                Evento e = new Evento(eventoId, servidor);
                buzonEntrada.depositar(e);


                System.out.println("[Sensor-" + id + "] Depositó " + e);
                Thread.sleep(random.nextInt(50) + 10);

            }


        }

        catch (InterruptedException ex) {
            System.err.println("[Sensor-" + id + "] Interrumpido.");
        }


        System.out.println("[Sensor-" + id + "] TERMINÓ. Generó " + numEventos + " eventos.");


    }

    public int getNumEventos() { return numEventos; }



}

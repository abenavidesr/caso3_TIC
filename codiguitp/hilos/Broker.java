package codiguitp.hilos;


import codiguitp.buffers.BuzonAlertas;
import codiguitp.buffers.BuzonClasificacion;
import codiguitp.buffers.BuzonEntrada;
import codiguitp.eventos.Evento;
import codiguitp.eventos.TipoEvento;

import java.util.Random;




public class Broker extends Thread {


    private final int totalEventos;
    private final BuzonEntrada buzonEntrada;
    private final BuzonAlertas buzonAlertas;
    private final BuzonClasificacion buzonClasificacion;
    private final Random random = new Random();



    public Broker(int totalEventos, BuzonEntrada buzonEntrada, BuzonAlertas buzonAlertas, BuzonClasificacion buzonClasificacion) {


        this.totalEventos = totalEventos;
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;


        setName("Broker");


    }




    @Override
    public void run() {

        System.out.println("[Broker] Iniciado. Esperando " + totalEventos + " eventos.");

        int procesados = 0;


        try {


            while (procesados < totalEventos) {

                Evento e = buzonEntrada.retirar();


                int r = random.nextInt(201);


                if (r % 8 == 0) {

                    buzonAlertas.depositar(e);
                    System.out.println("[Broker] ANOMALO → " + e.getId() + " (r=" + r + ")");
                }

                else {

                    buzonClasificacion.depositar(e);
                    System.out.println("[Broker] Normal  → " + e.getId() + " (r=" + r + ")");
                }


                procesados++;

            }


            System.out.println("[Broker] Procesó todos los eventos. Enviando FIN al Administrador.");
            buzonAlertas.depositar(new Evento(TipoEvento.FIN));

        }

        catch (InterruptedException ex) {
            System.err.println("[Broker] Interrumpido.");
        }


        System.out.println("[Broker] TERMINÓ.");

    }


}

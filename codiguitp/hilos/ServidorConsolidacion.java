package codiguitp.hilos;



import codiguitp.buffers.BuzonConsolidacion;
import codiguitp.eventos.Evento;

import java.util.Random;



public class ServidorConsolidacion extends Thread {

    private final int id;
    private final BuzonConsolidacion buzon;
    private final Random random = new Random();


    public ServidorConsolidacion(int id, BuzonConsolidacion buzon) {
        this.id = id;
        this.buzon = buzon;
        setName("Servidor-" + id);

    }




    @Override
    public void run() {

        System.out.println("[Servidor-" + id + "] Iniciado. Esperando eventos.");


        try {

            while (true) {
                Evento e = buzon.retirar();

                if (e.esFin()) {

                    System.out.println("[Servidor-" + id + "] Recibió FIN.");

                    break;

                }



                int tiempo = random.nextInt(901) + 100;

                System.out.println("[Servidor-" + id + "] Procesando " + e.getId()  + " (" + tiempo + " ms)...");
                Thread.sleep(tiempo);
                System.out.println("[Servidor-" + id + "] Completó " + e.getId());

            }

        }

        catch (InterruptedException ex) {
            System.err.println("[Servidor-" + id + "] Interrumpido.");
        }


        System.out.println("[Servidor-" + id + "] TERMINÓ.");

    }


}

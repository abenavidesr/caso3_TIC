package codiguitp.hilos;

import codiguitp.buffers.BuzonClasificacion;
import codiguitp.buffers.BuzonConsolidacion;
import codiguitp.eventos.Evento;
import codiguitp.eventos.TipoEvento;



public class Clasificador extends Thread {

    private final int id;
    private final BuzonClasificacion buzonClasificacion;
    private final BuzonConsolidacion[] buzonesConsolidacion;



    private static int clasificadoresActivos;
    private static final Object lockActivos = new Object();


    public Clasificador(int id, int nc, BuzonClasificacion buzonClasificacion, BuzonConsolidacion[] buzonesConsolidacion) {



        this.id = id;
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = buzonesConsolidacion;
        setName("Clasificador-" + id);


        synchronized (lockActivos) {

            clasificadoresActivos = nc;

        }


    }




    @Override
    public void run() {

        System.out.println("[Clasificador-" + id + "] Iniciado.");


        try {


            while (true) {
                Evento e = buzonClasificacion.retirar();

                if (e.esFin()) {

                    System.out.println("[Clasificador-" + id + "] Recibió FIN.");
                    break;
                }



                int idxServidor = e.getServidor() - 1;
                buzonesConsolidacion[idxServidor].depositar(e);

                System.out.println("[Clasificador-" + id + "] Enrutó " + e.getId()  + " → Servidor-" + e.getServidor());
            }




            synchronized (lockActivos) {

                clasificadoresActivos--;

                if (clasificadoresActivos == 0) {

                    System.out.println("[Clasificador-" + id + "] Es el último. Enviando FIN a servidores.");

                    for (BuzonConsolidacion buzon : buzonesConsolidacion) {

                        buzon.depositar(new Evento(TipoEvento.FIN));
                    }

                }


            }

        }


        catch (InterruptedException ex) {
            System.err.println("[Clasificador-" + id + "] Interrumpido.");
        }


        System.out.println("[Clasificador-" + id + "] TERMINÓ.");

    }


}

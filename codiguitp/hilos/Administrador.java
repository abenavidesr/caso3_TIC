package codiguitp.hilos;


import codiguitp.buffers.BuzonAlertas;
import codiguitp.buffers.BuzonClasificacion;
import codiguitp.eventos.Evento;
import codiguitp.eventos.TipoEvento;


import java.util.Random;



public class Administrador extends Thread {

    private final int nc;
    private final BuzonAlertas buzonAlertas;
    private final BuzonClasificacion buzonClasificacion;
    private final Random random = new Random();



    public Administrador(int nc, BuzonAlertas buzonAlertas, BuzonClasificacion buzonClasificacion) {

        this.nc = nc;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;

        setName("Administrador");
    }



    @Override
    public void run() {

        System.out.println("[Administrador] Iniciado.");


        try {
            while (true) {

                Evento e = buzonAlertas.retirar();

                if (e.esFin()) {
                    System.out.println("[Administrador] Recibió FIN. Enviando " + nc + " FIN a clasificadores.");


                    for (int i = 0; i < nc; i++) {

                        buzonClasificacion.depositar(new Evento(TipoEvento.FIN));
                    }

                    break;

                }



                int r = random.nextInt(21);


                if (r % 4 == 0) {

                    System.out.println("[Administrador] Inofensivo → " + e.getId() + " (r=" + r + ")");
                    buzonClasificacion.depositar(e);

                }
                else {

                    System.out.println("[Administrador] descartado → " + e.getId() + " (r=" + r + ")");
                }
            }

        }

        catch (InterruptedException ex) {

            System.err.println("[Administrador] Interrumpido.");
        }

        System.out.println("[Administrador] TERMINÓ.");


    }


}

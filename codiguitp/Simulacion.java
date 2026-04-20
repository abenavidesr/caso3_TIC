package codiguitp;

import codiguitp.buffers.*;
import codiguitp.hilos.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Simulacion {

    public static void main(String[] args) {

        try {

            Properties props = new Properties();
            InputStream input = Simulacion.class.getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                System.err.println("No esta el  config.properties en el classpath.");
                return;
            }
            props.load(input);

            int ni          = Integer.parseInt(props.getProperty("ni"));
            int baseEventos = Integer.parseInt(props.getProperty("base_eventos"));
            int nc          = Integer.parseInt(props.getProperty("nc"));
            int ns          = Integer.parseInt(props.getProperty("ns"));
            int tam1        = Integer.parseInt(props.getProperty("tam1"));
            int tam2        = Integer.parseInt(props.getProperty("tam2"));



            int totalEventos = 0;
            for (int i = 1; i <= ni; i++) totalEventos += baseEventos * i;

            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║      SIMULACIÓN                          ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("Sensores(ni): " + ni);
            System.out.println("Base eventos: " + baseEventos);
            System.out.println("Total eventos: " + totalEventos);
            System.out.println("Clasificadores(nc): " + nc);
            System.out.println("Servidores(ns): " + ns);
            System.out.println("Capacidad buzón clasificación (tam1): " + tam1);
            System.out.println("Capacidad buzón consolidación (tam2): " + tam2);
            System.out.println();



            BuzonEntrada       buzonEntrada       = new BuzonEntrada();
            BuzonAlertas       buzonAlertas       = new BuzonAlertas();
            BuzonClasificacion buzonClasificacion = new BuzonClasificacion(tam1);



            BuzonConsolidacion[] buzonesConsolidacion = new BuzonConsolidacion[ns];

            for (int i = 0; i < ns; i++) {

                buzonesConsolidacion[i] = new BuzonConsolidacion(i + 1, tam2);
            }


            List<Thread> hilos = new ArrayList<>();


            for (int i = 1; i <= ni; i++) {

                hilos.add(new Sensor(i, baseEventos, ns, buzonEntrada));

            }


            hilos.add(new Broker(totalEventos, buzonEntrada, buzonAlertas, buzonClasificacion));


            hilos.add(new Administrador(nc, buzonAlertas, buzonClasificacion));


            for (int i = 1; i <= nc; i++) {

                hilos.add(new Clasificador(i, nc, buzonClasificacion, buzonesConsolidacion));
            }


            for (int i = 0; i < ns; i++) {

                hilos.add(new ServidorConsolidacion(i + 1, buzonesConsolidacion[i]));
            }


            for (Thread t : hilos) t.start();


            for (Thread t : hilos) t.join();

            System.out.println();
            System.out.println("Fin. Todos los hilos finalizaron y buzones están vacíos.");


        }
        catch (Exception e) {
            e.printStackTrace();

        }


    }



}

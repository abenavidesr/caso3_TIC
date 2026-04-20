package codiguitp.eventos;


public class Evento {


    private final String id;
    private final TipoEvento tipo;
    private final int servidor;



    public Evento(String id, int servidor) {
        this.id = id;
        this.tipo = TipoEvento.NORMAL;
        this.servidor = servidor;

    }



    public Evento(TipoEvento tipo) {
        this.id = "FIN";
        this.tipo = tipo;
        this.servidor = -1;
    }


    public String getId()       { return id; }
    public TipoEvento getTipo() { return tipo; }
    public int getServidor()    { return servidor; }
    public boolean esFin()      { return tipo == TipoEvento.FIN; }



    @Override
    public String toString() {

        if (tipo == TipoEvento.FIN) return "[FIN]";

        return "Evento[" + id + ", srv=" + servidor + "]";

    }



}

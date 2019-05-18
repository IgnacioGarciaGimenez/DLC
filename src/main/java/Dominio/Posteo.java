package Dominio;

import java.util.ArrayList;
import java.util.Hashtable;

public class Posteo {

    private Hashtable<String, ArrayList<EntradaPosteo>> posteos;
    public static Posteo instance = null;

    private Posteo() {
        this.posteos = new Hashtable<>();
    }

    private Posteo(Hashtable<String, ArrayList<EntradaPosteo>> posteo) {
        this.posteos = posteo;
    }

    public static Posteo getInstance() {
        if (instance == null)
            instance = new Posteo();
        return instance;
    }

    public Hashtable<String, ArrayList<EntradaPosteo>> getPosteos() {
        return posteos;
    }

    public void setPosteos(Hashtable<String, ArrayList<EntradaPosteo>> posteos) {
        this.posteos = posteos;
    }

    public void indexarDocumento(Hashtable<String, Integer> parseo, String path, Hashtable<String, Termino> vocab) {
        parseo.forEach((k, v) -> {
            EntradaPosteo entrada = new EntradaPosteo(path, v);
            if (posteos.containsKey(k)) {
                ArrayList<EntradaPosteo> lista = posteos.get(k);
                lista.add(entrada);
            }
            else {
                ArrayList<EntradaPosteo> listaEntradas = new ArrayList<>();
                listaEntradas.add(entrada);
                posteos.put(k, listaEntradas);
            }

            if (vocab.containsKey(k)) {
                Termino term = vocab.get(k);
                term.setCantDocumentos(term.getCantDocumentos() + 1);
                if (term.getMaximaFrecuencia() < v)
                    term.setMaximaFrecuencia(v);
            }
            else {
                vocab.put(k, new Termino(1, v));
            }

        });
    }
}

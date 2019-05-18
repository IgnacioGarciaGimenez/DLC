package Dominio;

import Gestores.GestorDB;

import java.io.File;
import java.util.Hashtable;

public class Vocabulario {

    private Hashtable<String, Termino> vocabulario;
    private static Vocabulario instance = null;
    public String s = "";

    private Vocabulario() {
        this.vocabulario = new Hashtable<>();
    }

    public static Vocabulario getInstance() {
        if (instance == null)
            instance = new Vocabulario();
        return instance;
    }

    public Hashtable<String, Termino> getVocabulario() {
        return vocabulario;
    }

    public void agregarDocumento(File file) {

        LectorDocumento lector = new LectorDocumento(file);

        Hashtable<String, Integer> documentoParseado = lector.procesarArchivo();
        Posteo posteo = Posteo.getInstance();
        posteo.indexarDocumento(documentoParseado, file.getAbsolutePath());


        documentoParseado.forEach((k, v) -> {
            if (vocabulario.containsKey(k)) {
                Termino term = vocabulario.get(k);
                term.setCantDocumentos(term.getCantDocumentos() + 1);
                if (term.getMaximaFrecuencia() < v)
                    term.setMaximaFrecuencia(v);
            }
            else {
                vocabulario.put(k, new Termino(1, v));
            }

        });



    }



}

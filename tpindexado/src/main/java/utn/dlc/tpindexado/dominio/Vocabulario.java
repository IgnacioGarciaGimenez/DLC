package utn.dlc.tpindexado.dominio;


import javax.persistence.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Vocabulario {

    private HashMap<String, Termino> vocabulario;
        private static Vocabulario instance = null;
    public static int PROV_INDICE = 0;

    private Vocabulario() {
        this.vocabulario = new HashMap<>();

    }

    public static Vocabulario getInstance() {
        if (instance == null) {
            instance = new Vocabulario();
        }
        PROV_INDICE = instance.getVocabulario().size();
        System.out.println("Cantidad de palabras en el vocabulario: " + PROV_INDICE);
        return instance;
    }


    public HashMap<String, Termino> getVocabulario() {
        return vocabulario;
    }

    public Hashtable<String, Integer> agregarDocumento(File file) throws IOException {
        LectorDocumento lector = new LectorDocumento(file);
        Hashtable<String, Integer> documentoParseado = lector.procesarArchivo();


        documentoParseado.forEach((k , v) -> {
            if (vocabulario.containsKey(k)) {
                Termino term = vocabulario.get(k);
                term.setCantDocumentos(term.getCantDocumentos() + 1);
                if (term.getMaximaFrecuencia() < v)
                    term.setMaximaFrecuencia(v);
            }
            else {
                Termino term = new Termino(1, v, k);
                vocabulario.put(k, term);
            }
        });
        return documentoParseado;
    }

    public Termino get(String key) {
        return this.vocabulario.get(key);
    }

    public int size() {
        return this.vocabulario.size();
    }



}

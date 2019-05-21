package utn.dlc.tpindexado.dominio;

import VocabularioSerializacion.VocabularioIOException;
import VocabularioSerializacion.VocabularioReader;
import VocabularioSerializacion.VocabularioWriter;

import java.io.File;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Vocabulario implements Serializable {

    private ConcurrentHashMap<String, Termino> vocabulario;
    private static Vocabulario instance = null;
    private static int PROV_INDICE = 0;
    private static boolean cambio = false;

    private Vocabulario() {
        this.vocabulario = new ConcurrentHashMap<>();
    }

    public static Vocabulario getInstance() {
        if (instance == null) {
            System.out.println("Intenando levantar vocabulario");
            try {
                instance = new VocabularioReader().read();
            } catch (VocabularioIOException e) {
                System.out.println("No existe vocabulario, creando uno nuevo.");
                instance = new Vocabulario();
            }
        }
        PROV_INDICE = instance.getVocabulario().size();
        System.out.println("Cantidad de palabras en el vocabulario: " + PROV_INDICE);
        return instance;
    }

    private ConcurrentHashMap<String, Termino> getVocabulario() {
        return vocabulario;
    }

    public Hashtable<String, Integer> agregarDocumento(File file) {
        cambio = true;
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
                vocabulario.put(k, new Termino(1, v));
            }
        });
        return documentoParseado;
    }


    public void write() throws VocabularioIOException {
        if (cambio)
            new VocabularioWriter().write(this);
        cambio = true;
    }

    public Termino get(String key) {
        return this.vocabulario.get(key);
    }

    public int size() {
        return this.vocabulario.size();
    }

    public class Termino implements Serializable, Comparable{

        private int cantDocumentos;
        private int maximaFrecuencia;
        private int indice;

        private Termino(int cantDocumentos, int maximaFrecuencia) {
            this.cantDocumentos = cantDocumentos;
            this.maximaFrecuencia = maximaFrecuencia;
            this.indice = ++PROV_INDICE;
        }

        public int getCantDocumentos() {
            return cantDocumentos;
        }

        public void setCantDocumentos(int cantDocumentos) {
            this.cantDocumentos = cantDocumentos;
        }

        public int getMaximaFrecuencia() {
            return maximaFrecuencia;
        }

        public void setMaximaFrecuencia(int maximaFrecuencia) {
            this.maximaFrecuencia = maximaFrecuencia;
        }

        public int getIndice() {
            return indice;
        }

        private void setIndice(int indice) {
            this.indice = indice;
        }

        @Override
        public int compareTo(Object o) {
            Termino term = (Termino)o;
            if (term.getCantDocumentos() < this.cantDocumentos)
                return 1;
            else if (term.getCantDocumentos() > this.cantDocumentos)
                return -1;
            else
                return 0;
        }
    }


}

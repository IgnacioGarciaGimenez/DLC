package utn.dlc.tpindexado.dominio;


import utn.dlc.tpindexado.vocabularioserializacion.VocabularioIOException;
import utn.dlc.tpindexado.vocabularioserializacion.VocabularioReader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.Serializable;
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
                vocabulario.put(k, new Termino(1, v, k));
            }
        });
        return documentoParseado;
    }


    /*public void write() throws VocabularioIOException {
        if (cambio)
            new VocabularioWriter().write(this);
        cambio = true;
    }*/

    public Termino get(String key) {
        return this.vocabulario.get(key);
    }

    public int size() {
        return this.vocabulario.size();
    }

    @Entity
    @Table(name = "Terminos")
    public class Termino implements Serializable, Comparable{

        @Id
        @Column(name = "vocabulario_ID")
        private int indice;
        @Column(name = "cantDocumentos")
        private int cantDocumentos;
        @Column(name = "maxFrecuencia")
        private int maximaFrecuencia;
        @Column(name = "termino")
        private String palabra;

        public Termino() {

        }

        private Termino(int cantDocumentos, int maximaFrecuencia, String palabra) {
            this.cantDocumentos = cantDocumentos;
            this.maximaFrecuencia = maximaFrecuencia;
            this.indice = ++PROV_INDICE;
            this.palabra = palabra;
        }

        public String getPalabra() {
            return palabra;
        }

        public void setPalabra(String palabra) {
            this.palabra = palabra;
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

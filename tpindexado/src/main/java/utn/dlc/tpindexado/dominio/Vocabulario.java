package utn.dlc.tpindexado.dominio;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.util.*;

public class Vocabulario {

    private HashMap<String, Termino> vocabulario;
    private HashMap<String, Termino> terminosModificados;
    private static Vocabulario instance = null;
    private static int PROV_INDICE = 0;

    private Vocabulario() {
        this.vocabulario = new HashMap<>();
        this.terminosModificados = new HashMap<>();
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

    public Hashtable<String, Integer> agregarDocumento(File file) {
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
            terminosModificados.put(k, vocabulario.get(k));
        });
        return documentoParseado;
    }

    public HashMap<String, Termino> getTerminosModificados() {
        return terminosModificados;
    }

    public void setTerminosModificados(HashMap<String, Termino> terminosModificados) {
        this.terminosModificados = terminosModificados;
    }

    public Termino get(String key) {
        return this.vocabulario.get(key);
    }

    public int size() {
        return this.vocabulario.size();
    }

    @Entity(name = "Termino")
    @Table(name = "terminos")
    public class Termino implements Comparable{

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

        public Termino(int cantDocumentos, int maximaFrecuencia, String palabra) {
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

        public void setIndice(int indice) {
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

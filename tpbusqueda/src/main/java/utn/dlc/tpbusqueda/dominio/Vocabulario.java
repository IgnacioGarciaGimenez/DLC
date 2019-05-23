package utn.dlc.tpbusqueda.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;

public class Vocabulario {

    private HashMap<String, Termino> vocabulario;
    private static Vocabulario instance = null;

    private Vocabulario() {
        vocabulario = new HashMap<>();
    }

    public static Vocabulario getInstance() {
        if (instance == null)
            instance = new Vocabulario();
        return instance;
    }

    public HashMap<String, Termino> getVocabulario() { return vocabulario; }

    public void put(String key, Termino value) {
        vocabulario.put(key, value);
    }

    public Termino get(String key) {
        return this.vocabulario.get(key);
    }

    public int size() {
        return this.vocabulario.size();
    }

    @Entity
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

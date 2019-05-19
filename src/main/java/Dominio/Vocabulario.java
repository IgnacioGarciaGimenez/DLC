package Dominio;

import VocabularioSerializacion.VocabularioIOException;
import VocabularioSerializacion.VocabularioReader;
import VocabularioSerializacion.VocabularioWriter;

import java.io.File;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class Vocabulario implements Serializable {

    private Hashtable<String, Termino> vocabulario;
    private static Vocabulario instance = null;
    private static int PROV_INDICE = 0;

    private Vocabulario() {
        this.vocabulario = new Hashtable<>();
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
        instance.getVocabulario().forEach((k, v) -> {
            System.out.println("K: " + k + ", V: " + v.getCantDocumentos());
        });
        System.out.println(instance.getVocabulario().size());
        System.out.println(PROV_INDICE);
        new Scanner(System.in).nextLine();
        return instance;
    }

    public Hashtable<String, Termino> getVocabulario() {
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
                vocabulario.put(k, new Termino(1, v));
            }
        });
        return documentoParseado;
    }

    public void write() throws VocabularioIOException {
        new VocabularioWriter().write(this);
    }

    public class Termino implements Serializable {

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
    }


}

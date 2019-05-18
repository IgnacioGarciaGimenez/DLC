package Dominio;

import Gestores.GestorDB;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

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
        Hashtable<String, ArrayList<EntradaPosteo>> posteoDocumento = posteo.indexarDocumento(documentoParseado, file.getAbsolutePath());

        /*
        // Guardo vocabulario hasta el momento. Si hay duplicados se ignora. Solo se guarda el ID y nombre del término
        Set<String> docParseadoSet = documentoParseado.keySet();
        StringBuilder sql = new StringBuilder(5000);
        sql.append("INSERT IGNORE INTO vocabulario (termino) VALUES ");
        for (String termino : docParseadoSet) {
            sql.append("(?), ");
        }

        String sql2 = sql.substring(0, sql.length() - 2);
        try {
            PreparedStatement pstmt = GestorDB.connection.prepareStatement(sql2);

            int i = 1;
            for (String termino: docParseadoSet) {
                pstmt.setString(i, termino);
                i++;
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

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

        // GUARDO EL POSTEO
        StringBuilder sql = new StringBuilder(5000000);
        sql.append("INSERT INTO posteo (ruta, frecuencia, vocabulario_provisorio_ID) VALUES ");

        Set<String> p = posteoDocumento.keySet();
        for (String t : p) {
            posteoDocumento.get(t).forEach((e) -> {
                int indice = vocabulario.get(t).getIndice();

                sql.append("('" + e.getRutaDocumento() + "', " + e.getApariciones() + ", " + indice + "), ");
            });
        }
        String sql2 = sql.substring(0, sql.length() - 2);

        try {
            Statement st = GestorDB.connection.createStatement();
            st.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarVocabulario() {
        Set<String> voc = vocabulario.keySet();
        StringBuilder sql = new StringBuilder(5000);
        sql.append("INSERT INTO vocabulario (vocabulario_ID, termino, cantDocumentos, maxFrecuencia) VALUES ");
        for (String termino : voc) {
            Termino t = vocabulario.get(termino);
            int indice = t.getIndice();
            int cantDocumentos = t.getCantDocumentos();
            int maxFrecuencia = t.getMaximaFrecuencia();
            sql.append("(").append(indice).append(", ?, ").append(cantDocumentos).append(", ").append(maxFrecuencia).append("), ");
        }

        String sql2 = sql.substring(0, sql.length() - 2);
        System.out.println(sql2);

        try {
            PreparedStatement pstmt = GestorDB.connection.prepareStatement(sql2);

            int i = 1;
            for (String termino: voc) {
                pstmt.setString(i, termino);
                i++;
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

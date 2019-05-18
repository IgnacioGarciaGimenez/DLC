package Dominio;

import Gestores.GestorDB;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        int docId = -1;
        String aaa = "SELECT documento_ID FROM documento WHERE ruta LIKE '" + file.getAbsolutePath() + "'";
        try {
            Statement statement1 = GestorDB.connection.createStatement();
            ResultSet res = statement1.executeQuery(aaa);
            if (res.next()) {
                docId = res.getInt("documento_ID");
            }
            else {
                String insert = "INSERT INTO documento (ruta) VALUES (?)";
                // Agrega documento a la DB
                PreparedStatement statement = GestorDB.connection.prepareStatement(insert,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, file.getAbsolutePath());
                docId = statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LectorDocumento lector = new LectorDocumento(file);

        Hashtable<String, Integer> documentoParseado = lector.procesarArchivo();
        Posteo posteo = Posteo.getInstance();
        Hashtable<String, ArrayList<EntradaPosteo>> posteoDocumento = posteo.indexarDocumento(documentoParseado, file.getAbsolutePath());

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
        sql.append("INSERT INTO posteo (documento_ID, frecuencia, vocabulario_provisorio_ID) VALUES ");

        Set<String> p = posteoDocumento.keySet();
        for (String t : p) {
            for (EntradaPosteo e : posteoDocumento.get(t)) {
                int indice = vocabulario.get(t).getIndice();

                sql.append("(" + docId + ", " + e.getApariciones() + ", " + indice + "), ");
            }
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

    public void actuaizarReferenciaDePosteoAVocabulario() {
        String sql = "UPDATE posteo SET vocabulario_ID = vocabulario_provisorio_ID";
        try {
            Statement stmt = GestorDB.connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

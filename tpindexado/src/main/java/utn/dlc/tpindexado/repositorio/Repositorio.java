package utn.dlc.tpindexado.repositorio;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Posteo;

import javax.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Repositorio {

    private static final String URL = "jdbc:mysql://localhost/dlc";
    private static final String USER = "root";
    private static final String PASS = "root";



    private static EntityManager em = Persistence.createEntityManagerFactory("IndexPU").createEntityManager();

    public Repositorio() {
    }

    public Documento getDocumentByName(String name) {
        Documento output = null;
        TypedQuery<Documento> q = em.createQuery("select d from Documento d where d.titulo LIKE '" + name + "'", Documento.class);
        try {
            output = q.getSingleResult();
        } catch (Exception e) {}
        return output;
    }

    public void addDocumento(List<Documento> documento) {

        System.out.println("Iniciando transaccion");
        em.getTransaction().begin();
        for (Documento doc : documento)
            em.persist(doc);
        em.getTransaction().commit();
        /*Documento output = null;
        String insert = "INSERT INTO documento (titulo, ruta) VALUES (?, ?)";
        PreparedStatement statement = null;
        try {
            statement = Repositorio.connection.prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, titulo);
            statement.setString(2, path);
            statement.executeUpdate();
            ResultSet tableKeys = statement.getGeneratedKeys();
            tableKeys.next();
            int documentId = tableKeys.getInt(1);
            output = new Documento(documentId, titulo, path);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;*/
    }

    /*public void addPosteos(ArrayList<Posteo> posteos) {

        StringBuilder sql = new StringBuilder(5000000);
        sql.append("INSERT INTO posteo (documento_ID, frecuencia, vocabulario_ID) VALUES ");
        posteos.forEach((p) -> {
            sql.append("(" + p.getDocumentoId() + ", " + p.getFrecuencia() + ", " + p.getIndice() + "), ");
        });
        String sql2 = sql.substring(0, sql.length() - 2);
        try {
            Statement statement = Repositorio.connection.createStatement();
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/




}

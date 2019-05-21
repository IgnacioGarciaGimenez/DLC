package utn.dlc.tpindexado.repositorio;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Posteo;

import javax.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Repositorio {

    private static final String URL = "jdbc:mysql://localhost/dlc";
    private static final String USER = "root";
    private static final String PASS = "root";

    private EntityManager em;

    public Repositorio() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IndexPU");
        em = emf.createEntityManager();
    }

    public Documento getDocumentByName(String name) {
        TypedQuery<Documento> q = em.createQuery("select d from Documento d where d.titulo LIKE " + name, Documento.class);
        Documento output = q.getSingleResult();
        return output;
    }

    public void addDocumento(Documento documento) {

        em.getTransaction().begin();
        em.persist(documento);
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

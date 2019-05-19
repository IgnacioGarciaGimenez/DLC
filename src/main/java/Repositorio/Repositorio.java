package Repositorio;

import Dominio.Documento;
import Dominio.Posteo;
import Dominio.Vocabulario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Repositorio {

    private static final String URL = "jdbc:mysql://localhost/dlc";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection connection = null;

    public Repositorio() {

    }

    public static void iniciarConexión() {
        // Conectamos con la base de datos
        try {
            connection = DriverManager.getConnection(URL,USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Documento getDocumentByName(String name) {
        Documento output = null;
        String query = "SELECT * FROM documento WHERE titulo LIKE '" + name + "'";
        try {
            PreparedStatement statement1 = Repositorio.connection.prepareStatement(query);
            ResultSet res = statement1.executeQuery(query);
            if (res.next()) {
                output = new Documento(res.getInt("documento_ID"), res.getString("titulo"),
                        res.getString("ruta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public Documento addDocumento(String titulo, String path) {

        Documento output = null;
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
        return output;
    }

    public void addPosteos(ArrayList<Posteo> posteos) {

        StringBuilder sql = new StringBuilder(5000000);
        sql.append("INSERT INTO posteo (documento_ID, frecuencia, vocabulario_ID) VALUES ");
        posteos.forEach((p) -> {
            sql.append("(" + p.getDocumentoId() + ", " + p.getFrecuencia() + ", " + p.getIndice() + "), ");
        });

        try {
            Statement statement = Repositorio.connection.createStatement();
            statement.executeUpdate(sql.substring(0, sql.length() - 2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package Gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorDB {
    private static final String URL = "jdbc:mysql://localhost/dlcschema";
    private static final String USER = "root";
    private static final String PASS = "253614";

    public static Connection connection = null;

    public static void iniciarConexión() {
        // Conectamos con la base de datos
        try {
            connection = DriverManager.getConnection(URL,USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void statement(String sql) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

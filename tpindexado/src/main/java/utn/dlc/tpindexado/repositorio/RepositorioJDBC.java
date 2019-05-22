package utn.dlc.tpindexado.repositorio;

import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.gestores.IRepositorio;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import java.sql.*;

@RequestScoped
public class RepositorioJDBC implements IRepositorio {

    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/dlc";
    private static final String USER = "root";
    private static final String PASS = "root";

    public RepositorioJDBC() {
        System.out.println("Usando JDBC");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Documento getDocumentoByName(String name) {
        Documento output = null;
        String query = "SELECT * FROM documentos WHERE titulo LIKE '" + name + "'";
        try {
            Statement statement1 = this.connection.createStatement();
            ResultSet res = statement1.executeQuery(query);
            if (res.next()) {
                output = new Documento();
                output.setId(res.getInt("documento_ID"));
                output.setTitulo(res.getString("titulo"));
                output.setRuta(res.getString("ruta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void addDocumento(Documento documento) {

        String insert = "INSERT INTO documentos (titulo, ruta) VALUES (?, ?)";
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, documento.getTitulo());
            statement.setString(2, documento.getRuta());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            res.next();
            documento.setId(res.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder sql = new StringBuilder(5000000);
        sql.append("INSERT INTO posteos (documento_ID, frecuencia, vocabulario_ID) VALUES ");
        documento.getPosteos().forEach((p) -> {
            sql.append("(" + documento.getId() + ", " + p.getFrecuencia() + ", " + p.getIndice() + "), ");
        });
        String sql2 = sql.substring(0, sql.length() - 2);
        try {
            Statement posteoStatement = this.connection.createStatement();
            statement.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addVocabulario(Vocabulario vocabulario) {

    }

    @Override
    public Vocabulario getVocabulario() {
        return null;
    }
}

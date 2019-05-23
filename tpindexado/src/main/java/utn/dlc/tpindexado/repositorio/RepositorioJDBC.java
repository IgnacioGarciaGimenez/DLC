package utn.dlc.tpindexado.repositorio;

import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.gestores.IRepositorio;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

@RequestScoped
public class RepositorioJDBC implements IRepositorio {

    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost/dlc";
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

    public void addDocumentos(List<Documento> documentos) {

        for (Documento documento : documentos) {
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
                posteoStatement.addBatch(sql2);
                posteoStatement.executeBatch();
                System.out.println("Documento: " + documento.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateVocabulario(Vocabulario vocabulario) {
        StringBuilder sql = new StringBuilder(5000000);
        sql.append("INSERT INTO terminos (vocabulario_ID, termino, cantDocumentos, maxFrecuencia) VALUES ");
        HashMap<String, Vocabulario.Termino> terminos = vocabulario.getTerminosModificados();
        int i = 0;
        for (Vocabulario.Termino v : terminos.values()) {
            if (i > 0 && i % 10000 == 0) {
                String sql2 = sql.substring(0, sql.length()-2);
                sql2 +=" ON DUPLICATE KEY UPDATE cantDocumentos=VALUES(cantDocumentos) , maxFrecuencia=VALUES(maxFrecuencia)" ;
                Statement q = null;
                try {
                    q = this.connection.createStatement();
                    q.addBatch(sql2);
                    q.executeLargeBatch();
                    System.out.println(i);
                } catch (SQLException e) {
                    e.printStackTrace();

                }
                sql = new StringBuilder(5000000);
                sql.append("INSERT INTO terminos (vocabulario_ID, termino, cantDocumentos, maxFrecuencia) VALUES ");
            }
            sql.append("(" + v.getIndice() + ", \"" + v.getPalabra() + "\", " + v.getCantDocumentos() + ", " + v.getMaximaFrecuencia()
                    + "), ");
            i++;
        }

        String sql2 = sql.substring(0, sql.length()-2);
        sql2 +=" ON DUPLICATE KEY UPDATE cantDocumentos=VALUES(cantDocumentos) , maxFrecuencia=VALUES(maxFrecuencia)" ;
        Statement q = null;
        try {
            q = this.connection.createStatement();
            q.addBatch(sql2);
            q.executeLargeBatch();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void llenarVocabulario(Vocabulario v) {
        if (v.size() == 0) {
            String sql = "SELECT * FROM terminos";
            HashMap<String, Vocabulario.Termino> map = v.getVocabulario();
            try {
                Statement q = connection.createStatement();
                ResultSet terminos = q.executeQuery(sql);
                while (terminos.next()) {
                    Vocabulario.Termino t = v.new Termino(terminos.getInt("cantDocumentos"),
                            terminos.getInt("maxFrecuencia"),
                            terminos.getString("termino"));
                    t.setIndice(terminos.getInt("vocabulario_ID"));
                    map.put(t.getPalabra(), t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

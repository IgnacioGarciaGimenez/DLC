package utn.dlc.tpindexado.repositorio;

import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Termino;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.gestores.IRepositorio;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.swing.plaf.nimbus.State;
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

    public void addDocumentos(Documento documento) {
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
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder sql = new StringBuilder(5000000);
        sql.append("INSERT INTO posteos (documento_ID, frecuencia, vocabulario_ID, palabra) VALUES ");
        documento.getPosteos().forEach((p) -> {
            sql.append("(" + documento.getId() + ", " + p.getFrecuencia() + ", " + p.getIndice() + ", \"" + p.getPalabra() + "\"), ");
        });
        String sql2 = sql.substring(0, sql.length() - 2);
        try {
            Statement posteoStatement = this.connection.createStatement();
            posteoStatement.addBatch(sql2);
            posteoStatement.executeBatch();
            posteoStatement.close();
            System.out.println("Documento: " + documento.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateVocabulario(Vocabulario vocabulario) {

        if (Vocabulario.cambio) {
            System.out.println("Borrando");
            //Borramos
            String deleteVocabulario = "DELETE FROM terminos";
            try {
                Statement deleteq = connection.createStatement();
                deleteq.execute(deleteVocabulario);
                deleteq.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Guardando");
            //Guardamos de nuevo
            StringBuilder sql = new StringBuilder(5000000);
            sql.append("INSERT INTO terminos (vocabulario_ID, termino, cantDocumentos, maxFrecuencia) VALUES ");
            HashMap<String, Termino> terminos = vocabulario.getVocabulario();
            int i = 0;
            for (Termino v : terminos.values()) {
                if (i > 0 && i % 10000 == 0) {
                    String sql2 = sql.substring(0, sql.length()-2);

                    Statement q = null;
                    try {
                        q = this.connection.createStatement();
                        q.addBatch(sql2);
                        q.executeBatch();
                        q.close();
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
            Statement q = null;
            try {
                q = this.connection.createStatement();
                q.addBatch(sql2);
                q.executeBatch();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
            Vocabulario.cambio = false;
        }



    }

    @Override
    public void llenarVocabulario(Vocabulario v) {
        if (v.size() == 0) {
            String sql = "SELECT * FROM terminos";
            HashMap<String, Termino> map = v.getVocabulario();
            try {
                Statement q = connection.createStatement();
                ResultSet terminos = q.executeQuery(sql);
                while (terminos.next()) {
                    Termino t = new Termino(terminos.getInt("cantDocumentos"),
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

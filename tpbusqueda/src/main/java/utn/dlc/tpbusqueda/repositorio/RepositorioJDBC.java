package utn.dlc.tpbusqueda.repositorio;

import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Vocabulario;
import utn.dlc.tpbusqueda.dominio.Vocabulario.Termino;
import utn.dlc.tpbusqueda.gestores.IRepository;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Alternative
@RequestScoped
public class RepositorioJDBC implements IRepository {

    private static final String URL = "jdbc:mysql:://localhost:3306/dlc";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Connection connection;

    public RepositorioJDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCantidadTotalDeDocs() {
        String sql = "SELECT COUNT(*) FROM documentos";
        try {
            Statement q = connection.createStatement();
            ResultSet res = q.executeQuery(sql);
            if (res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Documento> getDocumentosMasRelevantesPorTermino(List<Termino> terminos, int cantDocs) {
        StringBuilder terminosString = new StringBuilder(5000000);
        List<Documento> docList = new ArrayList<>();
        for (Termino t : terminos) {
            terminosString.append(t.getIndice() + ", ");
        }

        String sql1 = terminosString.substring(0, terminosString.length() - 2);
        String sql = "SELECT d.documento_ID, d.titulo, d.ruta, p.frecuencia, p.vocabulario_ID FROM posteo p "
                + "INNER JOIN documento d ON p.documento_ID = d.documento_ID " +
                "WHERE p.vocabulario_ID IN(" + sql1 + ") ORDER BY p.frecuencia DESC";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet set = st.executeQuery(sql);
            while (set.next()) {
                Documento doc = new Documento();
                doc.setId(set.getInt("documento_ID"));
                doc.setTitulo(set.getString("titulo"));
                doc.setRuta(set.getString("ruta"));
                int indice = set.getInt("vocabulario_ID");
                for (Termino t : terminos) {
                    if (t.getIndice() == indice){
                        doc.calcularPeso(t.getCantDocumentos(), cantDocs, set.getInt("frecuencia"));
                        break;
                    }
                }
                docList.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docList;
    }

    @Override
    public void llenarVocabulario(Vocabulario v) {
        if (v.size() == 0) {
            String sql = "SELECT * FROM terminos";
            HashMap<String, Vocabulario.Termino> map = v.getVocabulario();
            try {
                Statement q = this.connection.createStatement();
                ResultSet terminos = q.executeQuery(sql);
                while (terminos.next()) {
                    Termino t = v.new Termino();
                    t.setCantDocumentos(terminos.getInt("cantDocumentos"));
                    t.setMaximaFrecuencia(terminos.getInt("maxFrecuencia"));
                    t.setPalabra(terminos.getString("termino"));
                    t.setIndice(terminos.getInt("vocabulario_ID"));
                    map.put(t.getPalabra(), t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void actualizarVocabulario(Vocabulario v) {
        String sql = "SELECT * FROM terminos";
        HashMap<String, Vocabulario.Termino> map = v.getVocabulario();
        try {
            Statement q = this.connection.createStatement();
            ResultSet terminos = q.executeQuery(sql);
            while (terminos.next()) {
                Termino t = v.new Termino();
                t.setCantDocumentos(terminos.getInt("cantDocumentos"));
                t.setMaximaFrecuencia(terminos.getInt("maxFrecuencia"));
                t.setPalabra(terminos.getString("termino"));
                t.setIndice(terminos.getInt("vocabulario_ID"));
                map.put(t.getPalabra(), t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

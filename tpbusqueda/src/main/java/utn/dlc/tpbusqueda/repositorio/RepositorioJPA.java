package utn.dlc.tpbusqueda.repositorio;

import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Vocabulario;
import utn.dlc.tpbusqueda.dominio.Vocabulario.Termino;
import utn.dlc.tpbusqueda.gestores.IRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RequestScoped
public class RepositorioJPA implements IRepository {

    @Inject
    private EntityManager em;

    public RepositorioJPA() {

    }

    @Override
    public int getCantidadTotalDeDocs() {
        Query q = em.createQuery("select COUNT(d.id) from Documento d");
        int count = (int)q.getSingleResult();
        return count;

    }

    @Override
    public List<Documento> getDocumentosMasRelevantesPorTermino(List<Termino> terminos, int cantDocs) {
        String sql = "select d.id, d.titulo, d.ruta, p.frecuencia, p.indice from Documento d join d.posteos p " +
                "where p.indice IN :indices";
        Query q = em.createQuery(sql);
        List<String> indices = new ArrayList<>();
        for (Termino term : terminos)
            indices.add(Integer.toString(term.getIndice()));
        q.setParameter("indices", indices);
        List<Object[]> set = q.getResultList();
        List<Documento> docs = new ArrayList<>();
        for (Object[] o : set) {
            Documento doc = new Documento();
            doc.setId((int)o[0]);
            doc.setTitulo((String)o[1]);
            doc.setRuta((String)o[2]);
            int indice = (int)o[4];
            for (Termino term : terminos) {
                if (term.getIndice() == indice) {
                    doc.calcularPeso(term.getCantDocumentos(), cantDocs, (int)o[3]);
                    break;
                }
            }
            docs.add(doc);
        }
        return docs;



    }

    @Override
    public void llenarVocabulario(Vocabulario v) {
        if (v.size() == 0) {
            TypedQuery<Vocabulario.Termino> q = em.createQuery("select t from Termino t", Vocabulario.Termino.class);
            List<Vocabulario.Termino> terminos = q.getResultList();
            HashMap<String, Vocabulario.Termino> vocab = v.getVocabulario();
            terminos.forEach((t) -> {
                vocab.put(t.getPalabra(), t);
            });
        }
    }

    @Override
    public void actualizarVocabulario(Vocabulario v) {
        TypedQuery<Vocabulario.Termino> q = em.createQuery("select t from Termino t", Vocabulario.Termino.class);
        List<Vocabulario.Termino> terminos = q.getResultList();
        HashMap<String, Vocabulario.Termino> vocab = v.getVocabulario();
        terminos.forEach((t) -> {
            vocab.put(t.getPalabra(), t);
        });
    }
}

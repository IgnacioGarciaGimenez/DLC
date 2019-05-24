package utn.dlc.tpbusqueda.repositorio;

import javafx.geometry.Pos;
import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Posteo;
import utn.dlc.tpbusqueda.dominio.Vocabulario;
import utn.dlc.tpbusqueda.dominio.Termino;
import utn.dlc.tpbusqueda.gestores.IRepository;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.*;
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
    public long getCantidadTotalDeDocs() {
        Query q = em.createQuery("select COUNT(d.id) from Documento d");
        long count = (long)q.getSingleResult();
        return count;

    }

    @Override
    public List<Documento> getDocumentosMasRelevantesPorTermino(List<Termino> terminos, long cantDocs, int R) {
        if (terminos.size() > 0) {
            List<Documento> documentos = new ArrayList<>();
            for (Termino termino : terminos) {
                String sql = "select d.id, d.titulo, d.ruta, p.frecuencia, p.indice from Documento d join d.posteos p " +
                        "where p.indice = :indice order by p.frecuencia desc";
                Query q = em.createQuery(sql).setMaxResults(15);
                q.setParameter("indice", termino.getIndice());
                List<Object[]> set = q.getResultList();
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
                    documentos.add(doc);
                }
            }
            return documentos;
        }
        return new ArrayList<>();



        /*if (terminos.size() > 0) {
            String sql = "select d.id, d.titulo, d.ruta, p.frecuencia, p.indice from Documento d join d.posteos p " +
                    "where p.indice IN :indices";
            Query q = em.createQuery(sql);
            List<Integer> indices = new ArrayList<>();
            for (Termino term : terminos)
                indices.add(term.getIndice());
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
        return new ArrayList<>();*/

    }

    public List<Posteo> getAllPosteosOfDocument(Documento d) {
        if (d != null) {
            TypedQuery<Posteo> q = em.createQuery("select p from Posteo p where p.documento.id = :indice" , Posteo.class);
            q.setParameter("indice", d.getId());
            return q.getResultList();
        }
        return new ArrayList<>();
    }

    @Override
    public void llenarVocabulario(Vocabulario v) {
        if (v.size() == 0) {
            TypedQuery<Termino> q = em.createQuery("select t from Termino t", Termino.class);
            List<Termino> terminos = q.getResultList();
            HashMap<String, Termino> vocab = v.getVocabulario();
            terminos.forEach((t) -> {
                vocab.put(t.getPalabra(), t);
            });
        }
    }

    @Override
    public void actualizarVocabulario(Vocabulario v) {
        TypedQuery<Termino> q = em.createQuery("select t from Termino t", Termino.class);
        List<Termino> terminos = q.getResultList();
        HashMap<String, Termino> vocab = v.getVocabulario();
        terminos.forEach((t) -> {
            vocab.put(t.getPalabra(), t);
        });
    }
}

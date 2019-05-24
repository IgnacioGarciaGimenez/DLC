package utn.dlc.tpindexado.repositorio;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Termino;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.gestores.IRepositorio;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;


@RequestScoped
public class RepositorioJPA implements IRepositorio {


    @Inject
    private EntityManager em;

    public RepositorioJPA() {
    }

    public Documento getDocumentoByName(String name) {
        Documento output = null;
        TypedQuery<Documento> q = em.createQuery("select d from Documento d where d.titulo LIKE '" + name + "'", Documento.class);
        try {
            output = q.getSingleResult();
        } catch (Exception e) {}
        return output;
    }

    public void addDocumentos(Documento documento) {
        em.getTransaction().begin();
        em.persist(documento);
        em.getTransaction().commit();
        em.clear();

    }

    @Override
    public void updateVocabulario(Vocabulario vocabulario) {
        if (Vocabulario.cambio) {
            em.getTransaction().begin();
            em.createQuery("delete from Termino").executeUpdate();
            em.getTransaction().commit();
            HashMap<String, Termino> actualizar = vocabulario.getVocabulario();
            em.getTransaction().begin();
            actualizar.values().forEach((v) -> {
                Termino term = new Termino(v.getCantDocumentos(), v.getMaximaFrecuencia(), v.getPalabra());
                term.setIndice(v.getIndice());
                em.persist(term);
            });
            em.getTransaction().commit();
            Vocabulario.cambio = false;
        }

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


}

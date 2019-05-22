package utn.dlc.tpindexado.repositorio;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.gestores.IRepositorio;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;

@Alternative
@RequestScoped
public class RepositorioJPA implements IRepositorio {

    private static final String URL = "jdbc:mysql://localhost/dlc";
    private static final String USER = "root";
    private static final String PASS = "root";


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

    public void addDocumento(Documento documento) {
        em.getTransaction().begin();
        em.persist(documento);
        em.getTransaction().commit();
    }

    @Override
    public void addVocabulario(Vocabulario vocabulario) {
        em.getTransaction().begin();
        vocabulario.getVocabulario().keySet().forEach((v) -> {
            em.persist(v);
        });
        em.getTransaction().commit();
    }

    @Override
    public Vocabulario getVocabulario() {
        return null;
    }


}

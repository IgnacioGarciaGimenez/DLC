package utn.dlc.tpindexado.repositorio;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.gestores.IRepositorio;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

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

    public void addDocumentos(List<Documento> documentos) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (int i = 0; i < documentos.size(); i++) {
                if (i > 0 && i % 25 == 0) {
                    transaction.commit();
                    transaction.begin();

                    em.clear();
                }
                em.persist(documentos.get(i));
            }

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @Override
    public void updateVocabulario(Vocabulario vocabulario) {
        HashMap<String, Vocabulario.Termino> actualizar = vocabulario.getTerminosModificados();
        em.getTransaction().begin();
        actualizar.keySet().forEach((v) -> {
            em.persist(v);
        });
        em.getTransaction().commit();
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


}

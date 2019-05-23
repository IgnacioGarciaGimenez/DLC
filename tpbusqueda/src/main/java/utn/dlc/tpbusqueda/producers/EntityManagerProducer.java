package utn.dlc.tpbusqueda.producers;

import javax.enterprise.inject.Disposes;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Produces;

@Named
@RequestScoped
public class EntityManagerProducer {

    @Inject
    private EntityManagerFactory emf;

    @Produces
    @RequestScoped
    public EntityManager create() {
        return emf.createEntityManager();
    }

    public void destroy(@Disposes EntityManager em) {
        em.close();
    }

}

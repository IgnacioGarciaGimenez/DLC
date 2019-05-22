package utn.dlc.tpindexado.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named
@ApplicationScoped
public class EntityManagerFactoryProducer {

    @Produces
    @ApplicationScoped
     public EntityManagerFactory create() {
            return Persistence.createEntityManagerFactory("IndexPU");
    }

    public void destroy(@Disposes EntityManagerFactory emf) {
        emf.close();
    }
}

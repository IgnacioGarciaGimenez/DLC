package utn.dlc.tpbusqueda.endpoints;

import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.gestores.IGestorBusqueda;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/actualizar")
public class ActualizarEndpoint {
    @Inject
    private IGestorBusqueda gestor;


    @POST
    @Produces("application/json")
    public Response doPost() {
        System.out.println("Llego la peticion");
        gestor.actualizarVocabulario();
        return Response.ok(true).build();
    }
}

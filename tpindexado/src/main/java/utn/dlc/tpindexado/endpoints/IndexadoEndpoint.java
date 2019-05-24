package utn.dlc.tpindexado.endpoints;


import utn.dlc.tpindexado.gestores.IGestorIndexado;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/indexado")
public class IndexadoEndpoint {

	@Inject
	private IGestorIndexado gestor;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet() {
		//gestor.indexar();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Response.ok(true).build();
	}
}

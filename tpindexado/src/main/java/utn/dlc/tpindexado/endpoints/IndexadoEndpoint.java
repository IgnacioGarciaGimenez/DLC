package utn.dlc.tpindexado.endpoints;


import utn.dlc.tpindexado.gestores.IGestorIndexado;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/indexar")
public class IndexadoEndpoint {

	@Inject
	private IGestorIndexado gestor;

	@GET
	@Produces("application/json")
	public Response doGet() {
		gestor.indexar();
		return Response.ok(true).build();
	}
}

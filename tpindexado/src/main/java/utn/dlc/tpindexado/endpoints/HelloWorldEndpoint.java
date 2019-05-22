package utn.dlc.tpindexado.endpoints;


import utn.dlc.tpindexado.gestores.GestorIndexado;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/hello")
public class HelloWorldEndpoint {

	@Inject
	private GestorIndexado gestor;

	@GET
	@Produces("text/plain")
	public Response doGet() {
		gestor.indexar();
		return Response.ok("Hello from Thorntail!").build();
	}
}

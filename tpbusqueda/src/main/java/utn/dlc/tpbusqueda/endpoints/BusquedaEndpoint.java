package utn.dlc.tpbusqueda.endpoints;


import utn.dlc.tpbusqueda.gestores.IGestorBusqueda;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/buscar")
public class BusquedaEndpoint {

	@Inject
	private IGestorBusqueda gestor;

	@GET
	@Produces("text/plain")
	public Response doGet() {

		return Response.ok("Hello from Thorntail!").build();
	}
}

package utn.dlc.tpbusqueda.endpoints;

import utn.dlc.tpbusqueda.gestores.IGestorBusqueda;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Path("/buscar")
public class BusquedaEndpoint {

	@Inject
	private IGestorBusqueda gestor;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@QueryParam("query") String query) {
		System.out.println("CONSULTAAAA: " + query);
		return Response.ok(gestor.buscar(query)).build();
	}
}

package utn.dlc.tpbusqueda.endpoints;


import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.gestores.IGestorBusqueda;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.List;


@Path("/buscar")
public class BusquedaEndpoint {

	@Inject
	private IGestorBusqueda gestor;

	@Path("{query}")
	@GET
	@Produces("application/json")
	public Response doGet(@PathParam("query") String query) {
		List<Documento> docs = gestor.buscar(query);
		/*if (peso == 1)
			docs = gestor.buscar(query, R, true);
		else
			docs = gestor.buscar(query, R, false);*/
		return Response.ok(docs).build();
	}
}

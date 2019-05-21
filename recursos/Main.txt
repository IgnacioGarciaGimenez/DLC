import Gestores.GestorIndexado;
import GestoresBusqueda.GestorBusqueda;
import Repositorio.Repositorio;

public class Main {
    public static void main(String[] args) {

        Repositorio.iniciarConexión();

        GestorIndexado gestor = new GestorIndexado();
        gestor.indexar();
        GestorBusqueda gestorBusqueda = new GestorBusqueda("succubus");
        gestorBusqueda.buscarDocumentos();
        gestorBusqueda.obtenerDocumentos().forEach((d) -> {
            System.out.println("Nombre Documento: " + d.getNombre() + ", Peso: " + d.getPeso());
        });
    }
}

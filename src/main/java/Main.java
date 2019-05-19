import Gestores.GestorIndexado;
import Repositorio.Repositorio;

public class Main {
    public static void main(String[] args) {
        Repositorio.iniciarConexión();

        GestorIndexado gestor = new GestorIndexado();
        gestor.indexar();
    }
}

import Gestores.GestorDB;
import Gestores.GestorIndexado;

public class Main {
    public static void main(String[] args) {
        GestorDB.iniciarConexión();

        GestorIndexado gestor = new GestorIndexado();
        gestor.indexar();
    }
}

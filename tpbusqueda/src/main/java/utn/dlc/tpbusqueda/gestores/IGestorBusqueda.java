package utn.dlc.tpbusqueda.gestores;

import utn.dlc.tpbusqueda.dominio.Documento;

import java.util.List;

public interface IGestorBusqueda {
    List<Documento> buscar(String busqueda, int cantidad, boolean peso);
    void actualizarVocabulario();
}

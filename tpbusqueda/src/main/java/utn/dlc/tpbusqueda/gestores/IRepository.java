package utn.dlc.tpbusqueda.gestores;

import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Vocabulario;
import utn.dlc.tpbusqueda.dominio.Vocabulario.Termino;

import java.util.List;

public interface IRepository {

    int getCantidadTotalDeDocs();
    List<Documento> getDocumentosMasRelevantesPorTermino(List<Termino> terminos, int cantDocs);
    void llenarVocabulario(Vocabulario v);
    void actualizarVocabulario(Vocabulario v);
}

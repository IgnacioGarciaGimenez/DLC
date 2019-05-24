package utn.dlc.tpbusqueda.gestores;

import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Posteo;
import utn.dlc.tpbusqueda.dominio.Termino;
import utn.dlc.tpbusqueda.dominio.Vocabulario;

import java.util.List;

public interface IRepository {

    long getCantidadTotalDeDocs();
    List<Documento> getDocumentosMasRelevantesPorTermino(List<Termino> terminos, long cantDocs, int R);
    void llenarVocabulario(Vocabulario v);
    void actualizarVocabulario(Vocabulario v);
    List<Posteo> getAllPosteosOfDocument(Documento d);
}

package utn.dlc.tpindexado.gestores;

import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Vocabulario;

import java.util.List;


public interface IRepositorio {
    Documento getDocumentoByName(String name);
    void addDocumentos(List<Documento> documentos);
    void updateVocabulario(Vocabulario vocabulario);
    void llenarVocabulario(Vocabulario v);
}

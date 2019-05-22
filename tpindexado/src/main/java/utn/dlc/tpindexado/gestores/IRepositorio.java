package utn.dlc.tpindexado.gestores;

import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Vocabulario;


public interface IRepositorio {
    Documento getDocumentoByName(String name);
    void addDocumento(Documento documento);
    void addVocabulario(Vocabulario vocabulario);
    Vocabulario getVocabulario();
}

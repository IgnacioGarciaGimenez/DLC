package utn.dlc.tpbusqueda.gestores;


import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Vocabulario;
import utn.dlc.tpbusqueda.dominio.Vocabulario.Termino;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Named
@RequestScoped
public class GestorBusqueda implements IGestorBusqueda{

    private int N;
    private int R = 15;
    private HashMap<String, Documento> documentos = new HashMap<>();
    private Vocabulario vocabulario;
    @Inject
    private IRepository repositorio;


    public GestorBusqueda() {
        vocabulario = Vocabulario.getInstance();
    }



    public List<Documento> buscar(String busqueda) {
        repositorio.llenarVocabulario(vocabulario);
        this.N = repositorio.getCantidadTotalDeDocs();
        List<Termino> terminos = new ArrayList<>();
        if (busqueda != null || busqueda != "") {
            for (String palabra : busqueda.toLowerCase().replaceAll("[^A-Za-z']+", " ").split("\\s+")) {
                if (vocabulario.get(palabra).getCantDocumentos() > N * 0.3) continue;
                if (!terminos.contains(palabra))
                    terminos.add(vocabulario.get(palabra));
            }
        }
        this.buscarDocumentos(terminos);
        return this.obtenerDocumentos();
    }


    private void buscarDocumentos(List<Termino> terminos) {

        List<Documento> docs = repositorio.getDocumentosMasRelevantesPorTermino(terminos, N);

        for (Documento doc : docs) {
            if (!documentos.containsKey(doc.getTitulo()))
                documentos.put(doc.getTitulo(), doc);
            else {
                double peso = doc.getPeso() + documentos.get(doc.getTitulo()).getPeso();
                documentos.get(doc.getTitulo()).setPeso(peso);
            }
        }
    }

    private List<Documento> obtenerDocumentos() {

        ArrayList<Documento> iterar = new ArrayList<>(documentos.values());
        Collections.sort(iterar);
        ArrayList<Documento> output = new ArrayList<>();
        for (Documento doc : iterar) {
            if (output.size() > 15) break;
            output.add(doc);
        }
        return output;
    }



}

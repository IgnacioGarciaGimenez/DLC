package utn.dlc.tpbusqueda.gestores;

import Dominio.Vocabulario;
import Dominio.Vocabulario.Termino;
import DominioBusqueda.Documento;
import Repositorio.Repositorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class GestorBusqueda {

    private ArrayList<Termino> busqueda = new ArrayList<>();
    private int N;
    private int R = 15;
    private HashMap<String, Documento> documentos = new HashMap<>();

    public GestorBusqueda(String busqueda) {

        Repositorio r = new Repositorio();
        this.N = r.getCantidadTotalDeDocs();
        if (busqueda != null || busqueda != "") {
            Vocabulario v = Vocabulario.getInstance();
            for (String palabra : busqueda.toLowerCase().replaceAll("[^A-Za-z']+", " ").split("\\s+")) {
                if (v.get(palabra).getCantDocumentos() > N * 0.3) continue;
                if (!this.busqueda.contains(palabra))
                    this.busqueda.add(v.get(palabra));
            }
        }

    }

    public void buscarDocumentos() {

        Repositorio repo = new Repositorio();
        ArrayList<Documento> docs = repo.getDocumentosMasRelevantesPorTermino(busqueda, N);

        for (Documento doc : docs) {
            if (!documentos.containsKey(doc.getNombre()))
                documentos.put(doc.getNombre(), doc);
            else {
                double peso = doc.getPeso() + documentos.get(doc.getNombre()).getPeso();
                documentos.get(doc.getNombre()).setPeso(peso);
            }
        }
    }

    public ArrayList<Documento> obtenerDocumentos() {

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

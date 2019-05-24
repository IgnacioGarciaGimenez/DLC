package utn.dlc.tpbusqueda.gestores;


import javafx.geometry.Pos;
import utn.dlc.tpbusqueda.dominio.Documento;
import utn.dlc.tpbusqueda.dominio.Posteo;
import utn.dlc.tpbusqueda.dominio.Termino;
import utn.dlc.tpbusqueda.dominio.Vocabulario;


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

    private long N;
    private int R = 15;
    private HashMap<String, Documento> documentos = new HashMap<>();
    private Vocabulario vocabulario;
    @Inject
    private IRepository repositorio;


    public GestorBusqueda() {
        vocabulario = Vocabulario.getInstance();
    }



    public List<Documento> buscar(String busqueda, int R, boolean peso) {
        if (R != 0)
            this.R = R;
        System.out.println("Cargando Vocabulario");
        repositorio.llenarVocabulario(vocabulario);
        System.out.println("Vocabulario cargado: " + vocabulario.size());
        this.N = repositorio.getCantidadTotalDeDocs();
        List<Termino> terminos = new ArrayList<>();
        if (busqueda != null || busqueda != "") {
            for (String palabra : busqueda.toLowerCase().replaceAll("[^A-Za-z']+", " ").split("\\s+")) {
                if (vocabulario.get(palabra).getCantDocumentos() > N * 0.80) continue;
                if (!terminos.contains(palabra) && vocabulario.getVocabulario().containsKey(palabra)) {
                    terminos.add(vocabulario.get(palabra));
                }

            }
            if (terminos.size() == 0) {
                for (String palabra : busqueda.toLowerCase().replaceAll("[^A-Za-z']+", " ").split("\\s+"))
                     terminos.add(vocabulario.get(palabra));
            }
        }
        this.buscarDocumentos(terminos);
        if (peso)
            this.ajustarPesos(terminos);
        return this.obtenerDocumentos();
    }


    private void buscarDocumentos(List<Termino> terminos) {

       List<Documento> docs = repositorio.getDocumentosMasRelevantesPorTermino(terminos, N, R);

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
            if (output.size() >= R) break;
            output.add(doc);
        }
        return output;
    }

    private void ajustarPesos(List<Termino> terminos){
        for (Documento doc : documentos.values()) {
            System.out.println("Doc:" + doc.getTitulo());
            List<Posteo> posts = repositorio.getAllPosteosOfDocument(doc);
            doc.ajustarPeso(posts, N);
        }
    }

    public void actualizarVocabulario(){
        System.out.println("Actualizando vocabulario");
        repositorio.actualizarVocabulario(vocabulario);
        System.out.println("Vocabulario actualizado: " + vocabulario.size());
    }



}

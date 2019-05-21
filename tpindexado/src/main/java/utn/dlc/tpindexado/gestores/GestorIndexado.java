package utn.dlc.tpindexado.gestores;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Posteo;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.repositorio.Repositorio;

import java.io.File;
import java.util.*;

public class GestorIndexado {

    public Vocabulario vocabulario;

    public GestorIndexado() {
        vocabulario = Vocabulario.getInstance();
    }


    public static void main(String[] args) {

        GestorIndexado gestor = new GestorIndexado();
        long generalTimer = System.currentTimeMillis();
        File carpeta = new File("C:\\DocumentosTP1");
        File[] archivos = carpeta.listFiles();
        List<File> files = Arrays.asList(archivos);
        files.parallelStream().forEach((d) -> {
            gestor.agregarDocumento(d);
        });

        System.out.println("Guardando vocabulario con " + gestor.vocabulario.size() + " palabras...");


        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }

    public void agregarDocumento(File file) {

        Repositorio repo = new Repositorio();
        Documento doc = repo.getDocumentByName(file.getName());
        if (doc == null) {
            doc = new Documento();
            doc.setRuta(file.getAbsolutePath());
            doc.setTitulo(file.getName());
            System.out.println("docId " + doc.getId());

            Hashtable<String, Integer> documentoParseado = vocabulario.agregarDocumento(file);
            ArrayList<Posteo> posteos = new ArrayList<>();
            documentoParseado.forEach((k, v) -> {
                posteos.add(new Posteo(v, vocabulario.get(k).getIndice()));
            });
            doc.setPosteos(posteos);
            repo.addDocumento(doc);
        } else {
            System.out.println(file.getName() + " ya agregado.");
        }




    }
}

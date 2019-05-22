package utn.dlc.tpindexado.gestores;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Posteo;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.repositorio.Repositorio;

import javax.print.Doc;
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
        ArrayList<Documento> docs = new ArrayList<>();
        int i = 0;
        for (File f : files) {
            Documento doc = gestor.agregarDocumento(f);
            if (doc != null)
                docs.add(doc);
            System.out.println("Documento Id: " + ++i);
        };
        Repositorio repo = new Repositorio();
        repo.addDocumento(docs);
        System.out.println("Guardando vocabulario con " + gestor.vocabulario.size() + " palabras...");


        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }

    public Documento agregarDocumento(File file) {

        Repositorio repo = new Repositorio();
        Documento doc2 = repo.getDocumentByName(file.getName());
        if (doc2 == null) {
            final Documento doc = new Documento();
            doc.setRuta(file.getAbsolutePath());
            doc.setTitulo(file.getName());

            Hashtable<String, Integer> documentoParseado = vocabulario.agregarDocumento(file);
            ArrayList<Posteo> posteos = new ArrayList<>();
            documentoParseado.forEach((k, v) -> {
                Posteo p = new Posteo(v, vocabulario.get(k).getIndice());
                p.setDocumento(doc);
                posteos.add(p);
            });
            doc.setPosteos(posteos);
            //repo.addDocumento(doc);
            return doc;
        } else {
            System.out.println(file.getName() + " ya agregado.");
            return null;
        }




    }
}

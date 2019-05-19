package Gestores;

import Dominio.Documento;
import Dominio.Posteo;
import Dominio.Vocabulario;
import Repositorio.Repositorio;
import VocabularioSerializacion.VocabularioIOException;
import VocabularioSerializacion.VocabularioReader;
import VocabularioSerializacion.VocabularioWriter;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class GestorIndexado {

    private Vocabulario vocabulario;

    public GestorIndexado() {
        vocabulario = Vocabulario.getInstance();
    }


    public void indexar() {

        long generalTimer = System.currentTimeMillis();
        long timer = generalTimer;
        File carpeta = new File("C:\\DocumentosTP1");
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            for (int i = 0; i < archivos.length; i++) {
                this.agregarDocumento(archivos[i]);
                if (System.currentTimeMillis() - timer > 10000) {
                    System.out.println("Indexando: " + (i / (float)archivos.length) * 100 + "%");
                    timer = System.currentTimeMillis();
                }
            }
        }
        System.out.println("Guardando vocabulario con " + vocabulario.getVocabulario().size() + " palabras...");
        try {
            vocabulario.write();
        } catch (VocabularioIOException e) {
            e.printStackTrace();
        }

        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }

    public void agregarDocumento(File file) {

        Repositorio repo = new Repositorio();
        Documento doc = repo.getDocumentByName(file.getName());
        if (doc == null) {
            doc = repo.addDocumento(file.getName(), file.getAbsolutePath());
            System.out.println("docId " + doc.getId());

            final int docId = doc.getId();
            Hashtable<String, Integer> documentoParseado = vocabulario.agregarDocumento(file);
            ArrayList<Posteo> posteos = new ArrayList<>();
            documentoParseado.forEach((k, v) -> {
                posteos.add(new Posteo(docId, v, vocabulario.getVocabulario().get(k).getIndice()));
            });
            repo.addPosteos(posteos);
        } else {
            System.out.println(file.getName() + " ya agregado.");
        }




    }
}

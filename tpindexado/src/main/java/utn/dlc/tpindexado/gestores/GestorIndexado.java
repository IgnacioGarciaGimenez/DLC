package utn.dlc.tpindexado.gestores;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Posteo;
import utn.dlc.tpindexado.dominio.Vocabulario;
import utn.dlc.tpindexado.repositorio.RepositorioJPA;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.util.*;

@Named
@RequestScoped
public class GestorIndexado {

    private Vocabulario vocabulario;
    @Inject
    private IRepositorio repositorio;

    public GestorIndexado() {
        vocabulario = Vocabulario.getInstance();
    }


    public void indexar() {

        long generalTimer = System.currentTimeMillis();
        File carpeta = new File("C:\\DocumentosTP1");
        File[] archivos = carpeta.listFiles();
        int i = 0;
        for (File f : archivos) {
            this.agregarDocumento(f);
            System.out.println("Documento Id: " + ++i);
        };

        System.out.println("Guardando vocabulario con " + this.vocabulario.size() + " palabras...");


        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }

    public Documento agregarDocumento(File file) {

        Documento aux = repositorio.getDocumentoByName(file.getName());
        if (aux == null) {
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
            repositorio.addDocumento(doc);
            return doc;
        } else {
            System.out.println(file.getName() + " ya agregado.");
            return null;
        }




    }
}

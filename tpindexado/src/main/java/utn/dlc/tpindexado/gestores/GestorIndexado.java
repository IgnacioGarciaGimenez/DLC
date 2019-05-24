package utn.dlc.tpindexado.gestores;


import utn.dlc.tpindexado.dominio.Documento;
import utn.dlc.tpindexado.dominio.Posteo;
import utn.dlc.tpindexado.dominio.Vocabulario;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Named
@RequestScoped
public class GestorIndexado implements IGestorIndexado{

    private Vocabulario vocabulario;
    @Inject
    private IRepositorio repositorio;
    private static boolean indexando = false;

    public GestorIndexado() {
        vocabulario = Vocabulario.getInstance();
    }


    public void indexar() {
        if (!indexando){
            indexando = true;
            repositorio.llenarVocabulario(vocabulario);
            System.out.println("Cantidad de palabras en vocabulario: " + vocabulario.size());
            long generalTimer = System.currentTimeMillis();
            List<Documento> documentos = new ArrayList<>();
            File carpeta = new File("C:\\DocumentosTP1");
            File[] archivos = carpeta.listFiles();
            System.out.println("Inicio de Parseo y creacion de posteos de documentos");
            int i = 0;
            for (File f : archivos) {
                Documento d = this.agregarDocumento(f);
                if (d != null)
                    repositorio.addDocumentos(d);
                System.out.println("Parseando documento " + ++i);
            };
            System.out.println("Guardando vocabulario con " + this.vocabulario.size() + " palabras...");
            repositorio.updateVocabulario(vocabulario);
            System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");
            indexando = false;
        }


    }

    private Documento agregarDocumento(File file) {

        Documento aux = repositorio.getDocumentoByName(file.getName());
        if (aux == null) {
            final Documento doc = new Documento();
            doc.setRuta(file.getAbsolutePath());
            doc.setTitulo(file.getName());

            Hashtable<String, Integer> documentoParseado = vocabulario.agregarDocumento(file);
            ArrayList<Posteo> posteos = new ArrayList<>();
            documentoParseado.forEach((k, v) -> {
                Posteo p = new Posteo(v, vocabulario.get(k).getIndice());
                //p.setPalabra(k);
                p.setDocumento(doc);
                posteos.add(p);
            });

            doc.setPosteos(posteos);
            return doc;
        } else {
            System.out.println(file.getName() + " ya agregado.");
            return null;
        }
    }
}

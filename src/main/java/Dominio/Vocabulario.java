package Dominio;

import java.io.File;
import java.util.Hashtable;

public class Vocabulario {

    private Hashtable<String, Termino> vocabulario;
    private static Vocabulario instance = null;
    public String s = "";

    private Vocabulario() {
        this.vocabulario = new Hashtable<>();
    }

    public static Vocabulario getInstance() {
        if (instance == null)
            instance = new Vocabulario();
        return instance;
    }

    public void agregarDocumento(File file) {

        LectorDocumento lector = new LectorDocumento(file);

        Hashtable<String, Integer> documentoParseado = lector.procesarArchivo();
        Posteo posteo = Posteo.getInstance();
        posteo.indexarDocumento(documentoParseado, file.getAbsolutePath(), vocabulario);

        /* TODO: acá estaba el for que cargaba el vocabulario, pero lo incluí en el método indexarDocumento
        para aprovechar el for*/
    }



}

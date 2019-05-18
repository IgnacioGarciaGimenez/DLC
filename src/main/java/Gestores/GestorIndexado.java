package Gestores;

import Dominio.Termino;
import Dominio.Vocabulario;

import java.io.File;
import java.util.Hashtable;

public class GestorIndexado {

    private Vocabulario vocabulario;

    public GestorIndexado() {
        vocabulario = Vocabulario.getInstance();
    }

    public void indexar() {
        long generalTimer = System.currentTimeMillis();
        /*long timer = generalTimer;
        File carpeta = new File("C:\\DocumentosTP1");
        File[] archivos = carpeta.listFiles();
        if (archivos != null) {
            for (int i = 0; i < archivos.length; i++) {
                vocabulario.agregarDocumento(archivos[i]);

                if (System.currentTimeMillis() - timer > 2000) {
                    System.out.println("Indexando: " + i + " / " + archivos.length);
                    timer = System.currentTimeMillis();
                }
            }
        }*/

        vocabulario.agregarDocumento(new File("C:\\DocumentosTP1\\7linc11.txt"));
        vocabulario.guardarVocabulario();
        vocabulario.actuaizarReferenciaDePosteoAVocabulario();

        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }
}

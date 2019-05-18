package Gestores;

import Dominio.Vocabulario;

import java.io.File;

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
                vocabulario.agregarDocumento(archivos[i]);

                if (System.currentTimeMillis() - timer > 10000) {
                    System.out.println("Indexando: " + (i / (float)archivos.length) * 100 + "%");
                    timer = System.currentTimeMillis();
                }
            }
        }
        System.out.println("Guardando vocabulario!");
        vocabulario.guardarVocabulario();
        System.out.println("Actualizando posteos");
        //vocabulario.actuaizarReferenciaDePosteoAVocabulario();


        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }
}

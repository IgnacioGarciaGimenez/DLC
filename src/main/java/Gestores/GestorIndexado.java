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

                if (System.currentTimeMillis() - timer > 2000) {
                    System.out.println("Indexando: " + i + " / " + archivos.length);
                    timer = System.currentTimeMillis();
                }
            }
        }

        System.out.println("TIEMPO TOTAL: " + ((System.currentTimeMillis() - generalTimer) / 1000f) + " segundos.");

    }
}

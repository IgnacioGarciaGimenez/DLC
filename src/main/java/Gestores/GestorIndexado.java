package Gestores;

import Dominio.Vocabulario;

import java.io.File;

public class GestorIndexado {

    private Vocabulario vocabulario;

    public GestorIndexado() {
        vocabulario = Vocabulario.getInstance();
    }

    public static void main(String[] args) {
        GestorIndexado gestor = new GestorIndexado();
        gestor.indexar();
    }


    public void indexar() {
        File carpeta = new File("C:\\DocumentosTP1");
        System.out.println();
        File[] archivos = carpeta.listFiles();
        for (File archivo : archivos) {
            vocabulario.agregarDocumento(archivo);

        }


    }
}

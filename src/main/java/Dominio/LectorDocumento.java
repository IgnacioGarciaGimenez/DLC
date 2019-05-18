package Dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Scanner;

public class LectorDocumento {

    private Hashtable<String, Integer> palabrasArchivo;
    private File archivo;

    public LectorDocumento() {
        this.palabrasArchivo = new Hashtable<>();
    }

    public LectorDocumento(File archivo) {
        this.archivo = archivo;
        this.palabrasArchivo = new Hashtable<>();
    }

    public LectorDocumento(Hashtable<String, Integer> palabrasArchivo, File archivo) {
        this.palabrasArchivo = palabrasArchivo;
        this.archivo = archivo;
    }

    private void parsearArchivo() throws FileNotFoundException {
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader buff = new BufferedReader(fr);
            Scanner sc = new Scanner(buff);
            while (sc.hasNextLine()) {
                String oracion = sc.nextLine();
                for (String palabra : oracion.toLowerCase().replaceAll("[^A-Za-z']+", " ").split("\\s+")) {
                    if (palabra.equals("")) continue;
                    if (palabrasArchivo.containsKey(palabra)) {
                        int repeticiones = palabrasArchivo.get(palabra);
                        palabrasArchivo.put(palabra, ++repeticiones);
                    }
                    else {
                        palabrasArchivo.put(palabra, 1);
                    }
                }
            }

        }
        catch(Exception e) {
            throw e;
        }
    }

    public Hashtable<String, Integer> procesarArchivo() {
        if (this.archivo != null) {
            try {
                this.parsearArchivo();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this.palabrasArchivo;
    }
}

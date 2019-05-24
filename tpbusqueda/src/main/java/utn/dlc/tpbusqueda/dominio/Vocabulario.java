package utn.dlc.tpbusqueda.dominio;


import java.util.HashMap;

public class Vocabulario {

    private HashMap<String, Termino> vocabulario;
    private static Vocabulario instance = null;

    private Vocabulario() {
        vocabulario = new HashMap<>();
    }

    public static Vocabulario getInstance() {
        if (instance == null)
            instance = new Vocabulario();
        return instance;
    }

    public void vaciar() {
        vocabulario = new HashMap<>();
    }

    public HashMap<String, Termino> getVocabulario() { return vocabulario; }

    public void put(String key, Termino value) {
        vocabulario.put(key, value);
    }

    public Termino get(String key) {
        return this.vocabulario.get(key);
    }

    public int size() {
        return this.vocabulario.size();
    }




}

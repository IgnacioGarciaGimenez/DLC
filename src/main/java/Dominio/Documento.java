package Dominio;

public class Documento {

    private int id;
    private String titulo;
    private String ruta;

    public Documento() {

    }

    public Documento(int id, String titulo, String ruta) {
        this.id = id;
        this.titulo = titulo;
        this.ruta = ruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String nombre) {
        this.titulo = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}

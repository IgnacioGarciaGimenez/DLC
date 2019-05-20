package Dominio;

public class Posteo {

    private int id;
    private int documentoId;
    private int frecuencia;
    private int indice;
    private String palabra;

    public Posteo() {
    }

    public Posteo(int id, int documentoId, int frecuencia, int indice) {
        this.id = id;
        this.documentoId = documentoId;
        this.frecuencia = frecuencia;
        this.indice = indice;

    }

    public Posteo(int documentoId, int frecuencia, int indice) {
        this.documentoId = documentoId;
        this.frecuencia = frecuencia;
        this.indice = indice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(int documentoId) {
        this.documentoId = documentoId;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}

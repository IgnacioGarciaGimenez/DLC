package Dominio;

public class EntradaPosteo {

    private String rutaDocumento;
    private int apariciones;

    public EntradaPosteo() {
        this.rutaDocumento = "";
        this.apariciones = 0;
    }

    public EntradaPosteo(String rutaDocumento, int apariciones) {
        this.rutaDocumento = rutaDocumento;
        this.apariciones = apariciones;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public void setRutaDocumento(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

    public int getApariciones() {
        return apariciones;
    }

    public void setApariciones(int apariciones) {
        this.apariciones = apariciones;
    }
}

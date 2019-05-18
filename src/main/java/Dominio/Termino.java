package Dominio;

public class Termino {

    public static int PROV_INDICE = 0;

    private int cantDocumentos;
    private int maximaFrecuencia;
    private int indice;



    public Termino(int cantDocumentos, int maximaFrecuencia) {
        this.cantDocumentos = cantDocumentos;
        this.maximaFrecuencia = maximaFrecuencia;
        this.indice = ++PROV_INDICE;
    }

    public int getCantDocumentos() {
        return cantDocumentos;
    }

    public void setCantDocumentos(int cantDocumentos) {
        this.cantDocumentos = cantDocumentos;
    }

    public int getMaximaFrecuencia() {
        return maximaFrecuencia;
    }

    public void setMaximaFrecuencia(int maximaFrecuencia) {
        this.maximaFrecuencia = maximaFrecuencia;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}

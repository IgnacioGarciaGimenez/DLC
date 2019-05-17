package Dominio;

public class Termino {

    private int cantArchivos;
    private int maximaFrecuencia;
    private int indice;



    public Termino(int cantArchivos, int maximaFrecuencia) {
        this.cantArchivos = cantArchivos;
        this.maximaFrecuencia = maximaFrecuencia;
    }

    public int getCantArchivos() {
        return cantArchivos;
    }

    public void setCantArchivos(int cantArchivos) {
        this.cantArchivos = cantArchivos;
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

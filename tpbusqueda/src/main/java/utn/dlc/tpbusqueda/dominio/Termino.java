package utn.dlc.tpbusqueda.dominio;

import javax.persistence.*;


@Entity
@Table(name = "terminos")
public class Termino implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "vocabulario_ID")
    private int indice;
    @Column(name = "cantDocumentos")
    private int cantDocumentos;
    @Column(name = "maxFrecuencia")
    private int maximaFrecuencia;
    @Column(name = "termino")
    private String palabra;

    public Termino() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
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

    @Override
    public int compareTo(Object o) {
        Termino term = (Termino)o;
        if (term.getCantDocumentos() < this.cantDocumentos)
            return 1;
        else if (term.getCantDocumentos() > this.cantDocumentos)
            return -1;
        else
            return 0;
    }
}


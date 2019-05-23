package utn.dlc.tpbusqueda.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "documentos")
public class Documento implements Comparable{

    @Id
    @Column(name = "documento_ID")
    private int id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "ruta")
    private String ruta;
    private double peso;

    @OneToMany( mappedBy = "documento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posteo> posteos;

    public Documento() {
        this.peso = 0.0;
    }

    public Documento(String titulo, String ruta) {
        this.titulo = titulo;
        this.ruta = ruta;
        this.peso = 0.0;
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void calcularPeso(int n, int N, int tf)
    {
        peso += (double) tf * Math.log10((double) N / (double) n);
    }

    public List<Posteo> getPosteos() {
        return posteos;
    }

    public void setPosteos(List<Posteo> posteos) {
        this.posteos = posteos;
    }

    @Override
    public int compareTo(Object o) {
        Documento doc = (Documento)o;
        if (this.peso > doc.getPeso())
            return -1;
        else if (this.peso < doc.getPeso())
            return 1;
        else
            return 0;
    }
}

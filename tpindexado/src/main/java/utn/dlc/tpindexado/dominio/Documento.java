package utn.dlc.tpindexado.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documento_ID")
    private int id;
    @Column
    private String titulo;
    @Column
    private String ruta;

    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL)
    private List<Posteo> posteos;

    public Documento() {
    }

    public List<Posteo> getPosteos() {
        return posteos;
    }

    public void setPosteos(List<Posteo> posteos) {
        this.posteos = posteos;
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

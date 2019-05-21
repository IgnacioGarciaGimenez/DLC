package utn.dlc.tpindexado.dominio;

import javax.persistence.*;

@Entity
@Table(name = "Posteos")
public class Posteo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "frecuencia")
    private int frecuencia;
    @Column(name = "indice")
    private int indice;
    @Column(name = "documento_ID")
    private int documentoId;

    public Posteo() {
    }

    public Posteo(int frecuencia, int indice) {
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

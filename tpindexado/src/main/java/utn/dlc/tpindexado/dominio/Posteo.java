package utn.dlc.tpindexado.dominio;

import javax.persistence.*;

@Entity
@Table(name = "posteos")
public class Posteo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posteo_ID")
    private int id;
    @Column(name = "frecuencia")
    private int frecuencia;
    @Column(name = "vocabulario_ID")
    private int indice;
    @ManyToOne
    @JoinColumn(name = "documento_ID")
    private Documento documento;

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

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documentoId) {
        this.documento = documentoId;
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

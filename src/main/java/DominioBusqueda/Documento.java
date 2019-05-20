package DominioBusqueda;

public class Documento implements Comparable{

    private String nombre;
    private String ruta;
    private double peso;

    public Documento() {
        this.peso = 0.0;
    }

    public Documento(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.peso = 0.0;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

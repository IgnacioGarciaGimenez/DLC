package utn.dlc.tpindexado.vocabularioserializacion;


import Dominio.Vocabulario;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class VocabularioWriter
{

    private String arch = "vocabulario.dat";

    public VocabularioWriter()
    {

    }

    public VocabularioWriter(String nom)
    {
        arch = nom;
    }


    public void write (Vocabulario v) throws VocabularioIOException
    {
        try
        {
            FileOutputStream ostream = new FileOutputStream(arch);
            ObjectOutputStream p = new ObjectOutputStream(ostream);

            p.writeObject(v);

            p.flush();
            ostream.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}

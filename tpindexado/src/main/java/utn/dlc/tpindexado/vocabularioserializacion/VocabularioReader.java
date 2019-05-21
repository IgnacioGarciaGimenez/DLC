package utn.dlc.tpindexado.vocabularioserializacion;


import utn.dlc.tpindexado.dominio.Vocabulario;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class VocabularioReader
{
    private String arch = "vocabulario.dat";


    public VocabularioReader()
    {

    }


    public VocabularioReader(String nom)
    {
        arch = nom;
    }


    public Vocabulario read() throws VocabularioIOException
    {
        Vocabulario v = null;

        try
        {
            FileInputStream istream = new FileInputStream(arch);
            ObjectInputStream p = new ObjectInputStream(istream);

            v = (Vocabulario) p.readObject();

            p.close();
            istream.close();
        }
        catch (Exception e)
        {
            throw new VocabularioIOException("No se pudo recuperar la hashtable.");
        }

        return v;
    }
}

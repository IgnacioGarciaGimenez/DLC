package VocabularioSerializacion;


public class VocabularioIOException extends Exception
{

    private String message = "Problema al serializar la hashtable";


    public VocabularioIOException()
    {
    }

    public VocabularioIOException(String msg)
    {
        message = msg;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

}

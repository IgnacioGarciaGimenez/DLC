package utn.dlc.tpindexado.conexiones;

import utn.dlc.tpindexado.gestores.IHTTPRequestSender;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@RequestScoped
public class HTTPRequestSender implements IHTTPRequestSender {


    public HTTPRequestSender() {

    }
    public void sendPostRequest(String u)  {
        System.out.println("Enviando post");
        URL url = null;
        try {
            url = new URL(u);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.connect();
            con.getInputStream();
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package ejemplorestfulitemsclient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class EjemploRestfulItemsPost {

    public static void main(String[] args) {
        try {
            String strConnection = "http://localhost:8080/EjemploRestfulItems/RestItems";
            URL url = new URL(strConnection);
            URLConnection uc = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) uc;
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "text/xml");

            // Se va a realizar una petición con el método POST
            conn.setRequestMethod("POST");
                        
            // Se crea un nuevo objeto, con contenido aleatorio, que será el que
            //  se envíe al servidor
            Item item = new Item();
            Random random = new Random();
            item.setId(random.nextInt(1000));
            String cadenaAleatoria = "";
            for(int i=0; i<10; i++) {
                cadenaAleatoria += (char)('A' + random.nextInt(26));
            }
            item.setAstring(cadenaAleatoria);
            
            // Aunque sólo se va a enviar un único objeto, se utilizará un objeto
            //  de la clase Items que almacenará dicho objeto. Se va a hacer
            //  así para usar siempre la clase Items al generar los XML
            Items items = new Items();
            items.getItemsList().add(item);
            
            // Convertir objeto items a XML y preparar para enviar al servidor
            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(items, conn.getOutputStream());
            // Ejecutar la conexión y obtener la respuesta
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            
        } catch (JAXBException ex) {
            Logger.getLogger(EjemploRestfulItemsPost.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EjemploRestfulItemsPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

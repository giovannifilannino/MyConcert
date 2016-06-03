package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;




import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Artist;

/**
 * Created by Giovanni on 03/06/2016.
 */
public class XMLParser {
    // struttura dati che immagazzinerà i dati letti
    ArrayList<Artist> parsedData = new ArrayList<Artist>();

    // log degli eventuali errori
    static void eDebug(String debugString) {
        Log.e("DomParsing", debugString+"\n");
    }

    // metodo di accesso alla struttura dei dati
    public ArrayList<Artist> getParsedData() {
        return parsedData;
    }

    // metodo di parsing
    public void parseXml(String xmlUrl) {
        Document doc;
        try {
            // accedo al documento remoto
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(xmlUrl).openStream());

            // costruiamo il nostro documento a partire dallo stream dati
            Element root = doc.getDocumentElement();

            // prendo tutti i figli del nodo radice
            NodeList figli = root.getChildNodes();

            // ciclo i messaggi
            for (int i=0; i<figli.getLength(); i++){

                // singolo messaggio
                Node c = figli.item(i);

                // controllo se questo è un nodo elemento
                if (c.getNodeType() == Node.ELEMENT_NODE) {

                    // costruiamo un oggetto MyMsg dove andremo a salvare i dati
                    Artist newMsg = new Artist();

                    // cast da nodo a Elemento
                    Element msg = (Element)c;

                    // leggo attributo id
                    String name = msg.getAttribute("name");

                    // settiamo l'id del nostro oggetto MyMsg
                    newMsg.setName(name);

                    // recupero i figli del nodo messaggio
                    // cioè i vari dettagli del messaggio stesso
                    NodeList msgDetails = c.getChildNodes();

                    for (int j=0; j<msgDetails.getLength(); j++) {

                        // singolo valore del nodo msg
                        Node c1 = msgDetails.item(j);

                        // anche in questo caso controlliamo se si tratta di tag
                        if (c1.getNodeType() == Node.ELEMENT_NODE) {

                            // recupero nome e contenuto del nodo
                            Element detail = (Element)c1;
                            String nodeName = detail.getNodeName();
                            String nodeValue = detail.getFirstChild().getNodeValue();

                            // a seconda del nome del nodo settiamo il relativo valore nell'oggetto
                            if (nodeName.equals("disambiguation")) newMsg.setDisambiguation(nodeValue);
                            if (nodeName.equals("mbid")) newMsg.setMbid(nodeValue);
                            if (nodeName.equals("tmid")) newMsg.setTmid(Integer.valueOf(nodeValue));
                            if (nodeName.equals("sortName")) newMsg.setSortName(nodeValue);
                        }
                    }
                    parsedData.add(newMsg); //aggiungiamo il nostro oggetto all'arraylist
                }
            }

            // gestione delle eventuali eccezioni
        } catch (SAXException e) {
            eDebug(e.toString());
        } catch (IOException e) {
            eDebug(e.toString());
        } catch (ParserConfigurationException e) {
            eDebug(e.toString());
        } catch (FactoryConfigurationError e) {
            eDebug(e.toString());
        }
    }
}

package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ParserXML;

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

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Concert;

/**
 * Created by hp01 on 20/06/2016.
 */
public class XMLChosenConcertsParser{
    ArrayList<Concert> parsedData = new ArrayList<Concert>();

    public ArrayList<Concert> getParsedData(){
        return parsedData;
    }

    static void eDebug(String debugString){
        Log.e("DomParsing", debugString+"\n");
    }

    public void ChosenConcertparseXML(String xmlUrl){
        Document doc;
        try{

            doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(xmlUrl).openStream());
            Element root = doc.getDocumentElement();

            NodeList nodes = root.getChildNodes();

            for (int i=0; i<nodes.getLength(); i++){
                Node c = nodes.item(i);

                if(c.getNodeType()==Node.ELEMENT_NODE){
                    Concert concert = new Concert();

                    Element node = (Element) c;

                    String artista = node.getAttribute("artist");
                    concert.setArtista(artista);
                    String Data = node.getAttribute("date");
                    concert.setData(Data);
                    String Luogo = node.getAttribute("luogo");
                    concert.setLuogo(Luogo);
                    String Citta = node.getAttribute("citta");
                    concert.setCitta(Citta);

                    parsedData.add(concert);
                }
            }


        }catch (SAXException e) {
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


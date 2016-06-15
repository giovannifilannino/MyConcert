package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.ParserXML;

import android.app.Application;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Artist;


/**
 * Created by Giovanni on 04/06/2016.
 */
public class XMLArtistParser{
    ArrayList<Artist> parsedData = new ArrayList<Artist>();

    public ArrayList<Artist> getParsedData(){
        return parsedData;
    }

    static void eDebug(String debugString){
        Log.e("DomParsing", debugString+"\n");
    }

    public void parseXML(String xmlUrl){
        Document doc;
        try{

            doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(xmlUrl).openStream());
            Element root = doc.getDocumentElement();

            NodeList nodes = root.getChildNodes();

            for (int i=0; i<nodes.getLength(); i++){
                Node c = nodes.item(i);

                if(c.getNodeType()==Node.ELEMENT_NODE){
                    Artist artist = new Artist();

                    Element node = (Element) c;

                    String name = node.getAttribute("name");
                    String mbid = node.getAttribute("mbid");

                    artist.setName(name);
                    artist.setMbid(mbid);

                    parsedData.add(artist);
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



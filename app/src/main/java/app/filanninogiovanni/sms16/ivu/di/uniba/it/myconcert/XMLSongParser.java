package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Giovanni on 07/06/2016.
 */
public class XMLSongParser {

    ArrayList<String> songParsed = new ArrayList<String>();

    public void fetchSong(String url){


        Document doc;

        try{
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
            Element root = doc.getDocumentElement();

            NodeList nodeList = root.getChildNodes();

            for(int i=0; i<nodeList.getLength(); i++){
                Node nodo = nodeList.item(i);

                Element elemento = (Element) nodo;

                if(nodo.getNodeName().compareToIgnoreCase("sets")==0){
                    NodeList primifigli = nodo.getChildNodes();

                    for(int j=0; j<primifigli.getLength(); j++){
                        NodeList canzoni = nodo.getChildNodes();

                        for(int k=0; k<canzoni.getLength(); k++){
                            Node canzoneNodo = canzoni.item(k);

                            Element canzone = (Element) canzoneNodo;

                            songParsed.add(canzone.getAttribute("name"));
                        }
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

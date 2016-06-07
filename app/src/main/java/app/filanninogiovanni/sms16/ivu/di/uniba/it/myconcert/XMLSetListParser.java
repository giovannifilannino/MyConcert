package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert.Entities.Setlist;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class XMLSetListParser {


        ArrayList<Setlist> parderData = new ArrayList<Setlist>();

    public void parseXML(String url){
        Document doc;

        try{
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
            Element root = doc.getDocumentElement();
            Setlist setlist = new Setlist();
            NodeList nodes = root.getChildNodes();

            for(int i=0; i<nodes.getLength(); i++){
                Node c = nodes.item(i);

                if(c.getNodeType() == Node.ELEMENT_NODE){


                    Element node = (Element) c;
                    setlist.setDate(node.getAttribute("eventDate"));

                    NodeList c1 = c.getChildNodes();

                    for(int j=0; j<c1.getLength(); j++){

                        Node subnode = c1.item(j);
                        Element subelemen = (Element) subnode;
                        if(subnode.getNodeName().compareToIgnoreCase("artist")==0){
                            setlist.setArtistName(subelemen.getAttribute("name"));
                        } else if(subnode.getNodeName().compareToIgnoreCase("venue")==0){
                            setlist.setVenueName(subelemen.getAttribute("name"));
                            Node cityNode = subnode.getFirstChild();
                            Element cityElement = (Element) cityNode;
                            setlist.setCity(cityElement.getAttribute("name"));
                        }

                    }


                    parderData.add(setlist);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package app.filanninogiovanni.sms16.ivu.di.uniba.it.myconcert;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Giovanni on 06/06/2016.
 */
public class XMLVenueParser
{

    ArrayList<Venues> parderData = new ArrayList<Venues>();

    public void parseXML(String url){
        Document doc;

        try{
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new URL(url).openStream());
            Element root = doc.getDocumentElement();

            NodeList nodes = root.getChildNodes();

            for(int i=0; i<nodes.getLength(); i++){
                Node c = nodes.item(i);

                if(c.getNodeType() == Node.ELEMENT_NODE){
                    Venues venues = new Venues();

                    Element node = (Element) c;
                    venues.setName(node.getAttribute("name"));
                    venues.setId(node.getAttribute("id"));

                    parderData.add(venues);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package se.chalmers.phrasebook.backend;

import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by David on 2016-02-19.
 */
public class XMLParser {

    private XmlPullParser parser;
    private PhraseBook phraseBook;
    private DocumentBuilder documentBuilder;
    public XMLParser(){
        parser = Xml.newPullParser();
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }




    public NodeList getSentenceList(Document document, String sentenceTitle){
        NodeList result = null;
        NodeList nl = document.getElementsByTagName("sentence");
        for (int i = 0; i <nl.getLength(); i++) {
            String s = nl.item(i).getFirstChild().getNodeValue();
            s = trimNodeValue(s);
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE && sentenceTitle.equals(s)) {
                result = nl.item(i).getChildNodes();
            }
        }
        return result;
    }

    private String trimNodeValue(String s) {
        s = s.replaceAll("[\\s]","");//Trim the whitespace characters
        return s;
    }

    public SyntaxSentence parseToSentence(NodeList sentence){
        SyntaxSentence syntaxSentence;
        StaticSyntaxElement staticSyntaxElement;

        return null;
    }

    private StaticSyntaxElement parseSyntaxElement(NodeList nl){
        String nodeValue;
        String description;
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE ) {

                if(nl.item(i){//End node at list
                    return new StaticSyntaxElement("","",null,false,null);
                }else{
                    return new StaticSyntaxElement("","",null,false,null);
                }



            }
        }


        return null;
    }

    public String trim

    public String parseDOM(InputStream is){
        String result = "";
        try {
            Document xmlDom = documentBuilder.parse(is);
            result = getStringContent(xmlDom);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getStringContent(Document document){
        NodeList nl = getSentenceList(document,"QWhatName");
        String s = "";
        for (int i = 0; i <nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(nl.item(i).getNodeName()+" at level "+i+" and "+nl.item(i).getTextContent());
                //System.out.println(nl.item(i).getTextContent());
            }
                s += nl.item(i).getTextContent();
        }
        return s;
    }

//    public String parseSentence(InputStream is){
//        try {
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(is, null);
//            parser.nextTag();
//            return readFeed(parser).toString();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }
//
//    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
//        List entries = new ArrayList();
//        //parser.require(XmlPullParser.TEXT, null, "feed");
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            // Starts by looking for the entry tag
//            if (name.equals("entry")) {
//                entries.add(parser.getAttributeCount());
//            } else {
//                entries.add(parser.getName());
//            }
//        }
//        return entries;
//
//    }



}

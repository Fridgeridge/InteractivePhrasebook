package se.chalmers.phrasebook.backend;

import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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



    public String parseDOM(InputStream is){
        String result = "";
        try {
            Document xmlDom = documentBuilder.parse(is);
            result = getContent(xmlDom);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getContent(Document document){
        NodeList nl = document.getElementsByTagName("syntax");
        String s = "";

        for (int i = 0; i <nl.getLength(); i++) {
                s += nl.item(i).getTextContent();

        }

        return s;
    }

    public String parseSentence(InputStream is){
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            parser.nextTag();
            return readFeed(parser).toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        //parser.require(XmlPullParser.TEXT, null, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(parser.getAttributeCount());
            } else {
                entries.add(parser.getName());
            }
        }
        return entries;

    }



}

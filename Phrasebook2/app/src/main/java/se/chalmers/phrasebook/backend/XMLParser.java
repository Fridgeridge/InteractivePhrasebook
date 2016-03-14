package se.chalmers.phrasebook.backend;

import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
<<<<<<< HEAD:GF-master/src/ui/Phrasebook2/app/src/main/java/se/chalmers/phrasebook/backend/XMLParser.java
import java.util.ArrayList;
=======
import java.util.HashMap;
import java.util.Map;
>>>>>>> dev:Phrasebook2/app/src/main/java/se/chalmers/phrasebook/backend/XMLParser.java

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
    private Document document;

    public XMLParser(InputStream is) {
        parser = Xml.newPullParser();
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(is);
            is.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public NodeList getSentence(String id) {
        NodeList result = null;
        NodeList nl = document.getElementsByTagName("sentence");

        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Node attr = nl.item(i).getAttributes().getNamedItem("id");
                if (attr != null && attr.getNodeValue().equals(id))
                    result = nl.item(i).getChildNodes();
            }
        }
        return result;
    }

<<<<<<< HEAD:GF-master/src/ui/Phrasebook2/app/src/main/java/se/chalmers/phrasebook/backend/XMLParser.java
    public ArrayList<String> getAllSentencesTitles() {
        ArrayList<String> result;
=======
    public HashMap<String,String> getSentencesData() {
        String[] result;
        HashMap<String,String> sentenceMap = new HashMap<String,String>();

>>>>>>> dev:Phrasebook2/app/src/main/java/se/chalmers/phrasebook/backend/XMLParser.java
        NodeList sentences = document.getElementsByTagName("sentence");
        int nbrOfSentences = sentences.getLength();
        result = new ArrayList<>();

        for (int i = 0; i < nbrOfSentences; i++) {
<<<<<<< HEAD:GF-master/src/ui/Phrasebook2/app/src/main/java/se/chalmers/phrasebook/backend/XMLParser.java
            result.add(sentences.item(i).getAttributes().item(1).getNodeValue());
        }
=======
            String desc = sentences.item(i).getAttributes().getNamedItem("desc").getNodeValue();
            String id = sentences.item(i).getAttributes().getNamedItem("id").getNodeValue();
>>>>>>> dev:Phrasebook2/app/src/main/java/se/chalmers/phrasebook/backend/XMLParser.java

            if (desc != null & id != null)
                sentenceMap.put(id,desc);
        }
        return sentenceMap;
    }


    public SyntaxTree buildSyntaxTree(NodeList currentRoot) {
        return new SyntaxTree(constructSentence(currentRoot, new SyntaxNode("root")));
    }

    private SyntaxNode constructSentence(NodeList nl, SyntaxNode parent) {
        if (nl == null || nl.getLength() < 1)
            return parent;
        int length = nl.getLength();
        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && (nl.item(i).getNodeType() == Node.ELEMENT_NODE) && nl.item(i).getAttributes() != null) {
                String syntax = "", desc = "", question = "", option;
                NamedNodeMap attributes = nl.item(i).getAttributes();

                if (attributes.getNamedItem("syntax") != null) {
                    syntax = attributes.getNamedItem("syntax").getNodeValue();
                }

                if (attributes.getNamedItem("desc") != null) {
                    desc = attributes.getNamedItem("desc").getNodeValue();
                }


                if (attributes.getNamedItem("question") != null) {
                    question = attributes.getNamedItem("question").getNodeValue();
                }

                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();
                    SyntaxNode s = getChildLeaf(constructSentence(jumpToChild("child", option), parent));
                    if (nl.item(i).hasChildNodes())
                        constructSentence(nl.item(i).getChildNodes(), s);
                }
                if (!syntax.isEmpty()) {
                    SyntaxNode node = new SyntaxNode(syntax);
                    parent.addChild(node);
                    constructSentence(nl.item(i).getChildNodes(), node);//Do not return
                    return node;
                }


            }
        }
        return parent;
    }

    public NodeList jumpToChild(String tag, String id) {
        NodeList result = null;
        NodeList nl = document.getElementsByTagName(tag);

        for (int i = 0; i < nl.getLength(); i++) {
            String s = nl.item(i).getFirstChild().getNodeValue();
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE && nl.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(id)) {
                result = nl.item(i).getChildNodes();
            }
        }
        return result;
    }

    public SyntaxNode getChildLeaf(SyntaxNode parent) {
        while (parent != null && parent.getSelectedChild() != null)
            parent = parent.getSelectedChild();
        return parent;
    }


}


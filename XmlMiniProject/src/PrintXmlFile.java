import java.io.IOException;

import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PrintXmlFile {
	private static boolean setValidation = false;//defaults
	JTextArea txt;
	
	public PrintXmlFile() {
			Document doc = AppManager.getInstance().getDocument();
			txt = AppManager.getInstance().getContentView().getTexTArea();
			txt.setText("");
			traverse(doc.getDocumentElement(), "");
	}
	
	public void traverse (Node node, String indent) {
		
		if(node == null) return;
		
		int type = node.getNodeType();
		switch(type) {
		case Node.ELEMENT_NODE:
			txt.append(indent + "<" + node.getNodeName());
			if (node.hasAttributes()) {
				NamedNodeMap attr = node.getAttributes();
				for (int i = 0; i < attr.getLength(); i++) {
				txt.append(" " + attr.item(i).getNodeName() 
						+ "=\"" + attr.item(i).getNodeValue() + "\"");
				if (attr.item(i).getNodeValue().length() >= 20) txt.append("\n" + indent);
				}
			}
			if (node.getChildNodes().getLength() == 0 || node.getChildNodes().getLength() == 1) {
					txt.append(">");
			} else {
				txt.append(">\n");
			}
			break;
		case Node.COMMENT_NODE:
			txt.append(indent + "<!--");
			txt.append(node.getNodeValue() + "-->\n");
			break;
		case Node.TEXT_NODE:
			txt.append(node.getNodeValue());
			break;
		}
		
		NodeList children = node.getChildNodes();
		if(children != null) {
			int len = children.getLength();
			if (len == 1) 
				traverse(children.item(0),"");
			else
				for (int i=1;i<len;i = i+2)
					traverse(children.item(i), indent + "   ");
		}
		if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE) {
			if(node.getChildNodes().getLength() == 0 || node.getChildNodes().getLength() == 1) {
				txt.append("<" + node.getNodeName() + "/>\n");
			}
			else {
				txt.append(indent + "<" + node.getNodeName() + "/>\n");
			}
		}
			
			
	}

}

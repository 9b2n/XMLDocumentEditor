import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FindNodes {

	Document doc = AppManager.getInstance().getDocument();
	int _sequence;
	JTextArea txt;

	public FindNodes(String nodename, int nodetype, int sequence) {
		_sequence = sequence;
		txt = AppManager.getInstance().getContentView().getTexTArea();
		txt.setText("");
		if(nodename.equals(""))  return;
		XmlDocumentView _view = AppManager.getInstance().getView();
		switch (nodetype) {
		case ButtonConstants.ELEMENT:
			if(elementFind(doc, nodename, 0) == 0)
				_view.addMessageLabel(new JLabel("Doesn't exist."));
			break;
		case ButtonConstants.ATTRIBUTE:
			if(attributeFind(doc, nodename, 0) == 0)
				_view.addMessageLabel(new JLabel("Doesn't exist."));
			break;
		case ButtonConstants.TEXT:
			if(textFind(doc, nodename, 0) == 0)
				_view.addMessageLabel(new JLabel("Doesn't exist."));
			break;
		case ButtonConstants.COMMENT:
			if(commentFind(doc, nodename, 0) == 0)
				_view.addMessageLabel(new JLabel("Doesn't exist."));
			break;
		}
	}

	public int elementFind(Node node, String nodename, int exist) {
		if (node == null) return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeName().equals(nodename)) {
				if (exist == _sequence - 1) {
					AppManager.getInstance().setCurrentNode(child);
					AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode().getParentNode());
					AppManager.getInstance().setChildIndex(i);
					txt.append("Element Node: " + child.getNodeName());
				}
				exist++;
			}
			exist = elementFind(child, nodename, exist);
		}
		return exist;
	}

	public int attributeFind(Node node, String attrname, int exist) {
		if (node == null) return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			NamedNodeMap attrList = child.getAttributes();
			for (int j = 0; attrList != null && j < attrList.getLength(); j++) {
				Node attr = attrList.item(j);
				if (attr.getNodeName().equals(attrname)) {
					if (exist == _sequence - 1) {
						AppManager.getInstance().setCurrentNode(attr);
						AppManager.getInstance().setParentNode(child.getParentNode());
						txt.append("Attribute Node: " + attr.getNodeName() + "\n");
						txt.append("Attribute Value: " + attr.getNodeValue());
					}
					exist++;
				}
			}

			exist = attributeFind(child, attrname, exist);
		}
		return exist;
	}

	public int textFind(Node node, String nodevalue, int exist) {
		if (node == null) return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeValue() != null && child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().equals(nodevalue)) {
				if (exist == _sequence - 1) {
					AppManager.getInstance().setCurrentNode(child);
					AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode().getParentNode());
					AppManager.getInstance().setChildIndex(i);
					txt.append("Text Node: " + child.getNodeValue());
				}
				exist++;
			}
			exist = textFind(child, nodevalue, exist);
		}
		return exist;
	}
	
	public int commentFind(Node node, String cmtvalue, int exist) {
		if (node == null) return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeValue() != null && child.getNodeType() == Node.COMMENT_NODE && child.getNodeValue().equals(cmtvalue)) {
				if (exist == _sequence - 1) {
					AppManager.getInstance().setCurrentNode(child);
					AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode().getParentNode());
					AppManager.getInstance().setChildIndex(i);
					txt.append("Comment Node: " + child.getNodeValue());
				}
				exist++;
			}
			exist = commentFind(child, cmtvalue, exist);
		}
		return exist;
	}
}

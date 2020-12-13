import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeleteXmlFile {

	Document doc = AppManager.getInstance().getDocument();
	int _sequence, _childIndex;

	public DeleteXmlFile(String nodename, int nodetype) {
		if(nodename.equals(""))  return;
		switch (nodetype) {
		case ButtonConstants.ELEMENT:
			deleteElement();
			break;
		case ButtonConstants.ATTRIBUTE:
			deleteAttribute(nodename);
			break;
		case ButtonConstants.TEXT:
			deleteText();
			break;
		case ButtonConstants.COMMENT:
			deleteElement();
			break;
		}
	}

	public int elementFind(Node node, String nodename, int exist) {
		if (node == null)
			return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeName().equals(nodename)) {
				if (exist == _sequence - 1) {
					AppManager.getInstance().setCurrentNode(child.getParentNode());
					AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode().getParentNode());
					_childIndex = i;
				}
				exist++;
			}
			exist = elementFind(child, nodename, exist);
		}
		return exist;
	}

	public int attributeFind(Node node, String attrname, int exist) {
		if (node == null)
			return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			NamedNodeMap attrList = child.getAttributes();
			for (int j = 0; attrList != null && j < attrList.getLength(); j++) {
				Node attr = attrList.item(j);
				if (attr.getNodeName().equals(attrname)) {
					if (exist == _sequence - 1) {
						AppManager.getInstance().setCurrentNode(child);
						AppManager.getInstance().setParentNode(child.getParentNode());
					}
					exist++;
				}
			}

			exist = attributeFind(child, attrname, exist);
		}
		return exist;
	}

	public int textFind(Node node, String nodevalue, int exist) {
		if (node == null)
			return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeValue() != null && child.getNodeValue().equals(nodevalue)) {
				if (exist == _sequence - 1) {
					AppManager.getInstance().setCurrentNode(child.getParentNode());
					AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode().getParentNode());
					_childIndex = i;
				}
				exist++;
			}
			exist = textFind(child, nodevalue, exist);
		}
		return exist;
	}

	public void deleteElement() {
		Node node = AppManager.getInstance().getParentNode();
		NodeList children = node.getChildNodes();
		Node selected_Node = children.item(AppManager.getInstance().getChildIndex());
		node.removeChild(selected_Node);
		selected_Node = children.item(AppManager.getInstance().getChildIndex());
		node.removeChild(selected_Node);
		if (node.getChildNodes().getLength() == 1)
			node.removeChild(children.item(0));
	}

	public void deleteAttribute(String attrname) {
		Node node = ((Attr) AppManager.getInstance().getCurrentNode()).getOwnerElement();
		NamedNodeMap attrList = node.getAttributes();
		attrList.removeNamedItem(attrname);
	}
	
	public void deleteText() {
		Node node = AppManager.getInstance().getParentNode();
		Node selected_Node = node.getFirstChild();
		node.removeChild(selected_Node);
	}
}

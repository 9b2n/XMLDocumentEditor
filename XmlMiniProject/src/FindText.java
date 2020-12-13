import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FindText {
	
	JTextArea txt;

	public FindText(String txtValue) {
		Document doc = AppManager.getInstance().getDocument();
		txt = AppManager.getInstance().getContentView().getTexTArea();
		txt.setText("");
		if (nodeFind(doc, txtValue, 0) == 0) {
			AppManager.getInstance().getView().addMessageLabel(new JLabel("Dosen't Esixt."));
			AppManager.getInstance().getView().viewMessages();
		}
	}

	public int nodeFind(Node node, String txtValue, int exist) {
		if (node == null)
			return exist;

		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeValue() != null && child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().equals(txtValue)) {
				AppManager.getInstance().setCurrentNode(child);
				txt.append("Index[" + (exist + 1) + "]:	" + "[" + getDepth(child) + ", ");
				txt.append(getSiblingIndex(child) + "]");
				txt.append(child.getNodeValue() + "\n");
				exist++;
			}

			exist = nodeFind(child, txtValue, exist);
		}
		return exist;
	}

	public int getDepth(Node node) {
		int index = 0;
		while ((node = node.getParentNode()) != null)
			index++;
		return index;
	}

	protected int getSiblingIndex(Node node) {
		int index = 1;

		while ((node = node.getPreviousSibling()) != null)
			if (node.getNodeType() != Node.TEXT_NODE && node.getNodeType() != Node.COMMENT_NODE)
				index++;

		return index;
	}
}

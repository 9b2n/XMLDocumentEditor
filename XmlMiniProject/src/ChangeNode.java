import org.w3c.dom.Document;

public class ChangeNode {

	public ChangeNode(String nodeName) {
		Document doc = AppManager.getInstance().getDocument();
		doc.renameNode(AppManager.getInstance().getCurrentNode(), null, nodeName);
	}
}

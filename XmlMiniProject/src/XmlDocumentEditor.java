import javax.swing.JFrame;

public class XmlDocumentEditor extends JFrame{
	public XmlDocumentEditor() {
		setTitle("XML Document Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		XmlDocumentView base = new XmlDocumentView();
		XmlDocumentController controller = new XmlDocumentController();
		
		add(base);
		pack();
		setVisible(true);
	}
}

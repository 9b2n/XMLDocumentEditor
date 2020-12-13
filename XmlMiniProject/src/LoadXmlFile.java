import java.io.IOException;

import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LoadXmlFile {
	private static boolean setValidation = false;// defaults
	XmlDocumentView _view = AppManager.getInstance().getView();
	
	public LoadXmlFile(String uri) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			factory.setValidating(setValidation);
			Document doc = builder.parse(uri);

			AppManager.getInstance().setFactory(factory);
			AppManager.getInstance().setBuilder(builder);
			AppManager.getInstance().setDocument(doc);
			AppManager.getInstance().setCurrentNode(doc);
			AppManager.getInstance().setParentNode(null);

			_view.makeCompleteMessageLabel();
			_view.viewMessages();
			_view.setEditMode(ButtonConstants.LOAD);
			_view.txtInput.setText("");
			_view.setVisibleBasicOptionComponents(false);

		} catch (FactoryConfigurationError e) {
			// unable to get a document builder factory
			e.printStackTrace(System.err);
		} catch (ParserConfigurationException e) {
			// parser was unable to be configured
			e.printStackTrace(System.err);
		} catch (SAXException e) {
			// parsing error
			e.printStackTrace(System.err);
		} catch (IOException e) {
			// i/o error
			_view.addMessageLabel(new JLabel("The file doesn't exist."));
			_view.viewMessages();
			_view.setVisibleBasicOptionComponents(true);
		}
	}
}

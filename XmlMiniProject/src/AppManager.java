import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class AppManager {

	private static AppManager s_instance;
	private XmlDocumentView _view;
	private ContentView _contview;
	private Document _doc;
	private DocumentBuilderFactory _factory;
	private DocumentBuilder _builder;
	private String _fileName;
	private Node _currentNode, _parentNode;
	private int _childIndex;
	// 정리하기 //

	public int getChildIndex() { return _childIndex;	}
	public void setChildIndex(int childIndex) { this._childIndex = childIndex; }
	
	public Node getCurrentNode() { return _currentNode; }
	public void setCurrentNode(Node currentNode) { this._currentNode = currentNode; }
	
	public Node getParentNode() { return _parentNode; }
	public void setParentNode(Node parentNode) { this._parentNode = parentNode; }
	
	public String getFileName() { return _fileName; }
	public void setFileName(String fileName) { this._fileName = fileName; }
	
	public XmlDocumentView getView() { return _view; }
	public void setView(XmlDocumentView view) { _view = view; }
	
	public ContentView getContentView() { return _contview; }
	public void setContentView(ContentView contview) { _contview = contview; }	
	
	public Document getDocument() { return _doc; }	
	public void setDocument(Document doc) { _doc = doc; }	
	
	public DocumentBuilderFactory getFactory() { return _factory; }	
	public void setFactory(DocumentBuilderFactory factory) { _factory = factory; }	
	
	public DocumentBuilder getBuilder() { return _builder; }	
	public void setBuilder(DocumentBuilder builder) { _builder = builder; }
	public static AppManager getInstance() { // 이것도 스태틱으로 해줘야됌
		if (s_instance == null)
			s_instance = new AppManager();
		return s_instance;
	}
	
}

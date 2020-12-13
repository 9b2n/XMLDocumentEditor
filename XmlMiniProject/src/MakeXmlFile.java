import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class MakeXmlFile {
	public MakeXmlFile() {
		Document doc= new DocumentImpl();
		AppManager.getInstance().setDocument(doc);
		AppManager.getInstance().setCurrentNode(doc);
		AppManager.getInstance().setParentNode(null);
	}

	public void makeXmlFile(String filename) {
		Document doc = AppManager.getInstance().getDocument();
		
		try {
		OutputFormat format = new OutputFormat(doc);
		format.setEncoding("UTF-8");
		StringWriter stringOut = new StringWriter();
		XMLSerializer serial = new XMLSerializer(stringOut, format);
		serial.asDOMSerializer();
		serial.serialize(doc.getDocumentElement());
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringOut.toString());
		bw.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

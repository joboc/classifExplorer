import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class ActViewsData {
	static public Map<ActCode, Integer> getActViewsInfo()
	{
		if (entries == null)
			entries = loadActsViewsInfo();
		return entries;
	}
	static public void fillWithActs(Map<ActCode, ActContent> acts)
	{
		entries.clear();
		for (Map.Entry<ActCode, ActContent> actEntry : acts.entrySet())
		{
			int timesViewed = actEntry.getValue().getTimesViewed(); 
			if (timesViewed > 0)
			{
				entries.put(actEntry.getKey(), timesViewed);
			}
		}
	}
	static public void saveActViewsInfo()
	{
		try
		{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        Document xmlRequest = docBuilder.newDocument();

            Element actViewsDataNode = xmlRequest.createElement("actViewsData");
            xmlRequest.appendChild(actViewsDataNode);
	        for (Map.Entry<ActCode, Integer> entry : entries.entrySet())
	        {
	            Element actNode = xmlRequest.createElement("act");
	            actViewsDataNode.appendChild(actNode);
	            Element codeNode = xmlRequest.createElement("code");
	            actNode.appendChild(codeNode);
	            Text codeValue = xmlRequest.createTextNode(entry.getKey().getValue());
	            codeNode.appendChild(codeValue);
	            Element timesViewedNode = xmlRequest.createElement("timesViewed");
	            actNode.appendChild(timesViewedNode);
	            Text timesViewedValue = xmlRequest.createTextNode(entry.getValue().toString());
	            timesViewedNode.appendChild(timesViewedValue);

	        }
	        
	        TransformerFactory transfac = TransformerFactory.newInstance();
	        Transformer trans = transfac.newTransformer();
	        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
	        StringWriter sw = new StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(xmlRequest);
	        trans.transform(source, result);
	        String xmlString = sw.toString();
	        OutputStream actViewsFileOutput = FileManager.getActViewsFileOutput();
	        BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(actViewsFileOutput));
	        bufWriter.write(xmlString);
	        bufWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	static private Map<ActCode, Integer> loadActsViewsInfo()
	{
		try
		{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        InputStream actViewsFile = FileManager.getActViewsFileInput();
	        Map<ActCode, Integer> builtEntries = new TreeMap<ActCode, Integer>();
	        if (actViewsFile.available() > 0)
	        {
		        Document xmlResult = docBuilder.parse(actViewsFile);
		        Element actViewsDataElement = xmlResult.getDocumentElement();
		        NodeList actList = actViewsDataElement.getElementsByTagName("act");
		        if(actList != null && actList.getLength() > 0){
		        	for (int i = 0; i < actList.getLength(); ++i) {
		        		Element actElement = (Element) actList.item(i);
		        		
		        		NodeList actCodeElements = actElement.getElementsByTagName("code");
		        		String xmlCode = actCodeElements.item(0).getFirstChild().getNodeValue();
		        		NodeList actTimesViewedElements = actElement.getElementsByTagName("timesViewed");
		        		String xmlTimesViewed = actTimesViewedElements.item(0).getFirstChild().getNodeValue();
		        		
		        		builtEntries.put(new ActCode(xmlCode), Integer.parseInt(xmlTimesViewed));
		        	}
		        }
	        }
	        return builtEntries;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new TreeMap<ActCode, Integer>();
		}
	}
	static private Map<ActCode, Integer> entries;
	private ActViewsData(){}
}

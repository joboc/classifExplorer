import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

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


public class AccountingData {
	static public AccountingInfo getAccountingInfo()
	{
		if (m_accountingInfo == null)
			m_accountingInfo = loadAccountingInfo();
		return m_accountingInfo;
	}
	static public void fillWithAccountingInfo(AccountingInfo accountingInfo)
	{
		m_accountingInfo = new AccountingInfo(accountingInfo);
	}
	static public void save()
	{
		try
		{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        Document xmlRequest = docBuilder.newDocument();

            Element actViewsDataNode = xmlRequest.createElement("accountingData");
            actViewsDataNode.setAttribute("version", "1.0");
            xmlRequest.appendChild(actViewsDataNode);
            Element percentStructureNode = xmlRequest.createElement("percentStructure");
            actViewsDataNode.appendChild(percentStructureNode);
            Text percentStructureValue = xmlRequest.createTextNode(Double.valueOf(m_accountingInfo.getPourcentageStructure()).toString());
            percentStructureNode.appendChild(percentStructureValue);
	        
	        TransformerFactory transfac = TransformerFactory.newInstance();
	        Transformer trans = transfac.newTransformer();
	        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");
	        StringWriter sw = new StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(xmlRequest);
	        trans.transform(source, result);
	        String xmlString = sw.toString();
	        OutputStream accountingFileOutput = FileManager.getAccountingFileOutput();
	        BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(accountingFileOutput));
	        bufWriter.write(xmlString);
	        bufWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	static private AccountingInfo loadAccountingInfo()
	{
		try
		{
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        InputStream accountingFile = FileManager.getAccountingFileInput();
	        double percentStructure = 0.;
	        if (accountingFile.available() > 0)
	        {
		        Document xmlResult = docBuilder.parse(accountingFile);
		        Element actViewsDataElement = xmlResult.getDocumentElement();
		        NodeList percentStructureElements = actViewsDataElement.getElementsByTagName("percentStructure");
        		String xmlPercentStructure = percentStructureElements.item(0).getFirstChild().getNodeValue();
        		percentStructure = Double.parseDouble(xmlPercentStructure);
	        }
	        return new AccountingInfo(percentStructure);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new AccountingInfo();
		}
	}
	static private AccountingInfo m_accountingInfo;
	private AccountingData(){}
}

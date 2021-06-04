package CovidTracker.db.ifaces;


import javax.xml.bind.JAXBException;


public interface JaxbManager {
	public void java2XmlPAT(String filename) throws Exception;// change a patient from java to Xml
	public void java2XmlDOC(String filename) throws Exception;// change a doctor from java to Xml
	public void simpleTransform(String sourcePath, String xsltPath,String resultDir); //Create an html
	public void xml2JavaPAT(String filename) throws JAXBException;// change a patient from Xml to java
	public void xml2JavaDOC(String filename) throws JAXBException;// change a doctor from Xml to java
}

package CovidTracker.db.ifaces;

import java.io.File;

import javax.xml.bind.JAXBException;

import CovidTracker.db.jaxb.*;

public interface JaxbManager {
	public void java2XmlPAT(String filename) throws Exception;
	public void java2XmlDOC(String filename) throws Exception;
	public void simpleTransform(String sourcePath, String xsltPath,String resultDir);
	public void transformPat(String filename);
	public void transformDoc(String filename);
	public void xml2JavaPAT(String filename) throws JAXBException;
	public void xml2JavaDOC(String filename) throws JAXBException;
}

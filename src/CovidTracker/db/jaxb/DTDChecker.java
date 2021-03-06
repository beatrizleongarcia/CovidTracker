package CovidTracker.db.jaxb;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import CovidTracker.db.xml.utils.CustomErrorHandler;

public class DTDChecker {

    public void Checker(String filename) {
    	File file = new File("./files/"+filename+".xml");
        try {
        	// Create a DocumentBuilderFactory
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            // Set it up so it validates XML documents
            dBF.setValidating(true);
            // Create a DocumentBuilder and an ErrorHandler (to check validity)
            DocumentBuilder builder = dBF.newDocumentBuilder();
            CustomErrorHandler customErrorHandler = new CustomErrorHandler();
            builder.setErrorHandler(customErrorHandler);
            // Parse the XML file and print out the result
            Document doc = builder.parse(file);
            if (customErrorHandler.isValid()) {
                System.out.println(file + " was valid!");
            }
        } catch (ParserConfigurationException ex) {
            System.out.println(file + " error while parsing!");
        } catch (SAXException ex) {
            System.out.println(file + " was not well-formed!");
        } catch (IOException ex) {
            System.out.println(file + " was not accesible!");
        }

    }
}

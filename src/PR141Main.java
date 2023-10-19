import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PR141Main {
    public static void main(String[] args) {
        try {
            // Crea una factoria de constructors de documents
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // Crea un constructor de documents
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Crea un nou document XML
            Document doc = db.newDocument();
            // Crea l'element root del document XML
            Element elmRoot = doc.createElement("biblioteca");
            // Afegeix l'element root al document XML
            doc.appendChild(elmRoot);
            // Crea l'element "to"
            Element elmLlibre = doc.createElement("llibre");
            // Afegeix l'element "to" a l'element root
            elmRoot.appendChild(elmLlibre);
            // Crea un atribut "id"
            Attr attrId = doc.createAttribute("id");
            // Estableix el valor de l'atribut "id"
            attrId.setValue("001");
            // Afegeix l'atribut "id" a l'element "to"
            elmLlibre.setAttributeNode(attrId);
            Element elmTitol = doc.createElement("titol");
            Text nodeTextTitol = doc.createTextNode("El viatge dels venturons");
            elmTitol.appendChild(nodeTextTitol);
            elmLlibre.appendChild(elmTitol);

            Element elmAutor = doc.createElement("autor");
            Text nodeTextAutor = doc.createTextNode("Joan Pla");
            elmAutor.appendChild(nodeTextAutor);
            elmLlibre.appendChild(elmAutor);

            Element elmAnyPublicacio = doc.createElement("anyPublicacio");
            Text nodeTextAnyPublicacio = doc.createTextNode("1998");
            elmAnyPublicacio.appendChild(nodeTextAnyPublicacio);
            elmLlibre.appendChild(elmAnyPublicacio);

            Element elmEditorial = doc.createElement("editorial");
            Text nodeTextEditorial = doc.createTextNode("Edicions Mar");
            elmEditorial.appendChild(nodeTextEditorial);
            elmLlibre.appendChild(elmEditorial);

            Element elmGenere = doc.createElement("genere");
            Text nodeTextGenere = doc.createTextNode("Aventura");
            elmGenere.appendChild(nodeTextGenere);
            elmLlibre.appendChild(elmGenere);

            Element elmPagines = doc.createElement("pagines");
            Text nodeTextPagines = doc.createTextNode("320");
            elmPagines.appendChild(nodeTextPagines);
            elmLlibre.appendChild(elmPagines);

            Element elmDisponible = doc.createElement("disponible");
            Text nodeTextDisponible = doc.createTextNode("true");
            elmDisponible.appendChild(nodeTextDisponible);
            elmLlibre.appendChild(elmDisponible);

            write("src/bibliotecta.xml", doc);
        } catch (Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();
        }

    }

    static public void write(String path, Document doc) throws TransformerException, IOException {
        if (!new File(path).exists()) {
            new File(path).createNewFile();
        }
        // Crea una factoria de transformadors XSLT
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        // Crea un transformador XSLT
        Transformer transformer = transformerFactory.newTransformer();
        // Estableix la propietat OMIT_XML_DECLARATION a "no" per no ometre la
        // declaració XML del document XML resultant
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        // Estableix la propietat INDENT a "yes" per indentar el document XML resultant
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Elimina els espais en blanc innecessaris del document XML. Implementació
        // pròpia

        // Crea una instància de DOMSource a partir del document XML
        DOMSource source = new DOMSource(doc);
        // Crea una instància de StreamResult a partir del camí del fitxer XML
        StreamResult result = new StreamResult(new File(path));
        // Transforma el document XML especificat per source i escriu el document XML
        // resultant a l'objecte especificat per result
        transformer.transform(source, result);
    }

}

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR140Main {
    public static void main(String[] args) {
        // Crea una factoria de constructors de documents
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Crea un constructor de documents
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Analitza el fitxer XML
            Document doc = dBuilder.parse("src/persones.xml");

            // Normalitza l'element arrel del document
            doc.getDocumentElement().normalize();

            // Obté una llista de tots els elements "student" del document
            NodeList listPersones = doc.getElementsByTagName("persona");

            // Imprimeix el número d'estudiants
            System.out.println("Número d'estudiants: " + listPersones.getLength());
            System.out.println(String.format("%-14s %-14s %-5s %-14s", "Nombre", "Apellido", "Edad", "Ciudad"));

            for (int cnt = 0; cnt < listPersones.getLength(); cnt++) {
                // Obté l'estudiant actual
                Node nodePersona = listPersones.item(cnt);
                // Comprova si l'estudiant actual és un element
                if (nodePersona.getNodeType() == Node.ELEMENT_NODE) {
                    // Converteix l'estudiant actual a un element
                    Element elm = (Element) nodePersona;
                    // **Obté el nom de l'estudiant**
                    NodeList nodeList = elm.getElementsByTagName("nom");
                    String nom = nodeList.item(0).getTextContent();
                    String apellido = elm.getElementsByTagName("cognom").item(0).getTextContent();
                    String edad = elm.getElementsByTagName("edat").item(0).getTextContent();
                    String ciudad = elm.getElementsByTagName("ciutat").item(0).getTextContent();

                    System.out.println(String.format("%-14s %-14s %-5s %-14s", nom, apellido, edad, ciudad));
                }
            }

        } catch (Exception e) {
            // Imprimeix la pila d'errors en cas d'excepció
            e.printStackTrace();
        }

    }
}

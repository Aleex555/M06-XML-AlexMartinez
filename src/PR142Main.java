import java.io.File;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;

public class PR142Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {

        String curso;
        String nombre;
        boolean running = true;
        while (running) {
            String menu = "Escull una opció:";
            menu = menu + "\n 0) ListarCursosTutoresAlumnos";
            menu = menu + "\n 1) MostrarModulosPorCurso";
            menu = menu + "\n 2) MostrarAlumnosPorCurso";
            menu = menu + "\n 3) AgregarAlumnoACurso";
            menu = menu + "\n 4) EliminarAlumnoDeCurso";
            menu = menu + "\n 10) Salir";
            System.out.println(menu);


            int opcio = Integer.valueOf(llegirLinia("Opció:"));
            try {
                switch (opcio) {
                case 0:  listarCursosTutoresAlumnos();break;

                case 1: System.out.print("Introduce un curso: ");
                curso = in.nextLine();
                 mostrarModulosPorCurso(curso);
                        break;
                case 2: System.out.print("Introduce un curso: ");
                curso = in.nextLine(); 
                mostrarAlumnosPorCurso(curso);break;
                case 3:  System.out.print("Introduce un curso: ");
                curso = in.nextLine(); 
                System.out.print("Introduce el nombre del Alumno: ");
                nombre = in.nextLine(); 
                agregarAlumnoACurso(curso,nombre);break;
                case 4:  System.out.print("Introduce un curso: ");
                curso = in.nextLine(); 
                System.out.print("Introduce el nombre del Alumno: ");
                nombre = in.nextLine(); 
                eliminarAlumnoDeCurso(curso,nombre);break;
                case 10: running = false; break;
                default: break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        in.close();
    }

    static public String llegirLinia (String text) {
        System.out.print(text);
        return in.nextLine();
      }
     
      static void listarCursosTutoresAlumnos() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
    
            String cursoExpression = "/cursos/curs";
            NodeList cursos = (NodeList) xPath.compile(cursoExpression).evaluate(doc, XPathConstants.NODESET);
    
            for (int i = 0; i < cursos.getLength(); i++) {
                Element curso = (Element) cursos.item(i);
                String cursoId = curso.getAttribute("id");
                
                String tutorExpression = "tutor";
                NodeList tutores = (NodeList) xPath.compile(tutorExpression).evaluate(curso, XPathConstants.NODESET);
    
                String totalAlumnosExpression = "count(alumnes/alumne)";
                Double totalAlumnos = (Double) xPath.compile(totalAlumnosExpression).evaluate(curso, XPathConstants.NUMBER);
    
                System.out.println("Curso ID: " + cursoId);
                
                for (int j = 0; j < tutores.getLength(); j++) {
                    Node tutorNode = tutores.item(j);
                    String tutorNombre = tutorNode.getTextContent();
                    System.out.println("Tutor: " + tutorNombre);
                }
                
                System.out.println("Total de Alumnos: " + totalAlumnos.intValue());
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void mostrarModulosPorCurso(String cursoId) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
    
            String expression = "/cursos/curs[@id='" + cursoId + "']/moduls/modul";
            NodeList modulos = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
    
            for (int i = 0; i < modulos.getLength(); i++) {
                Element modulo = (Element) modulos.item(i);
                String moduloId = modulo.getAttribute("id");
                String moduloTitulo = modulo.getElementsByTagName("titol").item(0).getTextContent();
                System.out.println("Módulo ID: " + moduloId);
                System.out.println("Título: " + moduloTitulo);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void mostrarAlumnosPorCurso(String cursoId) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
    
            String expression = "/cursos/curs/alumnes/alumne";
            NodeList modulos = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            System.out.println("Alumnos del curso "+cursoId+":");
            for (int i = 0; i < modulos.getLength(); i++) {
                Element alumno = (Element) modulos.item(i);
                String nombre = alumno.getTextContent();
                System.out.println("Alumno: " + nombre);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    static void agregarAlumnoACurso(String cursoId, String nombreAlumno) {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse("src/cursos.xml");
        XPath xPath = XPathFactory.newInstance().newXPath();

        // Encuentra el curso deseado por su ID
        String cursoExpression = "/cursos/curs[@id='" + cursoId + "']";
        Node cursoNode = (Node) xPath.compile(cursoExpression).evaluate(doc, XPathConstants.NODE);

        if (cursoNode != null) {
            // Crea un nuevo elemento <alumne> con el nombre del alumno
            Element nuevoAlumno = doc.createElement("alumne");
            Text nodeTextAlumno = doc.createTextNode(nombreAlumno);
            nuevoAlumno.appendChild(nodeTextAlumno);

            // Encuentra el elemento <alumnes> del curso
            Element alumnes = (Element) ((Element) cursoNode).getElementsByTagName("alumnes").item(0);

            // Agrega el nuevo elemento <alumne> al elemento <alumnes>
            alumnes.appendChild(nuevoAlumno);

            // Guarda el documento XML modificado
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/cursos.xml"));
            transformer.transform(source, result);

            System.out.println("Alumno '" + nombreAlumno + "' agregado al curso '" + cursoId + "'.");
        } else {
            System.out.println("Curso con ID '" + cursoId + "' no encontrado.");
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    }

    static void eliminarAlumnoDeCurso(String cursoId, String nombreAlumno) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/cursos.xml");
            XPath xPath = XPathFactory.newInstance().newXPath();
    
            String cursoExpression = "/cursos/curs[@id='" + cursoId + "']";
            Node cursoNode = (Node) xPath.compile(cursoExpression).evaluate(doc, XPathConstants.NODE);
    
            if (cursoNode != null) {
                Element alumnes = (Element) ((Element) cursoNode).getElementsByTagName("alumnes").item(0);
                NodeList alumnosList = alumnes.getElementsByTagName("alumne");
                for (int i = 0; i < alumnosList.getLength(); i++) {
                    Element alumnoElement = (Element) alumnosList.item(i);
                    if (alumnoElement.getTextContent().equals(nombreAlumno)) {
                        alumnes.removeChild(alumnoElement);
                        System.out.println("Alumno '" + nombreAlumno + "' eliminado del curso '" + cursoId + "'.");
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File("src/cursos.xml"));
                        transformer.transform(source, result);
                        return;
                    }
                }
                    System.out.println("Alumno '" + nombreAlumno + "' no encontrado en el curso '" + cursoId + "'.");
            } else {
                System.out.println("Curso con ID '" + cursoId + "' no encontrado.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

    
}

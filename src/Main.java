import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main {

  public static void main(String[] args) {
    boolean flag = true;
    Scanner scanner = new Scanner(System.in);
    while (flag) {
      printMenu();
      switch (scanner.nextInt()) {
        case 1:
          ParserDOM parserDOM = new ParserDOM();
          parserDOM.parseFile("Famous people.xml");
          break;
        case 2:
          XsdValidator xsdValidator = new XsdValidator();
          xsdValidator.validateXMLSchema("validator.xml", "Famous people.xml");
          break;
        case 3:ParserDOM parserDOM1 = new ParserDOM();
          try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("Famous people.xml");
            parserDOM1.addFamous(document);

          } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
          } catch (SAXException ex) {
            ex.printStackTrace(System.out);
          } catch (IOException ex) {
            ex.printStackTrace(System.out);
          }
          break;
        case 4:
          try {
            ParserDOM parserDOM2 = new ParserDOM();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
            Document document = documentBuilder.parse("Famous people.xml");
            parserDOM2.deleteFamous(document);
          } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
          } catch (SAXException ex) {
            ex.printStackTrace(System.out);
          } catch (IOException ex) {
            ex.printStackTrace(System.out);
          }
          break;
        case 5:
          try {
            ParserDOM parserDOM2 = new ParserDOM();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
            Document document = documentBuilder.parse("Famous people.xml");
            parserDOM2.getFamousBySex(document);
          } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
          } catch (SAXException ex) {
            ex.printStackTrace(System.out);
          } catch (IOException ex) {
            ex.printStackTrace(System.out);
          }
          break;
        case 0:
          flag = false;
          break;
        default:
          System.out.println("Wrong input! Try enter 1...5, or 0!");
      }
    }
  }

  private static void printMenu(){
    System.out.println("==>> 1 - Get file info");
    System.out.println("==>> 2 - Validate file");
    System.out.println("==>> 3 - Add famous person");
    System.out.println("==>> 4 - Delete famous person");
    System.out.println("==>> 5 - Get famous by gender");
    System.out.println("==>> 0 - Exit");
  }

}

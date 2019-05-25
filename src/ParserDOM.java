
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserDOM {
  Scanner scanner = new Scanner(System.in);

  public void parseFile(String fileName) {
    try {
      // Создается построитель документа
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      // Создается дерево DOM документа из файла
      Document document = documentBuilder.parse(fileName);

      // Получаем корневой элемент
      Node root = document.getDocumentElement();

      System.out.println("==>>List of famous people:");
      System.out.println();
      // Просматриваем все подэлементы корневого - т.е. бытовую технику
      NodeList people = root.getChildNodes();
      for (int i = 0; i < people.getLength(); i++) {
        Node famous = people.item(i);
        // Если нода не текст, то это люди - заходим внутрь
        if (famous.getNodeType() != Node.TEXT_NODE) {
          NodeList peopleProps = famous.getChildNodes();
          for(int j = 0; j < peopleProps.getLength(); j++) {
            Node peopleProp = peopleProps.item(j);
            // Если нода не текст, то это один из параметров люди - печатаем
            if (peopleProp.getNodeType() != Node.TEXT_NODE) {
              System.out.println(peopleProp.getNodeName() + ":" + peopleProp.getChildNodes().item(0).getTextContent());
            }
          }
          System.out.println("====================");
        }
      }

    } catch (ParserConfigurationException ex) {
      ex.printStackTrace(System.out);
    } catch (SAXException ex) {
      ex.printStackTrace(System.out);
    } catch (IOException ex) {
      ex.printStackTrace(System.out);
    }
  }

  public void addFamous(Document document) throws TransformerFactoryConfigurationError, DOMException {
    // Получаем корневой элемент
    Node root = document.getDocumentElement();

    // Создаем new people по элементам
    Element people = document.createElement("People");
    // <LastName>
    Element LastName = document.createElement("LastName");
    // Устанавливаем значение текста внутри тега
    System.out.println("Enter last name: ");
    String name = scanner.nextLine();
    LastName.setTextContent(name);
    // <Age>
    Element Age = document.createElement("Age");
    System.out.println("Enter age: ");
    String amountOfAge = scanner.nextLine();
    Age.setTextContent(amountOfAge);
    System.out.println("Enter sex: ");
    String sex = scanner.nextLine();
    Age.setAttribute("gender",sex);
    // <Role>
    Element Role = document.createElement("Role");
    System.out.println("Enter role: ");
    String role = scanner.nextLine();
    Role.setTextContent(role);
    // <Wage>
    Element Wage = document.createElement("Wage");
    System.out.println("Enter year wage: ");
    String wage = scanner.nextLine();
    Wage.setTextContent(wage);
    // <CountryOfBorn>
    Element countryOfBorn = document.createElement("CountryOfBorn");
    System.out.println("Enter country of born: ");
    String country= scanner.nextLine();
    countryOfBorn.setTextContent(country);


    people.appendChild(LastName);
    people.appendChild(Age);
    people.appendChild(Role);
    people.appendChild(Wage);
    people.appendChild(countryOfBorn);
    // Добавляем people в корневой элемент
    root.appendChild(people);

    // Записываем XML в файл
    writeDocument(document);
  }

  public void deleteFamous(Document document) {
    System.out.println("Enter last name:");
    String personName = scanner.nextLine();
    NodeList people = document.getElementsByTagName("People");
    for (int i = 0; i < people.getLength(); i++) {
      Element famous = (Element)people.item(i);
      // <LastName>
      Element lastName = (Element)famous.getElementsByTagName("LastName").item(0);
      String pName = lastName.getTextContent();
      if (pName.equals(personName)) {
        famous.getParentNode().removeChild(famous);
      }
      writeDocument(document);
    }
  }

  // Функция для сохранения DOM в файл
  public void writeDocument(Document document) throws TransformerFactoryConfigurationError {
    try {
      Transformer tr = TransformerFactory.newInstance().newTransformer();
      DOMSource source = new DOMSource(document);
      FileOutputStream fos = new FileOutputStream("Famous people.xml");
      StreamResult result = new StreamResult(fos);
      tr.transform(source, result);
    } catch (TransformerException | IOException e) {
      e.printStackTrace(System.out);
    }
  }

  public void getFamousBySex(Document document){
    System.out.println("Enter sex: ");
    String sex = scanner.nextLine();
    System.out.println("List of people with '"+sex+"' gender:\n");
    NodeList people = document.getElementsByTagName("People");
    for (int i = 0; i < people.getLength(); i++) {
      Element famous = (Element)people.item(i);
      // <Age>
      Element age = (Element)famous.getElementsByTagName("Age").item(0);
      String genderAttribute = age.getAttribute("gender");
      if(genderAttribute.equals(sex)){

        NodeList props = famous.getChildNodes();
        for(int k=0; k<props.getLength();k++){
          Node prop = props.item(k);
          if (prop.getNodeType() != Node.TEXT_NODE) {
            System.out.println(prop.getNodeName() + ":" + prop.getTextContent());
          }
        }
        System.out.println("====================");

      }
      }
  }
}
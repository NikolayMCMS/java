package org.example;
import javax.swing.*;
import java.awt.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;
import org.w3c.dom.Element;
import javax.swing.JPanel;


public class Main extends JFrame{

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Main() {
        setTitle("Two Page Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        try {
            JPanel page1Panel = loadPageFromXML("xml/Page1.xml");
            JPanel page2Panel = loadPageFromXML("xml/Page2.xml");

            cardPanel.add(page1Panel, "Page 1");
            cardPanel.add(page2Panel, "Page 2");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        getContentPane().add(cardPanel);
        setVisible(true);
    }

    // Метод загрузки содержимого страницы из XML-файла
    private JPanel loadPageFromXML(String filePath) {
        try {
            // Загружаем и разбираем XML файл
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));

            // Создаем панель, на которую будем добавлять компоненты
            JPanel pagePanel = new JPanel(new BorderLayout());




            // Получаем список всех элементов <input> в документе
            //NodeList inputNodes = doc.getElementsByTagName("input");

            //for (int i = 0; i < inputNodes.getLength(); i++) {
                // Создаем текстовое поле JTextField вместо элементов <input>
           //     JTextField textField = new JTextField();
            //    pagePanel.add(textField, BorderLayout.NORTH);
           // }


            // Получаем список всех элементов <input> в документе
            NodeList inputNodes = doc.getElementsByTagName("input");
            for (int i = 0; i < inputNodes.getLength(); i++) {
                Node inputNode = inputNodes.item(i);
                if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element inputElement = (Element) inputNode;
                    int width = Integer.parseInt(inputElement.getAttribute("width"));
                    int height = Integer.parseInt(inputElement.getAttribute("height"));
                    int x = Integer.parseInt(inputElement.getAttribute("x"));
                    int y = Integer.parseInt(inputElement.getAttribute("y"));

                    //JTextField inputField = new JTextField();
                    JTextField inputField = new JTextField();
                    inputField.setBounds(x, y, width, height);
                    //inputPanel.add(inputField);
                    pagePanel.add(inputField);
                    //     JTextField textField = new JTextField();
                    //    pagePanel.add(textField, BorderLayout.NORTH)
                }
            }


            // Получаем текст из тега "label" и создаем JLabel с этим текстом
            // String labelText = doc.getElementsByTagName("label").item(0).getTextContent();
           // JLabel label = new JLabel(labelText);
           // pagePanel.add(label, BorderLayout.NORTH);

            // Создание панели для размещения компонентов


            // Получение списка всех элементов <label> в документе
            // Получение списка всех элементов с тегом "label"
            // Получение списка всех элементов <label> в документе

            // Получение всех элементов с тегом "label"
            NodeList labelNodes = doc.getElementsByTagName("label");

            // Перебор элементов и вывод их содержимого
            for (int i = 0; i < labelNodes.getLength(); i++) {
                Node labelNode = labelNodes.item(i);
                if (labelNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element labelElement = (Element) labelNode;
                    String labelText = labelElement.getTextContent();
                    int x = Integer.parseInt(labelElement.getAttribute("x"));
                    int y = Integer.parseInt(labelElement.getAttribute("y"));
                    int width = Integer.parseInt(labelElement.getAttribute("width"));
                    int height = Integer.parseInt(labelElement.getAttribute("height"));
                    int fontSize = Integer.parseInt(labelElement.getAttribute("fontsize")); // добавляем размер текста
                    JLabel label = new JLabel(labelText);
                    label.setBounds(x, y, width, height);
                    label.setFont(new Font("Arial", Font.PLAIN, fontSize)); // устанавливаем размер шрифта
                    // Добавление JLabel на панель
                    pagePanel.add(label);
                }
            }
















            // Создаем панель для кнопок с null-менеджером компоновки
            JPanel buttonPanel = new JPanel(null);
            pagePanel.add(buttonPanel, BorderLayout.CENTER);

            // Получаем список всех элементов <button> в документе
            NodeList buttonNodes = doc.getElementsByTagName("button");
            for (int i = 0; i < buttonNodes.getLength(); i++) {
                Node buttonNode = buttonNodes.item(i);
                if (buttonNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element buttonElement = (Element) buttonNode;
                    int width = Integer.parseInt(buttonElement.getAttribute("width"));
                    int height = Integer.parseInt(buttonElement.getAttribute("height"));
                    int x = Integer.parseInt(buttonElement.getAttribute("x"));
                    int y = Integer.parseInt(buttonElement.getAttribute("y"));

                    JButton button = new JButton("Button " + (i + 1));
                    button.setBounds(x, y, width, height); // Устанавливаем размеры и позицию кнопки
                    buttonPanel.add(button);

                }
            }






            // Возвращаем панель с добавленными компонентами
            return pagePanel;
        } catch (Exception e) {
            // Обработка возможных ошибок при загрузке XML или создании компонентов
            e.printStackTrace();
            return null; // или возвращайте другую панель, в зависимости от требований вашего приложения
        }
    }

    public static void main(String[] args) {
        // System.out.println("Hello world!");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

}

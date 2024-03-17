package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.File;
import org.w3c.dom.Element;
import java.sql.*;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Main() {
        setTitle("Авторизация и Регистрация");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        cardLayout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        // Инициализация подключения к базе данных
        initializeDatabase();

        // Добавление панелей для авторизации и регистрации
        JPanel loginPanel = loadPageFromXML("xml/Login.xml");
        JPanel registrationPanel = loadPageFromXML("xml/Registration.xml");

        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registrationPanel, "Registration");

        getContentPane().add(cardPanel);
        setVisible(true);
    }

    // Подключение к базе данных
    private Connection connection;

    // Метод для инициализации подключения к базе данных
    private void initializeDatabase() {
        try {
            // Загрузка драйвера JDBC
            Class.forName("org.postgresql.Driver");

            // Установка соединения с базой данных
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

            // Вывод сообщения об успешном подключении
            System.out.println("Подключение к базе данных успешно.");
        } catch (ClassNotFoundException | SQLException e) {
            // Обработка исключений
            e.printStackTrace();
        }
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
            NodeList inputNodes = doc.getElementsByTagName("input");
            for (int i = 0; i < inputNodes.getLength(); i++) {
                Node inputNode = inputNodes.item(i);
                if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element inputElement = (Element) inputNode;
                    int width = Integer.parseInt(inputElement.getAttribute("width"));
                    int height = Integer.parseInt(inputElement.getAttribute("height"));
                    int x = Integer.parseInt(inputElement.getAttribute("x"));
                    int y = Integer.parseInt(inputElement.getAttribute("y"));

                    JTextField inputField = new JTextField();
                    inputField.setBounds(x, y, width, height);
                    pagePanel.add(inputField);
                }
            }

            // Получаем список всех элементов <label> в документе
            NodeList labelNodes = doc.getElementsByTagName("label");
            for (int i = 0; i < labelNodes.getLength(); i++) {
                Node labelNode = labelNodes.item(i);
                if (labelNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element labelElement = (Element) labelNode;
                    String labelText = labelElement.getTextContent();
                    int x = Integer.parseInt(labelElement.getAttribute("x"));
                    int y = Integer.parseInt(labelElement.getAttribute("y"));
                    int width = Integer.parseInt(labelElement.getAttribute("width"));
                    int height = Integer.parseInt(labelElement.getAttribute("height"));
                    int fontSize = Integer.parseInt(labelElement.getAttribute("fontsize"));
                    JLabel label = new JLabel(labelText);
                    label.setBounds(x, y, width, height);
                    label.setFont(new Font("Arial", Font.PLAIN, fontSize));
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

                    String buttonText = buttonElement.getTextContent();
                    JButton button = new JButton(buttonText);
                    button.setBounds(x, y, width, height);
                    buttonPanel.add(button);

                    // Добавляем уникальный идентификатор кнопки в качестве свойства
                    String buttonId = buttonElement.getAttribute("id");
                    button.putClientProperty("id", buttonId);

                    // Добавление обработчика событий для переключения между панелями
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Получаем идентификатор кнопки
                            String buttonId = (String) button.getClientProperty("id");
                            if ("loginButton".equals(buttonId)) {
                                cardLayout.show(cardPanel, "Login");
                            } else if ("registerButton".equals(buttonId)) {
                                cardLayout.show(cardPanel, "Registration");
                            }
                        }
                    });
                }
            }

            // Возвращаем панель с добавленными компонентами
            return pagePanel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}

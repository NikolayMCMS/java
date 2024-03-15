package org.example;
import javax.swing.*;
import java.awt.*;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

public class Main extends JFrame{

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Main() {
        setTitle("Two Page Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        cardLayout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        try {
            JPanel page1Panel = loadPageFromXML("xml/Page1.xml");


            cardPanel.add(page1Panel, "Page 1");


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        getContentPane().add(cardPanel);
        setVisible(true);
    }

    // Метод загрузки содержимого страницы из XML-файла
    private JPanel loadPageFromXML(String filePath) {
        // Реализация этого метода опущена для примера, замените её соответствующим кодом
        return new JPanel();
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

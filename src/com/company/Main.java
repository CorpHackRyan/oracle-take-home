package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class SwingGUI {

    SwingGUI() {
        JFrame frame = new JFrame("OpenAQ API Fetcher");
        setupWindow(frame);
    }

    public void setupWindow(JFrame frame) {

        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        // Labels
        JLabel openAQimg = new JLabel(new ImageIcon("./favicon.png"));
        JLabel openAQimg2 = new JLabel(new ImageIcon("./favicon.png"));
        JLabel openAQimg3 = new JLabel(new ImageIcon("./favicon.png"));
        openAQimg.setBounds(10, 200, 100, 100);
        openAQimg2.setBounds(680, 200, 100, 100);
        openAQimg3.setBounds(350, 200, 100, 100);

        JLabel directionsParam = new JLabel("Select your parameter");
        directionsParam.setBounds(10, 5, 200, 50);

        JLabel countryCodeDesc = new JLabel("Enter a 2 digit country code");
        countryCodeDesc.setBounds(300, 5, 200, 50);

        JLabel statusLbl = new JLabel("Responses from OpenAQ server...");
        statusLbl.setBounds(275, 460, 300, 50);

        JLabel limitLbl = new JLabel("Enter limit per page (max 100000)");
        limitLbl.setBounds(550, 5, 250, 50);

        // Combo boxes
        String[] legalParameters = { "pm25", "pm10", "so2", "no2", "o3", "co", "bc" };
        JComboBox<String> measuredParameter = new JComboBox<>(legalParameters);
        measuredParameter.setBounds(40, 50, 100, 30);

        // Text boxes
        JTextArea countryCode = new JTextArea("US");
        countryCode.setBounds(375, 50, 35, 30);
        countryCode.setFont(new Font("Arial", Font.BOLD, 15));
        countryCode.setBorder(BorderFactory.createLineBorder(Color.black));

        JTextArea limitTxt = new JTextArea("1000");
        limitTxt.setBorder(BorderFactory.createLineBorder(Color.blue));
        limitTxt.setBounds(650, 50, 50, 30);
        limitTxt.setFont(new Font("Arial", Font.BOLD, 15));


        JTextArea urlText = new JTextArea("https://api.openaq.org/v2/measurements?parameter=" +
                measuredParameter.getSelectedItem() + "&country=" + countryCode.getText() + "&limit="
                + limitTxt.getText());
        urlText.setBorder(BorderFactory.createLineBorder(Color.blue));
        urlText.setBounds(10, 110, 780, 20);


        JTextArea statusBar = new JTextArea();
        statusBar.setLineWrap(true);
        statusBar.setBorder(BorderFactory.createLineBorder(Color.blue));
        statusBar.setBounds(10, 510, 780, 50);

        // Buttons
        JButton getData = new JButton("Blank button");
        JButton fetchCountryAndParam  = new JButton("Fetch data");
        JButton fetchCoordinatesAndRadius = new JButton("Fetch data");
        JButton quit = new JButton("Exit");
        JButton pingServer = new JButton("Ping OpenAQ server");

        // Button locations
        getData.setBounds(100, 400, 200, 25);
        fetchCountryAndParam.setBounds(300, 150, 200, 25);
        fetchCoordinatesAndRadius.setBounds(300, 400, 200, 25);
        quit.setBounds(590, 475, 200, 25);
        pingServer.setBounds(10, 475, 200, 25);

        // Event listeners
        fetchCountryAndParam.addActionListener(e -> {
            urlText.setText("https://api.openaq.org/v2/measurements?parameter=" +
                    measuredParameter.getSelectedItem() + "&country=" + countryCode.getText() + "&limit="
                    + limitTxt.getText());

            Main.retrieveJSON(urlText.getText());


        });

        fetchCoordinatesAndRadius.addActionListener(e -> {

        });
        getData.addActionListener(e -> {

        });
        quit.addActionListener(e -> {
            // Exit program
            System.exit(0);
        });
        pingServer.addActionListener(e -> {
            statusBar.setText("Pinging OpenAQ server...");
            long start = System.currentTimeMillis();
            int response = Main.pingTarget();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            if (response == 200) {
                statusBar.setText("[" + formatter.format(date) + "]  " + "  \nResponse from ping target url: HTTP " +
                        "STATUS CODE 200 - OK - Completed in " + timeElapsed + " ms.\n");
            } else {
                statusBar.setText("[" + formatter.format(date) + "]  " + "Response code: " + response + " given. Ping "
                        + "did not complete successfully.");
                JOptionPane.showMessageDialog(frame, "Response code: " + response + " given. Ping did not " +
                        "complete successfully.\n");
            }
        });
        measuredParameter.addActionListener(e -> urlText.setText("https://api.openaq.org/v2/measurements?parameter="
                + measuredParameter.getSelectedItem()
                + "&country=" + countryCode.getText() + "&limit=" + limitTxt.getText()));



        // Add everything to window
        frame.getContentPane().add(fetchCountryAndParam);
        frame.getContentPane().add(fetchCoordinatesAndRadius);
        frame.getContentPane().add(getData);
        frame.getContentPane().add(quit);
        frame.getContentPane().add(pingServer);
        frame.getContentPane().add(urlText);
        frame.getContentPane().add(openAQimg);
        frame.getContentPane().add(openAQimg2);
        frame.getContentPane().add(openAQimg3);
        frame.getContentPane().add(statusBar);
        frame.getContentPane().add(measuredParameter);
        frame.getContentPane().add(directionsParam);
        frame.getContentPane().add(countryCodeDesc);
        frame.getContentPane().add(countryCode);
        frame.getContentPane().add(statusLbl);
        frame.getContentPane().add(limitTxt);
        frame.getContentPane().add(limitLbl);

        frame.setVisible(true);
    }
}

public class Main {

        public static void createGUI() {
            // creating instance of Frame class
            SwingGUI mainGUI = new SwingGUI();
        }

        public static int pingTarget() {
            String ping_url = "https://docs.openaq.org/ping";
            int response_code = 0;

            try {
                URL url = new URL(ping_url);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                response_code = conn.getResponseCode();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return response_code;
        }

        public static void retrieveJSON(String OpenAQUrl) {

             try {
                StringBuilder results = new StringBuilder();

                URL url = new URL(OpenAQUrl);
                HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int response = conn.getResponseCode();

                if (response != 200) {
                    throw  new RuntimeException("HTTP Response code: " + response);
                } else {
                    Scanner sc = new Scanner(url.openStream());
                    while(sc.hasNext())
                    {
                        results.append(sc.nextLine());
                    }

                    parseJSONData(results.toString());
                    System.out.println(results);

                    sc.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static void parseJSONData(String jsonList) {
            try {
                JSONParser parse = new JSONParser();
                JSONObject jobJ = (JSONObject) parse.parse(jsonList);

                //convert  JSON obj values into a JSON results array
                JSONArray jsArr = (JSONArray) jobJ.get("results");

                for (Object o : jsArr) {
                    //Store the JSON objects in an array
                    //Get the index of the JSON object and print the values as per the index
                    JSONObject jsonObj_1 = (JSONObject) o;
                    System.out.println(jsonObj_1);
                }
                
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }

        public static void main(String[] args) {
            createGUI();
        }
    }
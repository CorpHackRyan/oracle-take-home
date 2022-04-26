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

        String v2URL = "https://u50g7n0cbj.execute-api.us-east-1.amazonaws.com/v2";

        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        // Labels
        JLabel directionsParam = new JLabel("Select your parameter");
        JLabel countryCodeDesc = new JLabel("Enter a 2 digit country code");
        JLabel limitLbl = new JLabel("Enter limit per page (max 100000)");
        JLabel latitudeLbl = new JLabel("Latitude");
        JLabel longitudeLbl = new JLabel("Longitude");
        JLabel radiusLbl = new JLabel("Radius");
        JLabel selectParamlbl = new JLabel("Select your parameter");
        JLabel statusLbl = new JLabel("Responses from OpenAQ server...");
        JLabel openAQimg = new JLabel(new ImageIcon("./favicon.png"));
        JLabel openAQimg2 = new JLabel(new ImageIcon("./favicon.png"));
        JLabel openAQimg3 = new JLabel(new ImageIcon("./favicon.png"));

        // Combo boxes
        String[] legalParameters = { "pm25", "pm10", "so2", "no2", "o3", "co", "bc" };
        JComboBox<String> measuredParameter = new JComboBox<>(legalParameters);
        JComboBox<String> measuredParameter2 = new JComboBox<>(legalParameters);
        measuredParameter2.setSelectedItem("o3");

        // Text boxes
        JTextArea countryCode = new JTextArea("US");
        countryCode.setFont(new Font("Arial", Font.BOLD, 15));
        countryCode.setBorder(BorderFactory.createLineBorder(Color.black));
        JTextArea limitTxt = new JTextArea("1000");
        limitTxt.setBorder(BorderFactory.createLineBorder(Color.blue));
        limitTxt.setFont(new Font("Arial", Font.BOLD, 15));

        JTextArea urlText = new JTextArea(v2URL + "/measurements?parameter=" +
                measuredParameter.getSelectedItem() + "&country=" + countryCode.getText() + "&limit="
                + limitTxt.getText());
        urlText.setBorder(BorderFactory.createLineBorder(Color.blue));

        JTextArea statusBar = new JTextArea();
        statusBar.setLineWrap(true);
        statusBar.setBorder(BorderFactory.createLineBorder(Color.blue));

        JTextArea latitudeTxt = new JTextArea("41.886017");
        JTextArea longitudeTxt = new JTextArea("12.541614");
        JTextArea radiusTxt = new JTextArea("1000");
        latitudeTxt.setBorder(BorderFactory.createLineBorder(Color.blue));
        longitudeTxt.setBorder(BorderFactory.createLineBorder(Color.blue));
        radiusTxt.setBorder(BorderFactory.createLineBorder(Color.blue));

        JTextArea urlText2 = new JTextArea(v2URL + "/measurements?limit=1000&parameter="
                + measuredParameter2.getSelectedItem() + "&coordinates=" + latitudeTxt.getText() + "%2C" +
                longitudeTxt.getText() + "&radius=" + radiusTxt.getText());
        urlText2.setLineWrap(true);
        urlText2.setBorder(BorderFactory.createLineBorder(Color.blue));

        // Buttons /
        JButton fetchCountryAndParam  = new JButton("Fetch data");
        JButton fetchCoordinatesAndRadius = new JButton("Fetch data");
        JButton quit = new JButton("Exit");
        JButton pingServer = new JButton("Ping OpenAQ server");



        // Event listeners
        fetchCountryAndParam.addActionListener(e -> {
            urlText.setText(v2URL + "/measurements?parameter=" +
                    measuredParameter.getSelectedItem() + "&country=" + countryCode.getText() + "&limit="
                    + limitTxt.getText());

            Main.retrieveJSON(urlText.getText());
        });

        fetchCoordinatesAndRadius.addActionListener(e -> {
            urlText2.setText(v2URL + "/measurements?limit=1000&&parameter="
                            + measuredParameter2.getSelectedItem() + "&coordinates=" + latitudeTxt.getText() + "%2C" +
                            longitudeTxt.getText() + "&radius=" + radiusTxt.getText());
            Main.retrieveJSON(urlText2.getText());
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

        measuredParameter.addActionListener(e -> urlText.setText(v2URL + "/measurements?parameter="
                + measuredParameter.getSelectedItem()
                + "&country=" + countryCode.getText() + "&limit=" + limitTxt.getText()));


        // Placement of all objects
        openAQimg.setBounds(10, 200, 100, 100);
        openAQimg2.setBounds(680, 200, 100, 100);
        openAQimg3.setBounds(350, 200, 100, 100);
        directionsParam.setBounds(10, 5, 200, 50);
        countryCodeDesc.setBounds(300, 5, 200, 50);
        limitLbl.setBounds(550, 5, 250, 50);
        statusLbl.setBounds(275, 460, 300, 50);
        latitudeLbl.setBounds(15, 340, 90, 20);
        longitudeLbl.setBounds(110, 340, 90, 20);
        radiusLbl.setBounds(370, 340, 70, 20);
        selectParamlbl.setBounds(600, 340, 170, 20);
        measuredParameter.setBounds(40, 50, 100, 30);
        measuredParameter2.setBounds(630, 360, 100, 30);
        countryCode.setBounds(375, 50, 35, 30);
        limitTxt.setBounds(650, 50, 50, 30);
        urlText.setBounds(10, 110, 780, 20);
        statusBar.setBounds(10, 510, 780, 50);
        latitudeTxt.setBounds(10, 360, 75, 20);
        longitudeTxt.setBounds(110, 360, 75, 20);
        radiusTxt.setBounds(370, 360, 50, 20);
        urlText2.setBounds(10, 395, 780, 35);
        fetchCountryAndParam.setBounds(300, 150, 200, 25);
        fetchCoordinatesAndRadius.setBounds(300, 440, 200, 25);
        quit.setBounds(590, 475, 200, 25);
        pingServer.setBounds(10, 475, 200, 25);
        
        // Add everything to window
        frame.getContentPane().add(fetchCountryAndParam);
        frame.getContentPane().add(fetchCoordinatesAndRadius);
        frame.getContentPane().add(quit);
        frame.getContentPane().add(pingServer);
        frame.getContentPane().add(urlText);
        frame.getContentPane().add(urlText2);
        frame.getContentPane().add(openAQimg);
        frame.getContentPane().add(openAQimg2);
        frame.getContentPane().add(openAQimg3);
        frame.getContentPane().add(statusBar);
        frame.getContentPane().add(measuredParameter);
        frame.getContentPane().add(measuredParameter2);
        frame.getContentPane().add(directionsParam);
        frame.getContentPane().add(countryCodeDesc);
        frame.getContentPane().add(countryCode);
        frame.getContentPane().add(statusLbl);
        frame.getContentPane().add(limitTxt);
        frame.getContentPane().add(limitLbl);
        frame.getContentPane().add(latitudeTxt);
        frame.getContentPane().add(longitudeTxt);
        frame.getContentPane().add(radiusTxt);
        frame.getContentPane().add(latitudeLbl);
        frame.getContentPane().add(longitudeLbl);
        frame.getContentPane().add(radiusLbl);
        frame.getContentPane().add(selectParamlbl);
        frame.setVisible(true);
    }
}

public class Main {

        public static void createGUI() {
            // creating instance of Frame class
            SwingGUI mainGUI = new SwingGUI();
        }

        public static int pingTarget() {
            String ping_url = "https://u50g7n0cbj.execute-api.us-east-1.amazonaws.com/ping";
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
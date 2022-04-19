package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
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
        openAQimg.setBounds(200, 200, 100, 100);
        JLabel directionsParam = new JLabel("Select your parameter");
        directionsParam.setBounds(140, 5, 200, 50);
        JLabel countryCodeDesc = new JLabel("Enter a 2 digit country code");
        countryCodeDesc.setBounds(400, 5, 200, 50);


        // Combo box
        String[] legalParameters = { "pm25", "pm10", "so2", "no2", "o3", "co", "bc" };
        JComboBox measuredParameter = new JComboBox(legalParameters);
        measuredParameter.setBounds(170, 50, 100, 30);

        // Text box
        JTextArea countryCode = new JTextArea("US");
        countryCode.setBounds(500, 50, 35, 30);
        countryCode.setFont(new Font("Arial", Font.BOLD, 15));
        countryCode.setBorder(BorderFactory.createLineBorder(Color.black));

        JTextArea urlText = new JTextArea("https://api.openaq.org/v2/measurements?parameter=" +
                measuredParameter.getSelectedItem() + "&country=" + countryCode.getText() + "&limit=100");
        urlText.setBorder(BorderFactory.createLineBorder(Color.blue));
        urlText.setBounds(10, 110, 780, 20);

        JTextArea statusBar = new JTextArea();
        statusBar.setLineWrap(true);
        statusBar.setBorder(BorderFactory.createLineBorder(Color.blue));
        statusBar.setBounds(10, 510, 780, 50);

        // Buttons
        JButton getData = new JButton("Retrieve data");
        JButton fetchCountryAndParam  = new JButton("Fetch data");
        JButton fetchCoordinatesAndRadius = new JButton("Fetch data");
        JButton quit = new JButton("Exit");
        JButton pingServer = new JButton("Ping OpenAQ server");

        // Button locations
        getData.setBounds(100, 400, 200, 50);
        fetchCountryAndParam.setBounds(300, 400, 200, 50);
        fetchCoordinatesAndRadius.setBounds(300, 150, 200, 25);
        quit.setBounds(500, 400, 200, 50);
        pingServer.setBounds(100, 450, 200, 50);

        // Button event listeners
        fetchCountryAndParam.addActionListener(e -> {
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


        // Add everything to window
        frame.getContentPane().add(fetchCountryAndParam);
        frame.getContentPane().add(fetchCoordinatesAndRadius);
        frame.getContentPane().add(getData);
        frame.getContentPane().add(quit);
        frame.getContentPane().add(pingServer);
        frame.getContentPane().add(urlText);
        frame.getContentPane().add(openAQimg);
        frame.getContentPane().add(statusBar);
        frame.getContentPane().add(measuredParameter);
        frame.getContentPane().add(directionsParam);
        frame.getContentPane().add(countryCodeDesc);
        frame.getContentPane().add(countryCode);


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

        public static String parseJSONData(String jsonList) {
            try {
                JSONParser parse = new JSONParser();
                JSONObject jobJ = (JSONObject) parse.parse(jsonList);

                //convert  JSON obj values into a JSON results array
                JSONArray jsArr = (JSONArray) jobJ.get("results");

                //Get data for Results array

                //old way: for(int idx =0; idx < jsArr.size() ; idx++)
                for (Object o : jsArr) {
                    //Store the JSON objects in an array
                    //Get the index of the JSON object and print the values as per the index

                    //old way: JSONObject jsonObj_1 = (JSONObject)jsArr.get(idx); enhanced way below
                    JSONObject jsonObj_1 = (JSONObject) o;

                    //System.out.println("Elements under results array");
                    //System.out.println("\nPlace id: " + jsonObj_1.get("id"));
                    //System.out.println("Types: " + jsonObj_1.get("name"));
                    System.out.println(jsonObj_1);

                }
                
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

            return "success";
        }

        public static void main(String[] args) {



            createGUI();


        }

    }
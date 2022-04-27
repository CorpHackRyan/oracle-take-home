package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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
        JLabel selectParamLbl = new JLabel("Select your parameter");
        JLabel limit2Lbl = new JLabel("Enter limit per page (max 100000)");
        JLabel statusLbl = new JLabel("Responses from OpenAQ server...");
        JLabel openAQImg = new JLabel(new ImageIcon("./favicon.png"));
        JLabel openAQImg2 = new JLabel(new ImageIcon("./favicon.png"));
        JLabel openAQImg3 = new JLabel(new ImageIcon("./favicon.png"));

        // Combo boxes
        String[] legalParameters = { "pm25", "pm10", "so2", "no2", "o3", "co", "bc" };
        JComboBox<String> measuredParameter = new JComboBox<>(legalParameters);
        JComboBox<String> measuredParameter2 = new JComboBox<>(legalParameters);
        measuredParameter2.setSelectedItem("o3");

        // Text boxes
        JTextArea countryCodeTxt = new JTextArea("US");
        countryCodeTxt.setFont(new Font("Arial", Font.BOLD, 15));
        countryCodeTxt.setBorder(BorderFactory.createLineBorder(Color.blue));
        JTextArea limitTxt = new JTextArea("1000");
        limitTxt.setBorder(BorderFactory.createLineBorder(Color.blue));
        limitTxt.setFont(new Font("Arial", Font.BOLD, 15));
        JTextArea limit2Txt = new JTextArea("1000");
        limit2Txt.setBorder(BorderFactory.createLineBorder(Color.blue));
        limit2Txt.setFont(new Font("Arial", Font.BOLD, 15));

        JTextArea urlText = new JTextArea(v2URL + "/measurements?parameter=" +
                measuredParameter.getSelectedItem() + "&country=" + countryCodeTxt.getText() + "&limit="
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

        // Buttons
        JButton fetchCountryAndParam  = new JButton("Fetch data");
        JButton fetchCoordinatesAndRadius = new JButton("Fetch data");
        JButton quit = new JButton("Exit");
        JButton pingServer = new JButton("Ping OpenAQ server");

        // Event listeners
        fetchCountryAndParam.addActionListener(e -> {
            urlText.setText(v2URL + "/measurements?parameter=" +
                    measuredParameter.getSelectedItem() + "&country=" + countryCodeTxt.getText() + "&limit="
                    + limitTxt.getText());
            Main.retrieveJSON(urlText.getText(), "countryParam");
        });

        fetchCoordinatesAndRadius.addActionListener(e -> {
            urlText2.setText(v2URL + "/measurements?limit=" + limit2Txt.getText() + "&parameter="
                            + measuredParameter2.getSelectedItem() + "&coordinates=" + latitudeTxt.getText() + "%2C" +
                            longitudeTxt.getText() + "&radius=" + radiusTxt.getText());
            Main.retrieveJSON(urlText2.getText(), "coordRadius");
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
                + "&country=" + countryCodeTxt.getText() + "&limit=" + limitTxt.getText()));

        // Placement of all objects
        openAQImg.setBounds(10, 180, 100, 100);
        openAQImg2.setBounds(680, 180, 100, 100);
        openAQImg3.setBounds(350, 180, 100, 100);
        directionsParam.setBounds(10, 5, 200, 50);
        countryCodeDesc.setBounds(300, 5, 200, 50);
        limitLbl.setBounds(550, 5, 250, 50);
        statusLbl.setBounds(275, 460, 300, 50);
        latitudeLbl.setBounds(15, 310, 90, 20);
        longitudeLbl.setBounds(110, 310, 90, 20);
        radiusLbl.setBounds(250, 310, 70, 20);
        limit2Lbl.setBounds(330, 310, 250, 20);
        selectParamLbl.setBounds(600, 310, 170, 20);
        measuredParameter.setBounds(40, 50, 100, 20);
        measuredParameter2.setBounds(630, 330, 100, 20);
        countryCodeTxt.setBounds(375, 50, 35, 20);
        limitTxt.setBounds(650, 50, 50, 20);
        urlText.setBounds(10, 85, 780, 20);
        statusBar.setBounds(10, 510, 780, 50);
        latitudeTxt.setBounds(10, 330, 75, 20);
        longitudeTxt.setBounds(110, 330, 75, 20);
        radiusTxt.setBounds(250, 330, 50, 20);
        limit2Txt.setBounds(415, 330, 50, 20);
        urlText2.setBounds(10, 365, 780, 35);
        fetchCountryAndParam.setBounds(300, 115, 200, 25);
        fetchCoordinatesAndRadius.setBounds(300, 410, 200, 25);
        quit.setBounds(590, 475, 200, 25);
        pingServer.setBounds(10, 475, 200, 25);

        // Add everything to window
        frame.getContentPane().add(fetchCountryAndParam);
        frame.getContentPane().add(fetchCoordinatesAndRadius);
        frame.getContentPane().add(quit);
        frame.getContentPane().add(pingServer);
        frame.getContentPane().add(urlText);
        frame.getContentPane().add(urlText2);
        frame.getContentPane().add(openAQImg);
        frame.getContentPane().add(openAQImg2);
        frame.getContentPane().add(openAQImg3);
        frame.getContentPane().add(statusBar);
        frame.getContentPane().add(measuredParameter);
        frame.getContentPane().add(measuredParameter2);
        frame.getContentPane().add(directionsParam);
        frame.getContentPane().add(countryCodeDesc);
        frame.getContentPane().add(countryCodeTxt);
        frame.getContentPane().add(statusLbl);
        frame.getContentPane().add(limitTxt);
        frame.getContentPane().add(limit2Txt);
        frame.getContentPane().add(limitLbl);
        frame.getContentPane().add(limit2Lbl);
        frame.getContentPane().add(latitudeTxt);
        frame.getContentPane().add(longitudeTxt);
        frame.getContentPane().add(radiusTxt);
        frame.getContentPane().add(latitudeLbl);
        frame.getContentPane().add(longitudeLbl);
        frame.getContentPane().add(radiusLbl);
        frame.getContentPane().add(selectParamLbl);
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

    public static void retrieveJSON(String OpenAQUrl, String fetchType) {

        try {
            StringBuilder results = new StringBuilder();

            URL url = new URL(OpenAQUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int response = conn.getResponseCode();

            if (response != 200) {
                throw new RuntimeException("HTTP Response code: " + response);
            } else {
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    results.append(sc.nextLine());
                }

                parseJSONData(results.toString());
                sc.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred: \n" + ex);

        }
    }

    public static void parseJSONData(String jsonList) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonMaster = (JSONObject) parser.parse(jsonList); // contains meta header, and results header
            JSONArray parsedResults = (JSONArray) jsonMaster.get("results");

            createCSV(parsedResults);

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "An error occurred: \n" + e);
            e.printStackTrace();
        }
    }

    public static void createCSV(JSONArray resultsParsedArr) {
        String utcDate = "", country = "", lat = "", lon = "", unit = "", value = "",
                parameter = "", sensortype = ""; // add part 2 strings here
        String heatmapFilename = "openaq_heatmap.csv";

        try (FileWriter file = new FileWriter(heatmapFilename)) {

            // Write CSV Header
            file.write("date_UTC" + "," + "country" + "," + "latitude" + "," + "longitude" + "," + "unit" + "," +
                    "parameter" + "," + "value" + "," + "sensortype\n");

            // Data parsed out of JSON Array to export to CSV
            for (Object header : resultsParsedArr) {

                Object date = ((JSONObject) header).get("date");
                if (((JSONObject) date).get("utc") == null) {
                    utcDate = "";
                } else {
                    utcDate = ((JSONObject) date).get("utc").toString();
                }


                if (((JSONObject) header).get("country") == null) {
                    country = "";
                } else {
                    country = ((JSONObject) header).get("country").toString();
                }


                Object coordinates = ((JSONObject) header).get("coordinates");
                Object latitude = ((JSONObject) coordinates).get("latitude");
                Object longitude = ((JSONObject) coordinates).get("longitude");
                if (latitude.toString() == null) {
                    lat = "";
                } else {
                    lat = latitude.toString();
                }
                if (longitude.toString() == null) {
                    lon = "";
                } else {
                    lon = longitude.toString();
                }


                if (((JSONObject) header).get("unit") == null) {
                    unit = "";
                } else {
                    unit = (((JSONObject) header).get("unit").toString());
                }


                if (((JSONObject) header).get("parameter") == null) {
                    parameter = "";
                } else {
                    parameter = (((JSONObject) header).get("parameter").toString());
                }


                if (((JSONObject) header).get("value") == null) {
                    value = "";
                } else {
                    value = (((JSONObject) header).get("value").toString());
                }


                if (((JSONObject) header).get("sensorType") == null) {
                    sensortype = "";
                } else {
                    sensortype = (((JSONObject) header).get("sensorType").toString());
                }


                file.write(utcDate + "," + country + "," + lat + "," + lon + "," +
                        unit + "," + parameter + "," + value + "," + sensortype + "\n");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (resultsParsedArr.size() == 0) {
            JOptionPane.showMessageDialog(null, "No records were found");
        } else {

            JOptionPane.showMessageDialog(null, resultsParsedArr.size() +
                    " records found. File location is: \n" + Paths.get(heatmapFilename).toAbsolutePath());
        }

    }

    public static void main(String[] args) {
        createGUI();
    }
}


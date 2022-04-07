package com.company;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.net.URL;
import java.util.Scanner;

// class AWTExample2 directly creates instance of Frame class
class AWTExample2 {

    // initializing using constructor
    AWTExample2() {

        // creating a Frame
        Frame f = new Frame();

        // creating a Label
        Label l = new Label("Employee id:");

        // creating a Button
        Button b = new Button("Submit");

        // creating a TextField
        TextField t = new TextField();

        // setting position of above components in the frame
        l.setBounds(20, 80, 80, 30);
        t.setBounds(20, 100, 80, 30);
        b.setBounds(100, 100, 80, 30);

        // adding components into frame
        f.add(b);
        f.add(l);
        f.add(t);

        // frame size 300 width and 300 height
        f.setSize(400, 300);

        // setting the title of frame
        f.setTitle("Employee info");

        // no layout
        f.setLayout(null);

        // setting visibility of frame
        f.setVisible(true);
    }
}


    public class Main {

        public static int return_num(int input) {
            return input * 2;
        }

        public static void create_GUI() {
            // creating instance of Frame class
            AWTExample2 awt_obj = new AWTExample2();
        }

        public static int ping_target(int url) {
            // ping the V2 ping endpoint and then return a valid or invalid response from it
            //int response_code = 200;
            //return response_code;
            return 0;
        }

        public static void main(String[] args) {
            // write your code here
            try {
                String target_url = "https://u50g7n0cbj.execute-api.us-east-1.amazonaws.com/v2/locations?limit=100" +
                        "&page=1&offset=0&sort=desc&radius=1000&order_by=lastUpdated&dumpRaw=false";
                URL url = new URL(target_url);

                HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int response = conn.getResponseCode();

                String results = "";

                if (response != 200) {
                    throw  new RuntimeException("HTTP Response code: " + response);
                } else {
                    Scanner sc = new Scanner(url.openStream());
                    while(sc.hasNext())
                    {
                        //Possibly use a string builder here instead - but we may not even need this
                        results+= sc.nextLine();
                    }

                    System.out.println(results);
                    System.out.println("\n All JSON data returned  from " + target_url);

                    sc.close();

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
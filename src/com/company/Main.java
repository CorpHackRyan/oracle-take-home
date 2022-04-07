package com.company;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.net.URL;

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

        public static void junk() {
            System.out.println("Testing");
            int x = 2;
            int y = return_num(x);
            System.out.println(y);

            JSONObject jo = new JSONObject();
            jo.put("test", "jon doe");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("test");
            }
            // creating instance of Frame class
            AWTExample2 awt_obj = new AWTExample2();
            // EVERYTHING ABOVE IS JUST JUNK EXPERIMENTING

        }

        public static void main(String[] args) {
            // write your code here
            try {

                URL url = new URL("https://u50g7n0cbj.execute-api.us-east-1.amazonaws.com/ping");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responsecode = conn.getResponseCode();

                int rc = responsecode;


            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }
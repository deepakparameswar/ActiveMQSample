/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinmicro.activemqsample;

import java.util.Date;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Jefin
 */
public class MQTTSender2 {

    

    
    

    private String MQTT_URL = "ssl://b-db425f45-40f5-4b28-a021-7ae27c5ddeb1-1.mq.ap-northeast-1.amazonaws.com:8883";
   // "ssl://b-db425f45-40f5-4b28-a021-7ae27c5ddeb1-1.mq.ap-northeast-1.amazonaws.com:8883";
    private String MQTT_USERNAME = "cisco_meraki_ap";
    private String MQTT_PASSWORD = "hV7gJFG94Zd5xaUJ";
    private static final String CHANNEL = "meraki/log2";

  /*   private String MQTT_URL = "tcp://localhost:1883";
    private String MQTT_USERNAME = "meraki";
    private String MQTT_PASSWORD = "meraki";
    private static final String CHANNEL = "be8b147f6z6d2318b224e0358656694f/";  */

/*     private String MQTT_URL = "tcp://6.tcp.ngrok.io:16352";
    private String MQTT_USERNAME = "";
    private String MQTT_PASSWORD = "";
    private static final String CHANNEL = "meraki/log"; 
    private static final String CLIENT_ID = "server-"; */
    private static final String CLIENT_ID = "server-";
    MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient mqttClient;


    private static final String LOG_MESSAGE = " hello from publisher";

    private MQTTSender2() throws MqttException {
        mqttClient = new MqttClient(MQTT_URL, CLIENT_ID + MqttClient.generateClientId(), persistence);
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(MQTT_USERNAME);
        connectOptions.setPassword(MQTT_PASSWORD.toCharArray());
        //connectOptions.set
        mqttClient.connect(connectOptions);
        System.out.println("Sender Connected");
    }

    public static void main(String[] args) {
        Random random= new Random();

        MQTTSender2 mQTTSample = null;
        try {
            mQTTSample = new MQTTSender2();
            for(int i=0;i<1;i++){
                mQTTSample.sendMessage(CHANNEL, ("hello testing "+i) );
                Thread.sleep(10);
            }
            

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } catch (InterruptedException ex) {
            System.out.println("thread sleep failed");
        } finally {
            if (mQTTSample != null) {
                try {
                    mQTTSample.disconnect();
                } catch (MqttException ex) {
                    System.out.println("disconnect failed");
                    ex.printStackTrace();
                }
            }
        }
    }

    private void sendMessage(String channel, String messageStr) throws MqttException {
        MqttMessage message = new MqttMessage(messageStr.getBytes());
        //send exactly oncee
        message.setQos(1);
        //the broker should retain this message until consumed by a subscriber.
        //message.setRetained(true);
        mqttClient.publish(channel, message);
        System.out.println(new Date() + " Message published, topic:"+channel);
    }

    private void disconnect() throws MqttException {
        mqttClient.disconnect();
    }

//    public String  getJson() {
//        JSONParser jsonParser = new JSONParser();
//        Object payloadObj = jsonParser.parse(payload);
//        JSONObject parameters = (JSONObject) payloadObj;
//        String reqCount = parameters.get("reqCount").toString();
//        Integer startId = Integer.parseInt(parameters.get("startId").toString());
//
//        JSONObject settings = new JSONObject();
//        try {
//            FileReader reader = new FileReader("/home/joshy/Downloads/digi-response.json");
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
//            settings = (JSONObject) obj;
//            JSONArray data = (JSONArray) settings.get("data");
//            JSONArray filteredData = new JSONArray();
//            if (startId == -1) {
//                startId = 0;
//            }
//
//            for (int i = startId; i < data.size(); i++) {
//                filteredData.add(data.get(i));
//            }
//            settings.replace("data", filteredData);
//            // Gson gson= new Gson();
//            //AccessData accessData=gson.fromJson(settings.toJSONString(), AccessData.class);
//            return settings;
//
//        } catch (FileNotFoundException e) {
//            // logger.error("read settings.json error-File not found");
//            //System.exit(0);
//        } catch (IOException e) {
//            // logger.error("read settings.json error-" + e.getMessage());
//            // System.exit(0);
//        } catch (ParseException e) {
//            // logger.error("read settings.json error-" + e.getMessage());
//            // System.exit(0);
//        }
//    }
}

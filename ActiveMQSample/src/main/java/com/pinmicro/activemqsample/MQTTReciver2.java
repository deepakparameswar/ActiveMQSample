/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pinmicro.activemqsample;


import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author Jefin
 */
public class MQTTReciver2 {


     private static String CHANNEL = "be8b147f6z6d2318b224e0358656694f/#";//"mytopic";
    private String MQTT_URL="tcp://localhost:1883";
    private String MQTT_USERNAME="";
    private String MQTT_PASSWORD="";
    

    private static final String MESSAGE = "test message";
    private static final String CLIENT_ID = "client-";
    MemoryPersistence persistence = new MemoryPersistence();

    private MqttClient mqttClient;

    private MQTTReciver2() throws MqttException {
        mqttClient = new MqttClient(MQTT_URL, CLIENT_ID + MqttClient.generateClientId(), persistence);
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(MQTT_USERNAME);
        connectOptions.setPassword(MQTT_PASSWORD.toCharArray());
        mqttClient.connect(connectOptions);
        System.out.println("Reciever Connected");
    }

    public static void main(String[] args) {

        try {
            MQTTReciver2 mQTTSample = new MQTTReciver2();
            mQTTSample.subscribe(CHANNEL);
            System.out.println("topic: "+CHANNEL+" suscribed");
//            mQTTSample.disconnect();

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
    
    private void subscribe(String channel) throws MqttException {
        mqttClient.subscribe(channel, new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage mm) throws Exception {
                System.out.println("Message received:"+topic+" "+new String(mm.getPayload()));
            }
        });
    }
    
    private void disconnect() throws MqttException {
        mqttClient.disconnect();
    }
}

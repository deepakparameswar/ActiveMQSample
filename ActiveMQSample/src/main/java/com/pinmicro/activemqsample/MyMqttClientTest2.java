package com.pinmicro.activemqsample;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Deepak
 */
public class MyMqttClientTest2 implements MqttCallbackExtended {
    private static final  Logger LOGGER = LoggerFactory.getLogger(MyMqttClientTest2.class);
    private final int qos = 0;
    private String topic = "mytopic";
    private MqttClient client;

    public MyMqttClientTest2() throws MqttException {
        String host = "tcp://localhost:1883";
        String clientId = "MQTT-Client";

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        //Pay attention here to automatic reconnect
        conOpt.setAutomaticReconnect(true);
        this.client = new org.eclipse.paho.client.mqttv3.MqttClient(host, clientId);
        this.client.setCallback(this);
        this.client.connect(conOpt);
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        LOGGER.error("Connection lost because: " + cause);
    }

    @Override
    public void connectComplete(boolean arg0, String arg1) {
        try {
      //Very important to resubcribe to the topic after the connection was (re-)estabslished. 
      //Otherwise you are reconnected but you don't get any message
        this.client.subscribe(this.topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        LOGGER.info(String.format("[%s] %s", topic, new String(mm.getPayload())));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        try {
            LOGGER.info(String.format("[%s] %s", topic, imdt.getMessage()));
        } catch (MqttException ex) {
            LOGGER.error("",ex);
        }
        
    }
    public static void main(String[] args) throws MqttException {
        MyMqttClientTest2 s = new MyMqttClientTest2();
        
    }
}

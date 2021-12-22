package com.pinmicro.activemqsample;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 *
 * @author Deepak
 */
public class MyMqttClientTest implements MqttCallbackExtended {
   // private static final  Logger LOGGER = LoggerFactory.getLogger(MyMqttClientTest.class);
    private final int qos = 0;
    private String topic = "config/222C8F5D65703E91234";//"mytopic";
    private String host="ssl://b-db425f45-40f5-4b28-a021-7ae27c5ddeb1-1.mq.ap-northeast-1.amazonaws.com";
    private String username="external_terminal_app";
    private String password="f93F34Hb0Nkp";
    private MqttClient client;

    public MyMqttClientTest() throws MqttException {
        //String host = "tcp://localhost:1883";
        String clientId = "MQTT-Client";

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());
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
        System.out.println("Connection lost because: " + cause);
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
        System.out.println(String.format("[%s] %s", topic, new String(mm.getPayload())));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        try {
            System.out.println(String.format("[%s] %s", topic, imdt.getMessage()));
        } catch (MqttException ex) {
            System.out.println(ex);
        }
        
    }    
    
    public static void main(String[] args) throws MqttException {
        MyMqttClientTest s=new MyMqttClientTest();
    }
}

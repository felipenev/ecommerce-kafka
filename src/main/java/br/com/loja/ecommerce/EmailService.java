package br.com.loja.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Consumer for Email Service
 */
public class EmailService {

    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL"));

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));//listen for 100 miliseconds
            if(!records.isEmpty()){
                System.out.println("Register found: "+records.count());

                for (var record: records) {
                    System.out.println("------------------------------");
                    System.out.println("Send email!");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println(record.partition());
                    System.out.println(record.offset());//tracker of the sequential order
                    try{
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        //ignoring
                        e.printStackTrace();
                    }
                    System.out.println("Email sent");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());//Simple class name as group id
        return properties;
    }
}
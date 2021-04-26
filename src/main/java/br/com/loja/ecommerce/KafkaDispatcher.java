package br.com.loja.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Produces resource
 */
class KafkaDispatcher implements AutoCloseable{

    private final KafkaProducer<String, String> producer;

    KafkaDispatcher(){

        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    void send(String topic, String key, String value) throws ExecutionException, InterruptedException {

        ProducerRecord<String, String> recordNewOrder = new ProducerRecord<>(topic, key, value);
        producer.send(recordNewOrder, getCallback()).get();
    }

    private Callback getCallback() {
        return (data, ex) -> {
            if(ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso enviando " + data.topic() + "::: partition " + data.partition() + " / offset: " + data.offset() + " / timestamp: "+data.timestamp());
        };
    }

    @Override
    public void close() {
        this.producer.close();
    }
}

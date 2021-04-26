package br.com.loja.ecommerce;


import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        KafkaDispatcher kafkaDispatcher = new KafkaDispatcher();

        for(var i = 0; i < 10; i++){

            //Topics to send
            var key = UUID.randomUUID().toString();
            var value = key+",675757,3423424334";
            kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", key, value);//new order topic dispatcher

            var email = "Thank you for your order! We are processing your order!";
            kafkaDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);//email topic dispatcher

        }
    }

}

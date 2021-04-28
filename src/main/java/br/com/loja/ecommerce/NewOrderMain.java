package br.com.loja.ecommerce;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(KafkaDispatcher orderKafkaDispatcher = new KafkaDispatcher<Order>()){
            try(KafkaDispatcher emailKafkaDispatcher = new KafkaDispatcher<String>()){
                for(var i = 0; i < 10; i++){

                    //Topics to send
                    var userId = UUID.randomUUID().toString();
                    var orderid = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);

                    var order = new Order(userId, orderid, amount);
                    orderKafkaDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);//new order topic dispatcher

                    var email = "Thank you for your order! We are processing your order!";
                    emailKafkaDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);//email topic dispatcher

                }
            }
        }


    }

}

package br.com.loja.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Consumer for Email Service
 */
public class EmailService {

    public static void main(String[] args) {

        EmailService emailService = new EmailService();
        KafkaService kafkaService = new KafkaService(EmailService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse);
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("------------------------------");
        System.out.println("Send email!");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());//tracker of the sequential order
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            //ignoring
            e.printStackTrace();
        }
        System.out.println("Email sent");
    }

}

package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
/*
 * 
 * Za pokretanje primera potrebno je instalirati RabbitMQ - https://www.rabbitmq.com/download.html
 */
@SpringBootApplication
public class RabbitmqConsumerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqConsumerExampleApplication.class, args);
	}
	/*
	 * Registrujemo bean koji ce sluziti za konekciju na RabbitMQ
	 * gde se mi u primeru kacimo u lokalu.
	 */

	@Bean
	public Queue anonymousQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("advertisingExchange");
	}

	@Bean
	public Binding binding(FanoutExchange fanoutExchange, Queue anonymousQueue) {
		return BindingBuilder.bind(anonymousQueue).to(fanoutExchange);
	}


	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}
	
}

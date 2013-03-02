package gash.mq.rabbit;

import gash.mq.rabbit.core.MQueueFactory;
import gash.mq.rabbit.core.MQueuePublisher;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rabbitmq.client.AMQP;

public class MQueuePublisherTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPublish() throws Exception {
		MQueueFactory factory = new MQueueFactory("localhost", AMQP.PROTOCOL.PORT, "test", "test");
		MQueuePublisher pub = factory.createPublisher("pubsub-demo");
		pub.publish("hello I am a message");
		System.out.println("message sent");
	}

	/**
	 * test to verify the use of a routing key (binding key on receiver) to
	 * selectively manage messages
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPublishUsingBinding() throws Exception {
		String[] bindings = { "hello.info", "hello.error", "hello.sub.info", "hello.sub.error" };

		MQueueFactory factory = new MQueueFactory("localhost", AMQP.PROTOCOL.PORT, "test", "test");
		MQueuePublisher pub = factory.createPublisher("pubsub-demo");

		Random rand = new Random();
		for (int i = 0, I = 10; i < I; i++) {
			int n = rand.nextInt(bindings.length);
			pub.publish("hello I am " + (i + 1) + " of " + I + ": this is a " + bindings[n] + " message", bindings[n]);
			Thread.sleep(1000);
		}

		System.out.println("messages sent");
	}
}

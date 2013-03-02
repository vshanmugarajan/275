package gash.mq.rabbit.demo;

import gash.mq.rabbit.core.MQueueFactory;
import gash.mq.rabbit.core.MQueueListener;
import gash.mq.rabbit.core.MQueueSubscriber;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.AMQP;

public class SubscribeApp extends MQueueListener {
	private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
	private MQueueSubscriber sub;

	SubscribeApp() {
		MQueueFactory factory = new MQueueFactory("localhost", AMQP.PROTOCOL.PORT, "test", "test");
		sub = factory.createSubscriber("pubsub-demo");
		sub.addListener(this);
	}

	@Override
	public void onMessage(String msg, String topic) {
		// TODO should be abstract but for demonstration we print the message
		System.out.println("MSG(" + topic + ") at " + fmt.format(new Date()) + " - " + msg);
	}

	public void addBindingFilter(String v) {
		sub.addTopic(v);
	}

	public void demo() {

		// blocking
		try {
			sub.subscribe();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("** RabbitMQ pub-sub - Subscriber of ");
		for (String arg : args)
			System.out.print(arg + " ");
		System.out.println("**\n");

		SubscribeApp sa = new SubscribeApp();

		for (String arg : args)
			sa.addBindingFilter(arg);

		sa.demo();
	}

}

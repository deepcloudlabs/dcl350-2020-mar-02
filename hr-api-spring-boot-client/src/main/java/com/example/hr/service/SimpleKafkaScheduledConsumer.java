package com.example.hr.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;

@Service
public class SimpleKafkaScheduledConsumer {
	private KafkaConsumer<String, Employee> kafkaConsumer;

	@PostConstruct
	public void init() {
		Properties consumerProperties = new Properties();
		consumerProperties.put("bootstrap.servers", "localhost:9092");
		consumerProperties.put("group.id", "hr");
		consumerProperties.put("zookeeper.session.timeout.ms", "6000");
		consumerProperties.put("zookeeper.sync.time.ms", "2000");
		consumerProperties.put("auto.commit.enable", "false");
		consumerProperties.put("auto.commit.interval.ms", "1000");
		consumerProperties.put("consumer.timeout.ms", "-1");
		consumerProperties.put("max.poll.records", "1");
		consumerProperties.put("value.deserializer", "org.springframework.kafka.support.serializer.JsonDeserializer");
		consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumerProperties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		this.kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		this.kafkaConsumer.subscribe(Arrays.asList("employees"));
	}

	@Scheduled(fixedRate = 60_000)
	public void pollMessages() {
		ConsumerRecords<String, Employee> employees = this.kafkaConsumer.poll(Duration.ofMillis(100));
		for (ConsumerRecord<String, Employee> employee : employees) {
			System.out.println("[SimpleKafkaConsumer] Received an employee : " + employee);
		}
	}
}

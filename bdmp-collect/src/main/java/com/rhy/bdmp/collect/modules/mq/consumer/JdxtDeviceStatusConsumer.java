package com.rhy.bdmp.collect.modules.mq.consumer;

import com.rhy.bdmp.collect.modules.mq.handler.JdxtDeviceStatusHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class JdxtDeviceStatusConsumer {
    @Resource
    private JdxtDeviceStatusHandler deviceStatusHandler;

    @KafkaListener(topics = "jtjk_device_status")
    public void consumerDeviceStatusMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                deviceStatusHandler.syncDeviceStatus(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

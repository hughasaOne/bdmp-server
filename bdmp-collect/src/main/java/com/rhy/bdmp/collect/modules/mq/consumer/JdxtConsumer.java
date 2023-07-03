package com.rhy.bdmp.collect.modules.mq.consumer;

import com.rhy.bdmp.collect.modules.mq.handler.JdxtHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
@Slf4j
public class JdxtConsumer {

    @Resource
    private JdxtHandler jdxtHandler;

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOI_Company")
    public void consumerCompanyMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncOperatingCompany(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOI_WaySection")
    public void consumerWaySectionMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncWaySection(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOI_GeographyInfo")
    public void consumerFacilitiesMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncFacilities(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOP_DeviceRecord")
    public void consumerDeviceMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncDevice(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_assets_camera_resource_tl")
    public void consumerVideoResourceTlMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncVideoResourceTl(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_assets_camera_resource_yt")
    public void consumerVideoResourceYtMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncVideoResourceYt(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.OPF_Sys_Dict")
    public void consumerDictMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncDict(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.OPF_Sys_Dict_Item")
    public void consumerDictItemMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncDictItem(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOP_BrandInfo")
    public void consumerDictBrandMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncDictBrand(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOP_DeviceDict")
    public void consumerDictDeviceMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncDictDevice(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.HOP_MachineSystem")
    public void consumerDictSystemMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncDictSystem(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_assets_route")
    public void consumerRouteMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncRoute(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_assets_facilities_toll_station")
    public void consumerTollStationMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncTollStation(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_service_area")
    public void consumerServiceAreaMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncServiceArea(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_assets_facilities_bridge")
    public void consumerBridgeMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncBridge(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_assets_facilities_tunnel")
    public void consumerTunnelMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncTunnel(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_gantryinfo")
    public void consumerGantryMsg(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncGantry(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @KafkaListener(topics = "jt.db.jdxt.dbo.t_bdmp_station_lane")
    public void consumerTollStationLane(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<String> message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            String msg = message.get();
            try {
                jdxtHandler.syncTollStationLane(msg);
                ack.acknowledge();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

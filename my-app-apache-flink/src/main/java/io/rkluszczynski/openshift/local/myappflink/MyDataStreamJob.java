package io.rkluszczynski.openshift.local.myappflink;

import io.rkluszczynski.openshift.local.myappflink.domain.WordsCapitalizer;
import java.util.Properties;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.GlobalConfiguration;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

public class MyDataStreamJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = configureExecutionEnvironment();
        var bootstrapServers = "my-kafka-ephemeral-single-kafka-bootstrap:9093";

        var consumer = createStringConsumer("my-input-topic", bootstrapServers, "my-group");
        env.addSource(consumer)
              .returns(String.class)
              .map(new WordsCapitalizer())
              .addSink(
                    createStringProducer("my-output-topic", bootstrapServers)
              );
        env.execute("Flink Java UpperCase Example");
    }

    private static FlinkKafkaConsumer<String> createStringConsumer(String topic, String bootstrapServers, String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new FlinkKafkaConsumer<>(
              topic,
              new SimpleStringSchema(),
              properties
        );

//        return KafkaSource.<String>builder()
//              .setBootstrapServers(bootstrapServers)
//              .setTopics(topic)
//              .setGroupId(groupId)
////              .setStartingOffsets(OffsetsInitializer.earliest())
//              .setValueOnlyDeserializer(new SimpleStringSchema())
//              .setProperties(properties)
//              .build();
    }

    private static FlinkKafkaProducer<String> createStringProducer(String topic, String bootstrapServers) {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return new FlinkKafkaProducer<>(
              topic,
              new SimpleStringSchema(),
              properties);

//        final var serializer = KafkaRecordSerializationSchema.<String>builder()
//              .setValueSerializationSchema(new SimpleStringSchema())
//              .setTopic(topic)
//              .build();
//        return KafkaSink.<String>builder()
//              .setBootstrapServers(bootstrapServers)
//              .setRecordSerializer(serializer)
//              .setKafkaProducerConfig(properties)
//              .build();
    }

    private static StreamExecutionEnvironment configureExecutionEnvironment() {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        if (executionEnvironment instanceof LocalStreamEnvironment) {
            Configuration configuration = new Configuration(); //GlobalConfiguration.loadConfiguration("config");
            return StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        } else {
            return executionEnvironment;
        }
    }
}

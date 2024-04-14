package io.rkluszczynski.openshift.local.myappflink;

import io.rkluszczynski.openshift.local.myappflink.domain.WordsCapitalizer;
import java.util.Properties;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MyDataStreamJob {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final var bootstrapServers = "my-kafka-ephemeral-single-kafka-bootstrap";

        final var source = createStringConsumer("my-input-topic", bootstrapServers, "my-group");
        final var stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
        stream
              .map(new WordsCapitalizer())
              .sinkTo(
                    createStringProducer("my-output-topic", bootstrapServers)
              );

        env.execute("Flink Java UpperCase Example");
    }

    private static KafkaSource<String> createStringConsumer(String topic, String bootstrapServers, String kafkaGroup) {
        final var properties = new Properties();

        return KafkaSource.<String>builder()
              .setBootstrapServers(bootstrapServers)
              .setTopics(topic)
              .setGroupId(kafkaGroup)
              .setStartingOffsets(OffsetsInitializer.earliest())
              .setValueOnlyDeserializer(new SimpleStringSchema())
              .setProperties(properties)
              .build();
    }

    private static KafkaSink<String> createStringProducer(String topic, String bootstrapServers) {
        final var properties = new Properties();

        final var serializer = KafkaRecordSerializationSchema.<String>builder()
              .setValueSerializationSchema(new SimpleStringSchema())
              .setTopic(topic)
              .build();
        return KafkaSink.<String>builder()
              .setBootstrapServers(bootstrapServers)
              .setRecordSerializer(serializer)
              .setKafkaProducerConfig(properties)
              .build();
    }
}

package dataconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.math.Quantiles;
import dataconsumer.model.DataPoint;
import dataconsumer.repository.DataPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class KafkaConsumerService {

    private final DataPointRepository dataPointRepository;
    private final ObjectMapper objectMapper;
    private final MongoTemplate mongoTemplate;

    public KafkaConsumerService(DataPointRepository dataPointRepository, ObjectMapper objectMapper, MongoTemplate mongoTemplate) {
        this.dataPointRepository = dataPointRepository;
        this.objectMapper = objectMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @KafkaListener(
            topics = "${KafkaConsumerService.topic.name}",
            groupId = "${KafkaConfig.KafkaConsumerService.groupId}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeMessage(String message) {
        log.info("Received message {}", message);

        try {
            final JsonNode jsonNode = objectMapper.readTree(message);
            final DataPoint dataPoint = DataPoint.builder()
                    .publisher(getPublisher(jsonNode))
                    .value(getMedianOfReadings(jsonNode))
                    .timestamp(Calendar.getInstance().getTimeInMillis())
                    .build();
            dataPointRepository.save(dataPoint);
        } catch (JsonProcessingException e) {
            log.error("JSON unmarshalling error occurred while parsing message {}, {}", message, e);
        }
    }

    private String getPublisher(JsonNode jsonNode) {
        return jsonNode.get("publisher").asText();
    }

    private Double getMedianOfReadings(JsonNode jsonNode) {
        final ArrayNode readingsArrayNode = (ArrayNode) jsonNode.get("readings");
        final List<Double> readings = StreamSupport.stream(readingsArrayNode.spliterator(), false)
                .map(JsonNode::asDouble)
                .collect(Collectors.toList());
        return Quantiles.median().compute(readings);
    }
}

package dataconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataconsumer.dto.request.DataPointRequestDTO;
import dataconsumer.dto.response.DataPointResponseDTO;
import dataconsumer.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPointService {

    private final KafkaConsumerService kafkaConsumerService;
    private final ObjectMapper objectMapper;

    public DataPointService(KafkaConsumerService kafkaConsumerService, ObjectMapper objectMapper) {
        this.kafkaConsumerService = kafkaConsumerService;
        this.objectMapper = objectMapper;
    }

    public DataPointResponseDTO handleGet(DataPointRequestDTO dataPointRequestDTO) {
        throw new CustomException("An error occurred while handling a data point creation request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
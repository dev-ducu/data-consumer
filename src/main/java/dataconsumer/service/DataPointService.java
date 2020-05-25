package dataconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataconsumer.model.DataPoint;
import dataconsumer.repository.DataPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataPointService {

    private final DataPointRepository dataPointRepository;
    private final ObjectMapper objectMapper;

    public DataPointService(DataPointRepository dataPointRepository, ObjectMapper objectMapper) {
        this.dataPointRepository = dataPointRepository;
        this.objectMapper = objectMapper;
    }

    public List<DataPoint> handleGet(String publisher, Integer dataSize) {
        return dataPointRepository.findAll(
                Example.of(DataPoint.builder()
                        .publisher(publisher)
                        .build()),
                PageRequest.of(
                        0,
                        dataSize,
                        Sort.Direction.DESC,
                        "timestamp")).get()
                .collect(Collectors.toList());
    }
}
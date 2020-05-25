package dataconsumer.controller;

import dataconsumer.config.SwaggerConfig;
import dataconsumer.dto.response.DataPointResponseDTO;
import dataconsumer.service.DataPointService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(DataPointController.PATH_DATA_POINTS)
@Api(tags = SwaggerConfig.TAG_DATA_POINTS)
public class DataPointController {

    public static final String PATH_DATA_POINTS = "/data-points";

    private final DataPointService dataPointService;

    @Autowired
    public DataPointController(DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    @GetMapping(value = "")
    @ApiOperation(value = "${DataPointController.get}", response = DataPointResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Something went wrong")
    })
    public DataPointResponseDTO get(
            @ApiParam(value = "Publisher", example = "pub-1") @RequestParam String publisher,
            @ApiParam(value = "Reading bucket size", example = "10") @RequestParam Integer dataSize) {
        log.info("Handling GET request on path => {}", PATH_DATA_POINTS);
        log.info("Request params: publisher => {}, dataSize => {}", publisher, dataSize);

        final DataPointResponseDTO dataPointsResponseDTO = DataPointResponseDTO.builder()
                .dataPoints(dataPointService.handleGet(publisher, dataSize))
                .build();

        log.info("Response => {}", dataPointsResponseDTO);
        return dataPointsResponseDTO;
    }
}

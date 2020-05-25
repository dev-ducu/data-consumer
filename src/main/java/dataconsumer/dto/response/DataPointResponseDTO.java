package dataconsumer.dto.response;

import dataconsumer.model.DataPoint;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPointResponseDTO {

    @ApiModelProperty()
    private List<DataPoint> dataPoints;
}

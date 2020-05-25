package dataconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPoint {

    @NotBlank
    private String publisher;

    @NotBlank
    private Double value;

    @NotBlank
    private Long timestamp;
}

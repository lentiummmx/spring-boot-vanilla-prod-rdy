package mx.nuniez.xoco.spring.boot.vanilla.api.dto;

//import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemDto {

    @NotNull
    @Size(min = 1, max = 255)
    //@ApiModelProperty(value = "Product item title")
    private String title;

}

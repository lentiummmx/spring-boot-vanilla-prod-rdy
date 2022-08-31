package mx.nuniez.xoco.spring.boot.vanilla.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsDto {

    private List<ErrorDto> errors;

}

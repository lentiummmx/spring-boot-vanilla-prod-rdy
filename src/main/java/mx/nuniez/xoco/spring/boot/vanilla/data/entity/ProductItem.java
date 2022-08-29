package mx.nuniez.xoco.spring.boot.vanilla.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

    private Long id;

    private String title;

}

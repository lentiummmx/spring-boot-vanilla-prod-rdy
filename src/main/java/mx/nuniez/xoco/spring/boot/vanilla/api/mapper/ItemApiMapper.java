package mx.nuniez.xoco.spring.boot.vanilla.api.mapper;

import mx.nuniez.xoco.spring.boot.vanilla.api.dto.CreateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.UpdateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.CreateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.UpdateItemRequest;
import org.springframework.stereotype.Component;

@Component
public class ItemApiMapper {

    public CreateItemRequest map(CreateItemDto createItemDto) {
        return CreateItemRequest.builder()
                .title(createItemDto.getTitle())
                .build();
    }

    public UpdateItemRequest map(UpdateItemDto updateItemDto) {
        return UpdateItemRequest.builder()
                .title(updateItemDto.getTitle())
                .build();
    }

}

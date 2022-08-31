package mx.nuniez.xoco.spring.boot.vanilla.api.mapper;

import mx.nuniez.xoco.spring.boot.vanilla.api.dto.CreateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.UpdateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.CreateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.UpdateItemRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemApiMapper {

    CreateItemRequest map(CreateItemDto createItemDto);

    UpdateItemRequest map(UpdateItemDto updateItemDto);

}

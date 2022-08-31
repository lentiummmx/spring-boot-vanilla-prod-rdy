package mx.nuniez.xoco.spring.boot.vanilla.service.mapper;

import mx.nuniez.xoco.spring.boot.vanilla.data.entity.ProductItem;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.CreateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.UpdateItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    default Page<Item> map(Page<ProductItem> page) {
        return page.map(this::map);
    }

    Item map(ProductItem productItem);

    @Mapping(target = "id", ignore = true)
    ProductItem map(CreateItemRequest createItemRequest);

    @Mapping(target = "id", ignore = true)
    void map(@MappingTarget ProductItem item, UpdateItemRequest updateItemRequest);

}

package mx.nuniez.xoco.spring.boot.vanilla.service.mapper;

import mx.nuniez.xoco.spring.boot.vanilla.data.entity.ProductItem;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.CreateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.UpdateItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductItemMapper {

    public Item map(ProductItem productItem) {
        return Item.builder()
                .id(productItem.getId())
                .title(productItem.getTitle())
                .build();
    }

    public Page<Item> map(Page<ProductItem> page) {
        return page.map(this::map);
    }

    public ProductItem map(CreateItemRequest createItemRequest) {
        return ProductItem.builder()
                .title(createItemRequest.getTitle())
                .build();
    }

    public void map(ProductItem item, UpdateItemRequest updateItemRequest) {
        item.setTitle(updateItemRequest.getTitle());
    }

}

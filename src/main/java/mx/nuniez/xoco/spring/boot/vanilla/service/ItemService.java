package mx.nuniez.xoco.spring.boot.vanilla.service;

import mx.nuniez.xoco.spring.boot.vanilla.service.domain.CreateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.UpdateItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface ItemService {

    Page<Item> findAll(@NotNull Pageable pageable);

    Item getOne(@NotNull Long id);

    Item create(@NotNull @Valid CreateItemRequest createItemRequest);

    Item update(@NotNull Long id, @Valid UpdateItemRequest updateItemRequest);

    void delete(@NotNull Long id);

}

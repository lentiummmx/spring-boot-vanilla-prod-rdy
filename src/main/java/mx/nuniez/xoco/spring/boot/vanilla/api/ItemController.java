package mx.nuniez.xoco.spring.boot.vanilla.api;

import lombok.RequiredArgsConstructor;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.CreateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.UpdateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.api.mapper.ItemApiMapper;
import mx.nuniez.xoco.spring.boot.vanilla.service.ItemService;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static mx.nuniez.xoco.spring.boot.vanilla.api.ItemController.ENDPOINT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ENDPOINT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class ItemController {

    public static final String ENDPOINT = "/items";
    public static final String ENDPOINT_BY_ID = "/{id}";

    private final ItemService service;
    private final ItemApiMapper mapper;

    @GetMapping
    public Page<Item> find(@PageableDefault(sort = "id") Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping(value = ENDPOINT_BY_ID)
    public Item get(@PathVariable Long id) {
        return service.getOne(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public Item create(@RequestBody CreateItemDto createItemDto) {
        return service.create(mapper.map(createItemDto));
    }

    @PutMapping(value = ENDPOINT_BY_ID)
    public Item update(@PathVariable Long id, @RequestBody UpdateItemDto updateItemDto) {
        return service.update(id, mapper.map(updateItemDto));
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = ENDPOINT_BY_ID)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}

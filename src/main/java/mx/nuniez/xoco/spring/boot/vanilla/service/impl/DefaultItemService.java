package mx.nuniez.xoco.spring.boot.vanilla.service.impl;

import lombok.RequiredArgsConstructor;
import mx.nuniez.xoco.spring.boot.vanilla.data.repository.ProductItemRepository;
import mx.nuniez.xoco.spring.boot.vanilla.service.ItemService;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.CreateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.UpdateItemRequest;
import mx.nuniez.xoco.spring.boot.vanilla.service.mapper.ProductItemMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static mx.nuniez.xoco.spring.boot.vanilla.api.config.CacheConfiguration.ITEMS_CACHE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultItemService implements ItemService {

    private final ProductItemRepository repository;
    private final ProductItemMapper mapper;

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return mapper.map(repository.findAll(pageable));
    }

    @Override
    @Cacheable(ITEMS_CACHE)
    public Item getOne(Long id) {
        //return mapper.map(repository.getOne(id));
        return mapper.map(repository.customSelectById(id));
    }

    @Override
    @Transactional
    public Item create(CreateItemRequest createItemRequest) {
        return mapper.map(repository.save(mapper.map(createItemRequest)));
    }

    @Override
    @Transactional
    @CacheEvict(ITEMS_CACHE)
    public Item update(Long id, UpdateItemRequest updateItemRequest) {
        final var item = repository.getOne(id);
        mapper.map(item, updateItemRequest);
        return mapper.map(repository.save(item));
    }

    @Override
    @Transactional
    @CacheEvict(ITEMS_CACHE)
    public void delete(Long id) {
        final var item = repository.getOne(id);
        repository.delete(item);
    }

}

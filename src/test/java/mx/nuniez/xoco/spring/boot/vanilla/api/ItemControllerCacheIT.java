package mx.nuniez.xoco.spring.boot.vanilla.api;

import mx.nuniez.xoco.spring.boot.vanilla.AbstractIntegrationTest;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.CreateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static mx.nuniez.xoco.spring.boot.vanilla.api.config.CacheConfiguration.ITEMS_CACHE;
import static org.assertj.core.api.Assertions.assertThat;


@Sql(scripts = {
        "/sql/clean.sql",
        "/sql/product_items.sql"
})
public class ItemControllerCacheIT extends AbstractIntegrationTest {

    @Test
    public void shouldGetItem_FromCache() {
        final HttpEntity<Void> voidEntity = new HttpEntity<>(httpHeaders());

        final HttpEntity<CreateItemDto> createEntity = new HttpEntity<>(
                new CreateItemDto("new item for cache"),
                httpHeaders()
        );

        ResponseEntity<Item> createdItem = restTemplate.exchange(
                url("/items"),
                HttpMethod.POST,
                createEntity,
                Item.class
        );

        assertThat(createdItem.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final Long itemId = createdItem.getBody().getId();

        final ResponseEntity<Item> foundItem = restTemplate.exchange(
                url("/items/" + itemId),
                HttpMethod.GET,
                voidEntity,
                Item.class
        );
        assertThat(foundItem.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(foundItem.getBody()).isNotNull();
        assertThat(foundItem.getBody().getId()).isEqualTo(itemId);
        assertThat(foundItem.getBody().getTitle()).isEqualTo("new item for cache");

        final Item item = cacheManager.getCache(ITEMS_CACHE).get(itemId, Item.class);
        assertThat(item.getId()).isEqualTo(itemId);

        final ResponseEntity<Void> deletedItem = restTemplate.exchange(
                url("/items/" + itemId),
                HttpMethod.DELETE,
                voidEntity,
                Void.class
        );
        assertThat(deletedItem.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        final Item itemAfterDelete = cacheManager.getCache(ITEMS_CACHE).get(itemId, Item.class);
        assertThat(itemAfterDelete).isNull();
    }

}
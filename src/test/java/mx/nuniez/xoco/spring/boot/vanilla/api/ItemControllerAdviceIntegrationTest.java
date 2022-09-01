package mx.nuniez.xoco.spring.boot.vanilla.api;

import mx.nuniez.xoco.spring.boot.vanilla.AbstractIntegrationTest;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.CreateItemDto;
import mx.nuniez.xoco.spring.boot.vanilla.api.dto.ErrorsDto;
import mx.nuniez.xoco.spring.boot.vanilla.service.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {
        "/sql/clean.sql",
        "/sql/product_items.sql"
})
public class ItemControllerAdviceIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldNotGetItem_NotFound() {
        final HttpEntity<Void> entity = new HttpEntity<>(httpHeaders());

        ResponseEntity<Item> foundItem = restTemplate.exchange(url("/items/-1"), HttpMethod.GET, entity, Item.class);
        assertThat(foundItem.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldNotCreateItem_LongTitle() {
        final String title = Stream.generate(() -> "a")
                .limit(257)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        final HttpEntity<CreateItemDto> entity = new HttpEntity<>(
                new CreateItemDto(title),
                httpHeaders()
        );

        ResponseEntity<ErrorsDto> error = restTemplate.exchange(
                url("/items"),
                HttpMethod.POST,
                entity,
                ErrorsDto.class
        );

        assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(error.getBody()).isNotNull();
        assertThat(error.getBody().getErrors()).isNotNull();
        assertThat(error.getBody().getErrors()).hasSize(1);
        assertThat(error.getBody().getErrors().get(0).getMessage()).isEqualTo("size must be between 1 and 255");
        assertThat(error.getBody().getErrors().get(0).getPath()).isEqualTo("create.createItemRequest.title");
        assertThat(error.getBody().getErrors().get(0).getValue()).isEqualTo(title);
    }

}
package mx.nuniez.xoco.spring.boot.vanilla.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_item")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "product_item_id_generator")
    @SequenceGenerator(name = "product_item_id_generator", sequenceName = "product_item_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String title;

}

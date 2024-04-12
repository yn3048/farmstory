package kr.co.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.metamodel.model.domain.internal.PrimitiveBasicTypeImpl;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "img")
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ino;
    private int pno;
    private String img1;
    private String img2;
    private String img3;

}

package kr.co.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private String uid;
    private int pno;
    private int pcount;

}

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
@Table(name = "orderlist")
public class Orderlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno;
    private int ono;
    private String uid;
    private int pno;
    private int pcount;

}

package kr.co.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    private String uid;
    private String pname;
    private String cate;

    @CreationTimestamp
    private LocalDateTime rdate;

    private int price;

    @Builder.Default
    private int stock = 0;
    @Builder.Default
    private int delprice = 0;

    private String company;

    @Builder.Default
    private int discount = 0;
    @Builder.Default
    private int point = 0;

    private String info;

}
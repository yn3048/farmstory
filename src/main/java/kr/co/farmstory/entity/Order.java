package kr.co.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ono;
    private String uid;

    @CreationTimestamp
    private LocalDateTime odate;

    private int usepoint;
    private String receiver;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String payment;
    private String etc;
    private int total;

}

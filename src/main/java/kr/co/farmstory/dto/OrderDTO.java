package kr.co.farmstory.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    @Id
    private int ono;
    private String uid;

    private int pno;
    private int pcount;


    private String receiver;
    private LocalDateTime odate;
    private int usepoint;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;
    private String payment;
    private String etc;
    private int total;

    // orderlistDTO
    private int Ino;
    private int Ono;
    private int price;

    // productDTO
    private String pname;
    private String cate;
    private int delprice;
    private int discount;
    private int point;
    private String info;
    private String img1;

}

package kr.co.farmstory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderlistDTO {

    private int lno;
    private int ono;
    private String uid;
    private int pno;
    private int pcount;


    //productDTO
    private String pname;
    private int price;

    //ordersDTO
    private int total;
    private LocalDateTime odate;
}

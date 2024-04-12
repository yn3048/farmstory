package kr.co.farmstory.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private String uid;
    private int pno;
    private int pcount;

    private String cate;
    private String pname;
    private int discount;
    private int point;
    private int price;
    private int delprice;

    private String name;
    private String hp;
    private int userpoint;
    private String zip;
    private String addr1;
    private String addr2;
    private String img1;



}

package kr.co.farmstory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user")
public class User {

    @Id
    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private String zip;
    private String addr1;
    private String addr2;


    private String role;
    private String sms;
    private String provider;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;
    private LocalDateTime ddate;

}

package kr.co.farmstory.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
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
    private int userpoint;

    @CreationTimestamp
    private LocalDateTime rdate;
    private LocalDateTime ddate;
}

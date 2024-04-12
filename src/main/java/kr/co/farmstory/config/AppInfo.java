package kr.co.farmstory.config;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AppInfo {

    private String name;
    private String version;

}
package kr.co.farmstory.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {

    private int ano;
    private String uid;
    private int parent;
    private int comment;
    private String cate;
    private String title;
    private String content;
    private int hit;
    private String regip;
    private String grp;
    private LocalDateTime rdate;
    private int file;
    private Integer good;
    private Integer hate;

    private List<MultipartFile> files;

    private List<FileDTO> filelist;

    private String nick;
}

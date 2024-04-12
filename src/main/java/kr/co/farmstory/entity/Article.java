package kr.co.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ano;

    private String uid;
    private int parent;

    @ColumnDefault("0")
    private int comment;

    private String cate;
    private String title;
    private String content;

    @ColumnDefault("0")
    private int hit;

    private String regip;
    private String grp;

    @CreationTimestamp
    private LocalDateTime rdate;

    @ColumnDefault("0")
    private int file;

    @ColumnDefault("0")
    private Integer good;
    @ColumnDefault("0")
    private Integer hate;

    @OneToMany(mappedBy = "ano")
    private List<File> filelist;

}

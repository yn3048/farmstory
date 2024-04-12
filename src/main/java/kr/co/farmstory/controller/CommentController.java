package kr.co.farmstory.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j @RequiredArgsConstructor @RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/{no}")
    public ResponseEntity<List<ArticleDTO>> selectComment(@PathVariable("no") int no){
        return commentService.selectComment(no);
    }

    @PostMapping("/comment")
    public ResponseEntity<Article> insertComment(@RequestBody ArticleDTO articleDTO, HttpServletRequest req){
        String regip = req.getRemoteAddr();
        articleDTO.setRegip(regip);
        log.info("insertComment : " + articleDTO);

        return commentService.insertComment(articleDTO);
    }


    @Transactional
    @GetMapping("/good/{ano}")
    public String updateGood(String grp, String cate, @PathVariable("ano") int ano){
        ArticleDTO articleDTO = new ArticleDTO();
        log.info(" "+ano);
        commentService.updateGood(ano);
        return "redirect:/article/view?grp="+grp+"&cate="+cate+"&ano="+ano;
    }

    @Transactional
    @GetMapping("/hate/{ano}")
    public String updateHate(String grp, String cate, @PathVariable("ano") int ano){
        ArticleDTO articleDTO = new ArticleDTO();
        log.info(" "+ano);
        commentService.updateHate(ano);
        return "redirect:/article/view?grp="+grp+"&cate="+cate+"&ano="+ano;
    }

    @DeleteMapping("/comment/{ano}")
    public ResponseEntity<?> deleteComment(@PathVariable("ano") int ano){
        return commentService.deleteComment(ano);
    }

    @PutMapping("/comment")
    public ResponseEntity<?> modifyComment(@RequestBody ArticleDTO articleDTO, HttpServletRequest req){

        return commentService.modifyComment(articleDTO);
    }

}

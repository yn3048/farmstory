package kr.co.farmstory.service;

import kr.co.farmstory.dto.ArticleDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.mapper.ArticleMapper;
import kr.co.farmstory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j @RequiredArgsConstructor @Service
public class CommentService {

    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    public ResponseEntity<Article> insertComment(ArticleDTO articleDTO){
        Article article = modelMapper.map(articleDTO, Article.class);

        Article insertArticle = articleRepository.save(article);
        log.info(insertArticle.toString());

        return ResponseEntity.ok().body(insertArticle);
    }

    public ResponseEntity<List<ArticleDTO>> selectComment(int no){
        List<Article> articleList = articleRepository.findCommentByParent(no);

        List<ArticleDTO> articleDTOs = articleList.stream()
                .map(entity->modelMapper.map(entity, ArticleDTO.class))
                .toList();

        return ResponseEntity.ok().body(articleDTOs);
    }

    public void updateGood(int ano){

        articleRepository.updateGood(ano);
    }

    public void updateHate(int ano){

        articleRepository.updateHate(ano);
    }

    public ResponseEntity<?> deleteComment(int ano){
        Optional<Article> optArticle = articleRepository.findById(ano);
        log.info("commentDelete 1");
        if(optArticle.isPresent()){
            articleRepository.deleteById(ano);
            log.info("commentDelete 2");
            return ResponseEntity.ok().body(optArticle.get());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }
    }

    public ResponseEntity<?> modifyComment(ArticleDTO articleDTO){
        Optional<Article> optArticle = articleRepository.findById(articleDTO.getAno());

        if(optArticle.isPresent()){
            Article article = optArticle.get();

            article.setContent(articleDTO.getContent());

            log.info("modifyComment : " + article);

            Article modifyedArticle = articleRepository.save(article);

            return ResponseEntity.ok().body(modifyedArticle);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }

    }
}

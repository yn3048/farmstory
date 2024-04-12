package kr.co.farmstory.service;

import kr.co.farmstory.dto.ArticleDTO;

import kr.co.farmstory.dto.FileDTO;
import kr.co.farmstory.entity.Article;
import kr.co.farmstory.entity.File;
import kr.co.farmstory.mapper.ArticleMapper;
import kr.co.farmstory.repository.ArticleRepository;
import kr.co.farmstory.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import kr.co.farmstory.mapper.CropTalkMapper;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j @RequiredArgsConstructor @Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final FileRepository fileRepository;

    private final ModelMapper modelMapper;

    private final FileService fileService;

    private final ArticleMapper articleMapper;
    private final CropTalkMapper cropTalkMapper;

    public List<ArticleDTO> selectArticles(String grp, String cate){
        return articleMapper.selectArticles(grp, cate);
    }

    public List<ArticleDTO>selectArticlesMain(String cate){
        return articleMapper.selectArticlesMain(cate);
    }

    public List<ArticleDTO> getRecentArticles(String cate) {
        return cropTalkMapper.selectRecentArticles(cate);
    }

    public void insertArticle(ArticleDTO articleDTO){

        articleDTO.setGood(0);
        articleDTO.setHate(0);

        log.info("insertArticle...1");
        List<FileDTO> files = fileService.fileUpload(articleDTO);

        log.info("insertArticle...2" + files);

        // 파일 갯수
        articleDTO.setFile(files.size());

        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article.toString());

        Article savedArticle = articleRepository.save(article);
        log.info("insertArticle : " + savedArticle);

        // 파일 넣기
        for (FileDTO fileDTO : files){
            fileDTO.setAno(savedArticle.getAno());

            File file = modelMapper.map(fileDTO, File.class);

            fileRepository.save(file);
        }
    }

    public ArticleDTO selectArticle(int ano){
        Optional<Article> optArticle = articleRepository.findById(ano);
        log.info("findById 1");

        ArticleDTO articleDTO = null;
        if(optArticle.isPresent()){
            log.info("findById 2");
            Article article = optArticle.get();

            articleDTO = modelMapper.map(article, ArticleDTO.class);
            log.info("findById 3");
        }
        log.info("articleDTO : " + articleDTO);

        return articleDTO;
    }

    public ResponseEntity<?> updateArticle (ArticleDTO articleDTO){
        Optional<Article> optArticle = articleRepository.findById(articleDTO.getAno());

        if(optArticle.isPresent()){
            Article article = optArticle.get();
            log.info("article:" + article);

            article.setTitle(articleDTO.getTitle());
            article.setContent(articleDTO.getContent());

            Article modifiedArticle = articleRepository.save(article);

            return ResponseEntity.status(HttpStatus.OK).body(modifiedArticle);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }


    }

    public void deleteArticle(int ano){
        articleMapper.deleteArticle(ano);
    }

    public void updateHit(int ano){
        articleMapper.updateHit(ano);
    }
}
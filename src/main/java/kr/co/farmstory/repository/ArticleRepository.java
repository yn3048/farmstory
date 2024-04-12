package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    public List<Article> findCommentByParent(int parent);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE article SET good = good+1 where ano=?1", nativeQuery = true)
    public void updateGood(int ano);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE article SET hate = hate+1 where ano=?1", nativeQuery = true)
    public void updateHate(int ano);

}

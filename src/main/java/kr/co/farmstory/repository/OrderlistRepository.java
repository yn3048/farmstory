package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Article;
import kr.co.farmstory.entity.Orderlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderlistRepository extends JpaRepository<Orderlist, Integer> {


}

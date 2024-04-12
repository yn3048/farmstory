package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<Img, Integer> {
}

package kr.co.farmstory.repository;

import kr.co.farmstory.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public List<Cart> findCartByUidAndPno(String uid, int pno);
}

package kr.co.farmstory.service;


import kr.co.farmstory.dto.CartDTO;
import kr.co.farmstory.entity.Cart;
import kr.co.farmstory.mapper.CartMapper;
import kr.co.farmstory.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;

    // 장바구니 항목 삽입
   /* public ResponseEntity<CartDTO> insertCart(CartDTO cartDTO){

        cartMapper.insertCart(cartDTO);
        return ResponseEntity.ok().body(cartDTO);
    }*/
    
    // 장바구니 삽입
    public void insertCart(String uid, int pno, int pcount) {
        List<Cart> existingCartItems = cartRepository.findCartByUidAndPno(uid, pno);

        if(existingCartItems.isEmpty()){
            // 해당 상품이 장바구니에 없으면 새로 추가
            Cart cartItem = new Cart();
            cartItem.setUid(uid);
            cartItem.setPno(pno);
            cartItem.setPcount(pcount);

            log.info("cartItem : " + cartItem);
            cartRepository.save(cartItem);
        } else {
            // 해당 상품이 장바구니에 있으면 수량 추가 또는 새로운 상품으로 추가
            for (Cart existingCartItem : existingCartItems) {
                if (existingCartItem.getPno() == pno) { // 상품 번호가 일치하는 경우
                    int newPcount = existingCartItem.getPcount() + pcount;
                    existingCartItem.setPcount(newPcount);
                    cartRepository.save(existingCartItem);

                    log.info("existingCartItem : " + existingCartItem);
                    break;
                }
            }
        }
    }


    public List<CartDTO> getCartByPno(String uid){
        return  cartMapper.getCartByPno(uid);
    }

    public int updateCart(int pcount){
        return cartMapper.updateCart(pcount);
    }

    // 장바구니 리스트 출력
    public List<CartDTO> selectCartList (String uid){
        return  cartMapper.selectCartList(uid);
    }
    
    // 장바구니 삭제
    public void deleteCartList(int pno, String uid) {
        cartMapper.deleteCartList(pno, uid);
    }
    public List<CartDTO> selectCartList2 (String uid){
        return  cartMapper.selectCartList2(uid);
    }

}












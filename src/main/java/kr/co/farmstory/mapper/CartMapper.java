package kr.co.farmstory.mapper;

import kr.co.farmstory.dto.CartDTO;
import kr.co.farmstory.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {


    public void insertCart(CartDTO cartDTO);
    public OrderDTO insertOrder(OrderDTO orderDTO);


    public List<CartDTO> getCartByPno(String uid);
    public int updateCart(int pcount);
    
    // 장바구니 리스트
    public List<CartDTO> selectCartList(String uid);
    public List<CartDTO> selectCartList2(String uid);

    // 장바구니 리스트 삭제
    public void deleteCartList(int pno, String uid);

}

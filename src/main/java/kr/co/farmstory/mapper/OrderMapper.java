package kr.co.farmstory.mapper;

import kr.co.farmstory.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    public List<OrderDTO> selectOrderlist(String uid);

}

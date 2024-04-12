package kr.co.farmstory.controller;

import kr.co.farmstory.dto.CartDTO;
import kr.co.farmstory.dto.OrderDTO;
import kr.co.farmstory.entity.Cart;
import kr.co.farmstory.service.CartService;
import kr.co.farmstory.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/cart/insert")
    public ResponseEntity<CartDTO> insertCartItem(Principal principal, @RequestBody CartDTO cartDTO) {
        String uid = principal.getName();
        log.info("uid : " + uid);
        int pno = cartDTO.getPno();
        int pcount = cartDTO.getPcount();
        log.info("insertCartPno : " + pno);
        cartService.insertCart(uid, pno, pcount);

        return ResponseEntity.ok(cartDTO);
    }

}

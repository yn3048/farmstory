package kr.co.farmstory.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.co.farmstory.dto.*;

import kr.co.farmstory.entity.Cart;
import kr.co.farmstory.entity.Order;
import kr.co.farmstory.entity.Orderlist;
import kr.co.farmstory.mapper.CartMapper;
import kr.co.farmstory.service.CartService;
import kr.co.farmstory.service.OrderService;
import kr.co.farmstory.service.ProductService;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.boot.Banner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller @RequiredArgsConstructor
public class MarketController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    
    @GetMapping("/market/list")
    public String list(Model model, Integer pageNum, Integer pageSize, String cate, String keyword){


        log.info("cate:" + cate);
        model.addAttribute("cate", cate);

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        PageHelper.startPage(pageNum, pageSize);
        List<ProductDTO> products = productService.selectProducts(cate, keyword);

        PageInfo<ProductDTO> productPage = new PageInfo<>(products);

        log.info(productPage.getPages() + "");

        int lastPage = (pageNum / 11) * 10 + 10;

        if (lastPage > productPage.getPages()) {
            lastPage = productPage.getPages();
        }
        productPage.setNavigateFirstPage((pageNum / 11) * 10 + 1);
        productPage.setNavigateLastPage(lastPage);

        log.info("selectProductPage" + productPage);

        model.addAttribute("productPage", productPage);
        model.addAttribute("products", products);

        return "/market/list";
    }

    // 상품 상세보기
    @GetMapping("/market/view")

    public String view(Model model, Integer pno, CartDTO cartDTO) {


        ProductDTO productDTO = productService.findById(pno);
        model.addAttribute(productDTO);

        int pcount = cartDTO.getPcount();
        model.addAttribute(pcount);
        log.info("Pcount from cartDTO1 :" + pcount);

        return "/market/view";
    }


    // 장바구니 목록
    @PostMapping("/market/cart")
    public String cart(Principal principal, CartDTO cartDTO, Model model) {
        log.info(cartDTO.getPno() + "dd");
        log.info(principal.getName());

        ProductDTO productDTO = productService.findById(cartDTO.getPno());
        model.addAttribute("count", cartDTO.getPcount());
        log.info("addCount:" + cartDTO.getPcount());

        model.addAttribute(productDTO);
        log.info(productDTO.toString());

        String uid = principal.getName();

        cartDTO.setUid(uid);
        log.info("uid:" + uid);

        model.addAttribute(cartDTO);

        List<CartDTO> cartDTOList = cartService.selectCartList2(uid);
        for (CartDTO cartDTO1 : cartDTOList) {
            log.info(cartDTO1.toString());
        }

        model.addAttribute(cartDTOList);

        return "/market/cart";
    }


    @ResponseBody
    @PostMapping(value = "/market/cart/delete")
    public ResponseEntity<List<CartDTO>> deleteCartList(Principal principal, @RequestParam("pno") List<Integer> pnos) {

        String uid = principal.getName();
        log.info("uid :" + uid);

        // 선택한 상품 삭제
        for (int pno : pnos) {
            cartService.deleteCartList(pno, uid);
        }
        log.info("deletePnos : " + pnos);

        // 삭제후 장바구니 리스트 조회
        List<CartDTO> cartDTOList = cartService.selectCartList2(uid);
        for (CartDTO cartDTO1 : cartDTOList) {
            log.info("selectCartList : " + cartDTO1);
        }

        log.info("cartList : " + cartDTOList);

        return ResponseEntity.ok(cartDTOList);
    }


    @GetMapping("/market/cart")
    public String goToCart(Principal principal, Model model) {

        String uid = principal.getName();
        List<CartDTO> cartDTOList = cartService.selectCartList2(uid);
        for (CartDTO cartDTO1 : cartDTOList) {
            log.info("selectCartList..1 : " + cartDTO1);
        }

        model.addAttribute("cartDTOList", cartDTOList);
        log.info("selectCartList..2 : " + cartDTOList);


        return "/market/cart";
    }


    @GetMapping("/market/cartEmpty")
    public String showEmptyCart(Model model, CartDTO cartDTO) {
        log.info("emptyCart..2");

        int defaultPcount = 0;
        model.addAttribute("pcount", defaultPcount);
        cartDTO.setPcount(defaultPcount);
        log.info("defaultPcount: " + defaultPcount);

        return "redirect:/market/cartEmpty";
    }


    @PostMapping("/market/order")
    public String order(Principal principal, CartDTO cartDTO, Model model){


        UserDTO userDTO = userService.selectUser(principal.getName());
        log.info(userDTO.toString());
        //cartService.insertCart(principal.getName(), cartDTO.getPno(), cartDTO.getPcount());

        List<CartDTO> cartDTOList = cartService.selectCartList2(principal.getName());

        model.addAttribute(userDTO);
        model.addAttribute(cartDTOList);
        return "/market/order";
    }

    @GetMapping("/market/order")
    public String order(Principal principal, Model model){


        UserDTO userDTO = userService.selectUser(principal.getName());
        log.info(userDTO.toString());

        List<CartDTO> cartDTOList = cartService.selectCartList2(principal.getName());

        model.addAttribute(userDTO);
        model.addAttribute(cartDTOList);
        return "/market/order";
    }


    @PostMapping("/market/order/save")
    public String orderSave(@RequestParam List<String> checkbox,
                            Principal principal,
                            OrderDTO orderDTO){

        log.info("ordersave...1 : "+principal.getName());
        String uid = principal.getName();
        orderDTO.setUid(uid);

        log.info("ordersave...2 : "+orderDTO);
        Order orderKey = orderService.insertOrder(orderDTO);
        log.info("ordersave...6, key"+orderKey.getOno());


        for(String index : checkbox){
            String[] values = index.split(",");
            OrderlistDTO orderlistDTO = new OrderlistDTO();
            if(values.length == 2){
                int pno = Integer.parseInt(values[0]);
                int pcount = Integer.parseInt(values[1]);

                log.info("pno : "+pno);
                log.info("pcount : "+pcount);
                orderlistDTO.setUid(uid);
                orderlistDTO.setPno(pno);
                orderlistDTO.setPcount(pcount);
                orderlistDTO.setOno(orderKey.getOno());
                log.info("반복문 orderlist : "+orderlistDTO);
                orderService.insertOrderList(orderlistDTO);
                log.info("insert");

                cartService.deleteCartList(pno, uid);
                log.info("delete : " +pno);
            }

            //String pcount = index.substring(index.lastIndexOf(","));
            log.info("orderlist : "+orderlistDTO);




        }
       return "redirect:/market/list";
    }

}

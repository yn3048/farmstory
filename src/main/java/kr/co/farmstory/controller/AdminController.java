package kr.co.farmstory.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.*;

import kr.co.farmstory.entity.Product;
import kr.co.farmstory.service.AdminService;
import kr.co.farmstory.service.ImgService;
import kr.co.farmstory.service.ProductService;
import kr.co.farmstory.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final ImgService imgService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping(value = {"/admin","/admin/index"})
    public String admin(Model model){

        List<UserDTO> users = adminService.adminIdxUsers();
        List<ProductDTO> products = adminService.adminIdxProducts();
        List<OrderlistDTO> orderlist = adminService.adminSelectOrderlimit();

        model.addAttribute("users", users);
        model.addAttribute("products", products);
        model.addAttribute("orderlist", orderlist);

        return "/admin/index";
    }

    // admin 회원 등록
    @GetMapping("/admin/user/register")
    public String adminReg(){
        return "/admin/user/register";
    }

    // admin 회원 등록
    @PostMapping("/admin/user/register")
    public String regAdmin(HttpServletRequest req, UserDTO userDTO) {

        String regIp = req.getRemoteAddr();
        userDTO.setRegip(regIp);

        log.info(userDTO.getRole());
        adminService.insertAdmin(userDTO);
        log.info(userDTO.toString());

        return "redirect:/user/login?success=200";

    }


    @ResponseBody
    @GetMapping("/admin/user/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                       @PathVariable("type") String type,
                                       @PathVariable("value") String value) {
        int count = userService.selectCountUser(type, value);
        log.info("count :" + count);

        // 중복 없으면 이메일 인증코드 발송
        if(count == 0 && type.equals("email")) {
            log.info("email : " + value);
            userService.sendEmailConde(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);

    }

    // 이메일 인증 코드 검사
    @ResponseBody
    @GetMapping("/admin/email/{code}")
    public ResponseEntity<?> checkEmail(HttpSession session, @PathVariable("code") String code) {

        String sessionCode = (String) session.getAttribute("code");

        if(sessionCode.equals(code)) {
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }

    }



    // admin.user.list 출력
    @GetMapping("/admin/user/list")
    public String adminUserlist(Model model, Integer pageNum, Integer pageSize){

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        // pageHelper를 사용하여 페이징 시작(1~10)
        PageHelper.startPage(pageNum, pageSize);
        List<UserDTO> adminUsers = adminService.adminSelectUsers();

        PageInfo<UserDTO> adminUserPage = new PageInfo<>(adminUsers);
        log.info("adminUsers" + adminUserPage);

        model.addAttribute("adminUsers", adminUsers);
        model.addAttribute("adminUserPage", adminUserPage);

        return "/admin/user/list";
    }

    //관리자 회원삭제
    @PostMapping("/admin/user/delete")
    public String adminUserDelete(@RequestParam List<String> checkbox){
        for(String uid : checkbox){
            adminService.adminUserDelete(uid);
        }
        log.info(checkbox.toString());
        return "redirect:/admin/user/list";
    }

    
    //관리자 상품삭제
    @PostMapping("/admin/product/delete")
    public String adminProductDelete(@RequestParam List<String> checkbox){
        log.info("productDelete....1");
        for(String pno : checkbox){
            int productId = Integer.parseInt(pno);
            adminService.adminProductDelete(productId);
        }
        log.info(checkbox.toString());
        return "redirect:/admin/product/list";
    }


    // admin.user.modify
    @GetMapping("/admin/user/{uid}")
    public String adminUserModify(@PathVariable String uid, Model model){
        UserDTO user = adminService.adminSelectUser(uid);
        model.addAttribute("user", user);
        return "/admin/user/modify";
    }

    @PostMapping("/admin/user")
    public String updateUser(UserDTO userDTO){
        adminService.adminUpdateUser(userDTO);
        return "redirect:/admin/user/list";
    }

    // admin.product.list 출력
    @GetMapping("/admin/product/list")
    public String adminProductlist(Model model, Integer pageNum, Integer pageSize){


        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        // pageHelper를 사용하여 페이징 시작(1~10)
        PageHelper.startPage(pageNum, pageSize);
        List<ProductDTO> adminProducts = adminService.adminSelectProducts();

        PageInfo<ProductDTO> adminProductPage = new PageInfo<>(adminProducts);
        log.info("adminProducts" + adminProductPage);

        model.addAttribute("adminProducts", adminProducts);
        model.addAttribute("adminProductPage", adminProductPage);

        return "/admin/product/list";
    }


    @GetMapping("/admin/order/list")
    public String adminOrderlist(Model model, Integer pageNum, Integer pageSize){

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        PageHelper.startPage(pageNum, pageSize);
        List<OrderlistDTO> adminOrder = adminService.adminSelectOrder();

        PageInfo<OrderlistDTO> adminOrderPage = new PageInfo<>(adminOrder);
        log.info("adminOrderPage : " +adminOrderPage);

        model.addAttribute("adminOrder", adminOrder);
        log.info("adminOrder : " +adminOrder);
        model.addAttribute("adminOrderPage", adminOrderPage);

        return "/admin/order/list";
    }



    @GetMapping("/admin/product/register")
    public String productRegister(){

        return "/admin/product/register";
    }
/*
    @PostMapping("/admin/product/register")
    public String productregister(HttpServletRequest req, ProductDTO productDTO) {


        log.info("" + productDTO);


        productService.insertProduct(productDTO);

        return "redirect:/admin/product/register?success=200";
    }
*/
    @PostMapping("/admin/product/register")
    public String productRegister(ProductDTO productDTO,
                                  @RequestParam("imgMain") MultipartFile fileA,
                                  @RequestParam("imgSub1") MultipartFile fileB,
                                  @RequestParam("imgSub2") MultipartFile fileC){
        log.info("productRegister");
        log.info(""+productDTO);



        log.info(""+productDTO);



        List<MultipartFile> files = new ArrayList<>();
        files.add(fileA);
        files.add(fileB);
        files.add(fileC);

        ImgDTO imgDTO = new ImgDTO();

        imgDTO.setPno(productDTO.getPno());
        imgDTO.setFiles(files);


        imgService.imgUpload(imgDTO, productDTO.getCate());
        Product product = productService.insertProduct(productDTO);
        imgDTO.setPno(product.getPno());
        imgService.insertImg(imgDTO);
        return "redirect:/admin/product/register?success=200";
    }

    //주문목록 삭제
    @PostMapping("/admin/order/delete")
    public String adminOrderDelete(@RequestParam List<String> checkbox){
        for(String ono : checkbox){
            int orderid = Integer.parseInt(ono);
            adminService.adminOrderDelete(orderid);
        }
        log.info(checkbox.toString());
        return "redirect:/admin/order/list";
    }

}

package kr.co.farmstory.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.farmstory.dto.OrderDTO;
import kr.co.farmstory.dto.TermsDTO;
import kr.co.farmstory.dto.UserDTO;
import kr.co.farmstory.service.OrderService;
import kr.co.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final OrderService orderService;


    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/checkLogin")
    public String checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return "로그인 상태입니다.";
        }else {
            return "로그인 되지 않았습니다.";
        }
    }

    @GetMapping("/user/modify")
    public String modify(String uid, Model model){
        UserDTO user = userService.selectUser(uid);
        model.addAttribute("user", user);
        return "/user/modify";
    }

    // 사용자 정보 수정
    @PostMapping("/user")
    public String updateUser(UserDTO userDTO){
        userService.updateUser(userDTO);
        return "redirect:/user/modify?uid=" + userDTO.getUid();
    }

   // 사용자 주문 조회
    @GetMapping("/user/orderlist")
   public String orderList(Model model, String uid , Integer pageNum, Integer pageSize){

        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 5 : pageSize;

        PageHelper.startPage(pageNum, pageSize);

        List<OrderDTO> orderDTOList = orderService.selectOrderlist(uid);

        PageInfo<OrderDTO> orderPage = new PageInfo<>(orderDTOList);

        log.info(" " + orderPage.getPages());

        int lastPage = (pageNum / 11) * 10 + 10;

        if (lastPage > orderPage.getPages()) {
            lastPage = orderPage.getPages();
        }

        orderPage.setNavigateFirstPage((pageNum / 11) * 10 +1);
        orderPage.setNavigateLastPage(lastPage);

        log.info("selectOrderListPage" + orderPage);

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("orderDTOList", orderDTOList);

        log.info("orderDTOList : "+ orderDTOList.toString());

        model.addAttribute(orderDTOList);

        return "/user/orderlist";
   }


    @GetMapping("/user/register")
    public String register(String sms, Model model) {
        model.addAttribute("sms", sms);
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String registerUser(HttpServletRequest req, UserDTO userDTO, @RequestParam String sms) {

        String regip = req.getRemoteAddr();
        userDTO.setRegip(regip);

        userDTO.setRole("USER");
        userService.insertUser(userDTO);
        log.info(userDTO.toString());


        return "redirect:/user/login?success=200";

    }

    @ResponseBody
    @GetMapping("/user/{type}/{value}")
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
    @GetMapping("/email/{code}")
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

    @GetMapping("/user/terms")
    public String terms(Model model){

        TermsDTO termsDTO = userService.selectTerms();
        model.addAttribute(termsDTO);

        return "/user/terms";
    }



}

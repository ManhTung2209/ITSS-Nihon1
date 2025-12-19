package com.itss.cafe_finder.controller;

import dto.CafeDTO;
import dto.DishDTO;
import dto.ReviewDTO;
import com.itss.cafe_finder.model.User;
import com.itss.cafe_finder.service.CafeDetailService;
import com.itss.cafe_finder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cafes")
public class CafeDetailController {
    
    private static final Logger logger = LoggerFactory.getLogger(CafeDetailController.class);

    @Autowired
    private CafeDetailService cafeDetailService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public String getCafeDetailPage(@PathVariable Long id, Model model, HttpSession session) {
        logger.info("Accessing cafe detail page: {}", id);
        
        CafeDTO cafe = cafeDetailService.getCafeDetail(id);
        List<DishDTO> dishes = cafeDetailService.getDishes(id);
        List<ReviewDTO> reviews = cafeDetailService.getReviews(id);

        if (cafe == null) {
            logger.warn("Cafe not found: {}", id);
            return "error";
        }

        model.addAttribute("cafe", cafe);
        model.addAttribute("dishes", dishes != null ? dishes : List.of());
        model.addAttribute("reviews", reviews != null ? reviews : List.of());
        
        // SessionInterceptor sẽ tự động add isLoggedIn vào model
        // Không cần thêm ở đây nữa

        return "details";
    }
    
    @PostMapping("/{id}/reviews")
    public String saveReview(@PathVariable Long id, 
                           @RequestParam("star") Integer star,
                           @RequestParam("content") String content,
                           HttpSession session) {
        
        logger.info("Saving review for cafe: {}", id);
        
        // Lấy user ID từ session
        Long userId = (Long) session.getAttribute("userId");
        logger.info("User ID from session: {}", userId);
        
        if (userId == null) {
            logger.warn("User not logged in - redirecting to login");
            return "redirect:/login";
        }
        
        // Lấy user từ database
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            logger.warn("User not found in database: {}", userId);
            return "redirect:/login";
        }
        
        logger.info("User found: {} ({})", user.getName(), user.getId());
        
        // Lưu review với user đang đăng nhập
        cafeDetailService.saveReview(id, user, star, content);
        
        logger.info("Review saved successfully");
        return "redirect:/cafes/" + id;
    }
}
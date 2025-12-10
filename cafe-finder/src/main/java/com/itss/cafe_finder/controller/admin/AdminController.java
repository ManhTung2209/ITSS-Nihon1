package com.itss.cafe_finder.controller.admin;

import dto.admin.AdminCafeDTO;
import dto.admin.AdminReviewDTO;
import dto.admin.AdminUserDTO;
import dto.admin.DashboardStatsDTO;
import com.itss.cafe_finder.model.enums.CafeStatusType;
import com.itss.cafe_finder.model.enums.ReviewStatusType;
import com.itss.cafe_finder.model.enums.UserRoleType;
import com.itss.cafe_finder.model.enums.UserStatusType;
import com.itss.cafe_finder.service.admin.AdminCafeService;
import com.itss.cafe_finder.service.admin.AdminDashboardService;
import com.itss.cafe_finder.service.admin.AdminReviewService;
import com.itss.cafe_finder.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDashboardService dashboardService;

    @Autowired
    private AdminUserService userService;

    @Autowired
    private AdminCafeService cafeService;

    @Autowired
    private AdminReviewService reviewService;

    // Dashboard page
    @GetMapping({"", "/", "/index"})
    public String dashboard(Model model) {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        model.addAttribute("stats", stats);
        return "admin/index";
    }

    // Users page
    @GetMapping("/users")
    public String usersPage(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) String roleType,
                           @RequestParam(required = false) String status) {
        UserRoleType roleTypeEnum = roleType != null ? UserRoleType.valueOf(roleType) : null;
        UserStatusType statusEnum = status != null ? UserStatusType.valueOf(status) : null;
        
        Page<AdminUserDTO> users = userService.getAllUsers(page, size, keyword, roleTypeEnum, statusEnum);
        model.addAttribute("users", users);
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("activeUsers", userService.getActiveUsers());
        model.addAttribute("bannedUsers", userService.getBannedUsers());
        model.addAttribute("adminUsers", userService.getAdminUsers());
        return "admin/user";
    }

    // Cafes page
    @GetMapping("/cafes")
    public String cafesPage(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) String status) {
        CafeStatusType statusEnum = status != null ? CafeStatusType.valueOf(status) : null;
        
        Page<AdminCafeDTO> cafes = cafeService.getAllCafes(page, size, keyword, statusEnum);
        model.addAttribute("cafes", cafes);
        return "admin/cafe";
    }

    // Reviews page
    @GetMapping("/reviews")
    public String reviewsPage(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) Integer star) {
        ReviewStatusType statusEnum = status != null ? ReviewStatusType.valueOf(status) : null;
        
        Page<AdminReviewDTO> reviews = reviewService.getAllReviews(page, size, keyword, statusEnum, star);
        model.addAttribute("reviews", reviews);
        model.addAttribute("pendingReviews", reviewService.getPendingReviewsCount());
        model.addAttribute("publishedReviews", reviewService.getPublishedReviewsCount());
        return "admin/review";
    }

    // API Endpoints for AJAX calls

    @GetMapping("/api/stats")
    @ResponseBody
    public ResponseEntity<DashboardStatsDTO> getStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/api/users")
    @ResponseBody
    public ResponseEntity<Page<AdminUserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String roleType,
            @RequestParam(required = false) String status) {
        UserRoleType roleTypeEnum = roleType != null ? UserRoleType.valueOf(roleType) : null;
        UserStatusType statusEnum = status != null ? UserStatusType.valueOf(status) : null;
        return ResponseEntity.ok(userService.getAllUsers(page, size, keyword, roleTypeEnum, statusEnum));
    }

    @GetMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<AdminUserDTO> getUser(@PathVariable Long id) {
        AdminUserDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/api/users/{id}/status")
    @ResponseBody
    public ResponseEntity<AdminUserDTO> updateUserStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        AdminUserDTO user = userService.updateUserStatus(id, UserStatusType.valueOf(status));
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/api/users/{id}/role")
    @ResponseBody
    public ResponseEntity<AdminUserDTO> updateUserRole(
            @PathVariable Long id,
            @RequestParam String roleType) {
        AdminUserDTO user = userService.updateUserRole(id, UserRoleType.valueOf(roleType));
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/api/cafes")
    @ResponseBody
    public ResponseEntity<Page<AdminCafeDTO>> getCafes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        CafeStatusType statusEnum = status != null ? CafeStatusType.valueOf(status) : null;
        return ResponseEntity.ok(cafeService.getAllCafes(page, size, keyword, statusEnum));
    }

    @GetMapping("/api/cafes/{id}")
    @ResponseBody
    public ResponseEntity<AdminCafeDTO> getCafe(@PathVariable Long id) {
        AdminCafeDTO cafe = cafeService.getCafeById(id);
        return cafe != null ? ResponseEntity.ok(cafe) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api/cafes")
    @ResponseBody
    public ResponseEntity<AdminCafeDTO> createCafe(@RequestBody AdminCafeDTO dto) {
        return ResponseEntity.ok(cafeService.createCafe(dto));
    }

    @PutMapping("/api/cafes/{id}")
    @ResponseBody
    public ResponseEntity<AdminCafeDTO> updateCafe(
            @PathVariable Long id,
            @RequestBody AdminCafeDTO dto) {
        AdminCafeDTO cafe = cafeService.updateCafe(id, dto);
        return cafe != null ? ResponseEntity.ok(cafe) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/cafes/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        boolean deleted = cafeService.deleteCafe(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/api/reviews")
    @ResponseBody
    public ResponseEntity<Page<AdminReviewDTO>> getReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer star) {
        ReviewStatusType statusEnum = status != null ? ReviewStatusType.valueOf(status) : null;
        return ResponseEntity.ok(reviewService.getAllReviews(page, size, keyword, statusEnum, star));
    }

    @GetMapping("/api/reviews/{id}")
    @ResponseBody
    public ResponseEntity<AdminReviewDTO> getReview(@PathVariable Long id) {
        AdminReviewDTO review = reviewService.getReviewById(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @PutMapping("/api/reviews/{id}/status")
    @ResponseBody
    public ResponseEntity<AdminReviewDTO> updateReviewStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        AdminReviewDTO review = reviewService.updateReviewStatus(id, ReviewStatusType.valueOf(status));
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/reviews/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        boolean deleted = reviewService.deleteReview(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/api/reviews/approve-all")
    @ResponseBody
    public ResponseEntity<java.util.Map<String, Object>> approveAllPendingReviews() {
        int count = reviewService.approveAllPendingReviews();
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("count", count);
        response.put("message", count + "件のレビューを承認しました");
        return ResponseEntity.ok(response);
    }
}


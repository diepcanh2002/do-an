package com.traicau.pitch.controller.mvc;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
    @GetMapping("/admin/pitch-management")
    public String home() {
        return "pages/admin/pitch-management";
    }
    @GetMapping("/admin/pricing-slot-management")
    public String pricingSlot() {
        return "pages/admin/pricing-slot-management";
    }
    @GetMapping("/admin/match-management")
    public String match() {
        return "pages/admin/match-management";
    }
    @GetMapping("/admin/booking-management")
    public String booking() {
        return "pages/admin/booking-management";
    }
    @GetMapping("/admin/payment-management")
    public String payment() {
        return "pages/admin/payment-management";
    }
    @GetMapping("/admin/team-management")
    public String team() {
        return "pages/admin/team-management";
    }
    @GetMapping("/admin/user-management")
    public String user() {
        return "pages/admin/user-management";
    }
    @GetMapping("/admin/")
    public String report() {
        return "pages/admin/report";
    }


    //common
    @GetMapping ("/login")
    public String login(){
        return "pages/common/login";
    }
    @GetMapping ("/register")
    public String register(){
        return "pages/common/register";
    }
    @GetMapping ("/")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "home";
    }
    @GetMapping ("/matches")
    public String matches(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "pages/common/matches";
    }

    @GetMapping("/field-detail")
    public String pitchDetail(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "pages/common/field-detail";
    }

    //user
    @GetMapping ("/user/field-booking")
    public String fieldBooking(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "pages/user/field-booking";
    }

    @GetMapping ("/user/notification")
    public String notification(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "pages/user/notification";
    }
    @GetMapping ("/user/booking-bill")
    public String bookingBill(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "pages/user/booking-bill";
    }

    @GetMapping ("/user/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "pages/user/profile";
    }


}

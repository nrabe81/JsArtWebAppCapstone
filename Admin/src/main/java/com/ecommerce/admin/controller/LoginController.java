package com.ecommerce.admin.controller;


import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController
{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminServiceImpl adminService;

    //Mapping for the default landing page for Admin login
    @GetMapping("/login")
    public String loginForm(Model model)
    {
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping("/index")
    public String home(Model model)
    {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
        {
            return "redirect:/login";
        }

        return "index";
    }

    //Mapping for register page for Admin
    @GetMapping("/register")
    public String register(Model model)
    {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    //Mapping for Admin forgot password
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model)
    {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto")AdminDto adminDto, BindingResult result,
                              Model model)
    {
        try{
//            //reset or clear message so new message can appear each time
//            session.removeAttribute("message");

            if(result.hasErrors())
            {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }

            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            //if the email has already been registered
            if(admin != null)
            {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Your email has already been registered");
                System.out.println("Admin not null");
                return "register";
            }

            //as long as both passwords match
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword()))
            {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("success", "Registered successfully");
                System.out.println("successfully saved password");
            }
            else
            {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Passwords do not match");
                System.out.println("Password not same");
                return "register";
            }

        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            model.addAttribute("errors", "Error. Please Try Again");
        }

        return "register";
    }
}

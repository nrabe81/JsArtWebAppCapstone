package com.ecommerce.admin.controller;

import com.ecommerce.library.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class LoginControllerTest {

    private LoginController controller;
    private BCryptPasswordEncoder encoder;
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        encoder = new BCryptPasswordEncoder();
        adminService = mock(AdminServiceImpl.class);
        controller = new LoginController();
    }

    @Test
    public void testLoginForm()
    {
        Model model = mock(Model.class);
        String viewName = controller.loginForm(model);
        assertEquals("login", viewName);
    }

    @Test
    public void testHome()
    {
        Model model = mock(Model.class);
        Authentication auth = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String viewName = controller.home(model);
        assertEquals("index", viewName);
    }

    @Test
    public void testRegister()
    {
        Model model = mock(Model.class);
        String viewName = controller.register(model);
        assertEquals("register", viewName);
    }

}

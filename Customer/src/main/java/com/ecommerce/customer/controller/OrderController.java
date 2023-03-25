package com.ecommerce.customer.controller;

import com.ecommerce.library.model.ChargeRequest;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController
{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    //Stripe Test public key
//    @Value("${pk_test_51MovtkBwzfKoO9qzUOj62X5Oz8l8XpQzmlUh2tsMN23HrHjr1iC18qK47lMj0xdLQgsBafmrQnNSztfOZ8VxWz9200uCBbaItK}")
    private String stripePublicKey = "pk_test_51MovtkBwzfKoO9qzUOj62X5Oz8l8XpQzmlUh2tsMN23HrHjr1iC18qK47lMj0xdLQgsBafmrQnNSztfOZ8VxWz9200uCBbaItK";


    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty())
        {
            model.addAttribute("customer", customer);
            model.addAttribute("error", "Please update your information before checking out");
            return "account";
        }
        else
        {
            model.addAttribute("customer", customer);
            ShoppingCart cart = customer.getShoppingCart();
            model.addAttribute("cart", cart);
            model.addAttribute("amount", Integer.valueOf((int) ((cart.getTotalPrices() * 100) + (cart.getTotalPrices() * 100 * 0.085)))); // in cents
            model.addAttribute("stripePublicKey", stripePublicKey);
            model.addAttribute("currency", ChargeRequest.Currency.USD);
        }

        return "checkout";
    }


    @GetMapping("/order")
    public String order(Principal principal, Model model)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders", orderList);

        return "order-complete";
    }


    @GetMapping("/save-order")
    public String saveOrder(Principal principal)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        ShoppingCart cart = customer.getShoppingCart();
        orderService.saveOrder(cart);

        return "redirect:/order";
    }

    @GetMapping("/past-orders")
    public String pastOrders(Principal principal, Model model)
    {
        if(principal == null)
        {
            return "redirect:/login";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);

        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders", orderList);

        return "past-orders";
    }



}

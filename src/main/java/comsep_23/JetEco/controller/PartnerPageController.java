package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
   @RequestMapping("/partner-page")
   public class PartnerPageController {
       @GetMapping
       public String showPartnerPage() {
           return "partner-page";
       }
}

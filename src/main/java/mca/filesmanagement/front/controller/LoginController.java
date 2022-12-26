package mca.filesmanagement.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	public LoginController() {
		super();
	}
	
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        // ...

        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        HttpSession hs = request.getSession();
        hs.invalidate();
        return "login";
    }
    
    @GetMapping("/403")
    public String get403Page(Model model) {
        // ...

        return "403";
    }
}

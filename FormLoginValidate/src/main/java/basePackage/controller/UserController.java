package basePackage.controller;

import basePackage.model.User;
import basePackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public ModelAndView inputUser(){
        return new ModelAndView("user/form","user",new User());
    }
    @PostMapping("/user")
    public ModelAndView input(@Validated @ModelAttribute(name = "user")User user, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return new ModelAndView("user/form","user",user);
        }else {
            userService.save(user);
            return new ModelAndView("user/result","user",user);
        }
    }
}

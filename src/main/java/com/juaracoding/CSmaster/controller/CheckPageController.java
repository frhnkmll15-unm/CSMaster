package com.juaracoding.CSmaster.controller;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.CSmaster.dto.ForgetPasswordDTO;
import com.juaracoding.CSmaster.dto.UserDTO;
import com.juaracoding.CSmaster.model.Userz;
import com.juaracoding.CSmaster.utils.CaptchaUtils;
import com.juaracoding.CSmaster.utils.MappingAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.cache.AbstractCacheManager;
import org.thymeleaf.cache.ExpressionCacheKey;
import org.thymeleaf.cache.ICache;
import org.thymeleaf.cache.TemplateCacheKey;
import org.thymeleaf.engine.TemplateModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/check")
public class CheckPageController  {

    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    private MappingAttribute mappingAttribute = new MappingAttribute();

    @GetMapping("/signin")
    public String pageOne(Model model,HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        Captcha captcha = CaptchaUtils.createCaptcha(150, 60);
        Userz users = new Userz();
        users.setHidden(captcha.getAnswer());
        users.setCaptcha("");
        users.setImage(CaptchaUtils.encodeBase64(captcha));
        model.addAttribute("usr",users);
        return "authz_signin";
    }

    @GetMapping("/register")
    public String pageTwo(Model model)
    {
        UserDTO users = new UserDTO();
        model.addAttribute("usr",users);
        return "authz_register";
    }

    @GetMapping("/verify")
    public String pageThree(Model model)
    {
        model.addAttribute("usr",new Userz());
        return "authz_verifikasi";
    }

    @GetMapping("/index1")
    public String pageFour(Model model, WebRequest request,HttpServletResponse response)
    {
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);
        mappingAttribute.setAttribute(model,objectMapper,request);
        return "index_1";

    }
    @GetMapping("/styledPage")
    public String pageFive(Model model)
    {
        model.addAttribute("name", "Paulo");
        return "styledPage";
    }

    @GetMapping("/index")
    public String pageSix()
    {
        return "index";
    }

    @GetMapping("/forgetpwd")
    public String pageSeven(Model model)
    {
        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        model.addAttribute("forgetpwd",forgetPasswordDTO);
        return "authz_forget_pwd_email";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return "redirect:/api/check/signin";
    }

}
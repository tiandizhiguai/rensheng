package com.example.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.constant.Constants;
import com.example.controller.AbstractController;
import com.example.param.UserParam;
import com.example.service.UserService;
import com.example.vo.User;

/**
 * 用户信息模型。
 * 
 * @author Administrator 2015年8月8日 下午9:55:25
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Resource
	private UserService userServie;

    @RequestMapping("/preRegister")
    public ModelAndView preRegister(UserParam param) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/preRegister");
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView register(UserParam param) {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isEmpty(param.getLoginName()) || StringUtils.isEmpty(param.getPasswd())) {
            modelAndView.setViewName("/user/registerError");
            return modelAndView;
        }

        userServie.add(param);
        User loginUser = userServie.get(param.getLoginName());
        if (loginUser != null && StringUtils.endsWithIgnoreCase(param.getLoginName(), loginUser.getLoginName())) {
            httpSession.setAttribute(Constants.LOGIN_USER, loginUser);
            modelAndView.setViewName("/user/registerSuccess");
        } else {
            modelAndView.setViewName("/user/registerError");
        }

        return modelAndView;
    }

    @RequestMapping("/preLogin")
    public ModelAndView preLogin(String redirectUri) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("redirectUri", redirectUri);
        modelAndView.setViewName("/user/preLogin");

        return modelAndView;
    }

    @RequestMapping("/login")
	public ModelAndView login(UserParam param) {
		ModelAndView modelAndView =  new ModelAndView();
        User loginUser = userServie.get(param);
        if (loginUser != null && StringUtils.endsWithIgnoreCase(param.getLoginName(), loginUser.getLoginName())) {
            httpSession.setAttribute(Constants.LOGIN_USER, loginUser);
            if (!StringUtils.isEmpty(param.getRedirectUri())) {
                modelAndView.setViewName("redirect:" + param.getRedirectUri());
            }else{
                modelAndView.setViewName("redirect:/index");
            }
		}else{
            modelAndView.setViewName("/user/loginError");
		}
		return modelAndView;
	}

    @RequestMapping("/logout")
    public ModelAndView logout(UserParam param) {
        httpSession.removeAttribute(Constants.LOGIN_USER);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }
}
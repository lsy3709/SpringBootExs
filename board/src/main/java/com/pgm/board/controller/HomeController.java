package com.pgm.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pgm.board.model.User;
import com.pgm.board.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value={"","index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("join")
	public String joinForm() {
		return "member/join";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sUser");
		return "redirect:/board/list";
	}
	

	@PostMapping("join")
	@ResponseBody
	public String register(@RequestBody User user) {
		
		if(userService.findByUsername(user.getUsername())!=null) {
			return "fail";
		}
		userService.register(user);
		return "success";
	}
	
	@GetMapping("login")
	public String loginForm() {
		return "member/login";
	}
	
	@PostMapping("login")
	@ResponseBody
	public String loginPro(String username, String password,HttpSession session) {
		User user=userService.findByUsername(username);
		String result="no";
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				session.setAttribute("sUser", user);
				result="success";
			}else {
				result="fail";
			}
		}
		return result;
	}

}

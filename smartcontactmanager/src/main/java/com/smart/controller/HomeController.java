package com.smart.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;
/*import com.smart.dao.UserRepository;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
*/
@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	/*
	@GetMapping("/test")
	@ResponseBody()
	public String test()
	{
		User user = new User();
		user.setName("Girjesh");
		user.setEmail("girjesh62586@gmail.com");
		user.setId(12345);
		userRepository.save(user);
		return "Running";
	}*/
	
	@RequestMapping("")
	public String home(Model m)
	{
		m.addAttribute("title","Home - Smart Contact Manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model m)
	{
		m.addAttribute("title","About - Smart Contact Manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model m)
	{
		m.addAttribute("title","SignUp - Smart Contact Manager");
		m.addAttribute("user", new User());
		return "signup";
	}
	@RequestMapping("/login_page")
	public String login(Model m)
	{
		m.addAttribute("title","Login - Smart Contact Manager");
		return "login_page";
	}
	
	//this is a Handler for user
	@RequestMapping(value ="/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, @RequestParam(value ="agreement",defaultValue="false") boolean agreement,Model m,HttpSession session)
	{
		try
		{
		
			if(!agreement)
			{
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("Default Image");
			
			user.setId(123);
			User result = this.userRepository.save(user);
			System.out.println("Agreement : "+agreement);
			System.out.println("User : "+user);
			session.setAttribute("message",new Message("Successfully Register !!" ,"alert-success"));
			m.addAttribute("user",new User());
			return "signup";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			m.addAttribute("user",user);
			session.setAttribute("message",new Message("Something Went Wrong !!" + e.getMessage(),"alert-danger"));
			return "signup";
		}		
		
	}
	
	
	

}

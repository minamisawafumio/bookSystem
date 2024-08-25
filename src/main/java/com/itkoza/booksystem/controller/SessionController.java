package com.itkoza.booksystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itkoza.data.UserData;

@Controller
@RequestMapping("/session")
public class SessionController {

  @GetMapping("/set")
  public String setSessionAttribute(HttpSession session) {
	  System.out.println("-- SessionController setSessionAttribute --");
	  session.setAttribute("message", "これはセッションのメッセージです！！");
	  return "redirect:/session/show";
  }
  
  @GetMapping("/set2")
  public String set2(HttpSession session) {
	  System.out.println("-- SessionController setSessionAttribute --");
	  session.setAttribute("message", "〇〇〇〇〇");
	  session.setAttribute("aaaabbbb", "RRRRRRR");
	  return "redirect:/session/show";
  }
  
  @PostMapping("/btn_user_0001")
  public String btn_user_0001(HttpSession session, Model model, @RequestParam Map<String, Object> params) {
	  System.out.println("-- SessionController btn_user_0001 --");

	  String name = (String) params.get("name");
	  String age  = (String) params.get("age");
	  
	  session.setAttribute("name", name);
	  session.setAttribute("age", age);
	  
	  return "redirect:/session/show";
  }


  @GetMapping("/show")
  public String showSessionAttribute(HttpSession session, Model model) {
	  System.out.println("-- SessionController showSessionAttribute --");
	  String message = (String) session.getAttribute("message");
	  model.addAttribute("sessionMessage", message);

	  String message2 = (String) session.getAttribute("aaaabbbb");
	  model.addAttribute("aaaabbbb", message2);


	  
	  //----------------------------------------------------------------
	  List<UserData> displayData = new ArrayList<UserData>();
	    
	  // サンプルとして3名のユーザーを追加
	  displayData.add(new UserData("太郎", 18));
	  displayData.add(new UserData("次郎", 21));
	  displayData.add(new UserData("三郎", 14));
	    
	  model.addAttribute("users", displayData);
	  //----------------------------------------------------------------
	  String name = (String) session.getAttribute("name");
	  String age = (String) session.getAttribute("age");
	  model.addAttribute("name", name);
	  model.addAttribute("age", age);
	  
	  
	  
	  
	  return "sessionView";
  }
}
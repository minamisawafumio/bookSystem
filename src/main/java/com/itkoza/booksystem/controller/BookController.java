package com.itkoza.booksystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	@GetMapping("/book")
	public String test() {
		return "aaaaaaaaa";
	}

	@GetMapping(value = "/user/list")
	public String list(Model model) {
		List<String> list = new ArrayList();
		list.add("ああああああああああああああああああ");
		list.add("いいいいいいいいいいいいいいいいいい");
		model.addAttribute("userList", list);
		return "user/list";
	}
}

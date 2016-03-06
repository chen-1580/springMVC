package com.ckx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mouse")
public class MouseController {
	
	@RequestMapping("/run")
	public String mouseRun(){
		return "mouse";
	}
}

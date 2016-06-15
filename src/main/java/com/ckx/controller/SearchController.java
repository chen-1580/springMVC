package com.ckx.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ckx.service.IEsSearchService;

@Component
@RequestMapping("/")
public class SearchController {

	@Autowired
	private IEsSearchService service;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String toHomePage() {
		String result = service.search("name", "马来西亚");
		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		return "mouse";
	}

	@RequestMapping(value = "/search/{data}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String search(HttpServletRequest request, @PathVariable String data) {
		String encoding = request.getCharacterEncoding();
		System.out.println(encoding);
		String result = service.search("name", data);
		System.out.println(result);
		return result;
	}

}

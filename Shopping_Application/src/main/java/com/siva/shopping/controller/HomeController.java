package com.siva.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.siva.shopping.model.Category;
import com.siva.shopping.model.Product;
import com.siva.shopping.model.User;
import com.siva.shopping.service.ICategoryService;
import com.siva.shopping.service.IProductService;
import com.siva.shopping.service.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IUserService userService;
	
	@ModelAttribute
	public void commonData(Model model) {
		List<Category> categories = categoryService.activeCategories();
		model.addAttribute("categories", categories);
	}
	 
	@GetMapping("/")
	public String indexPage(Model model) {
		List<Category> activeCategories = categoryService.activeCategories().stream().sorted((c1, c2) -> c2.getId().compareTo(c1.getId())).limit(6).toList();
		List<Product> activeProducts = productService.activeProducts().stream().sorted((p1, p2) -> p2.getId().compareTo(p1.getId())).limit(8).toList();
		model.addAttribute("category",  activeCategories);
		model.addAttribute("products", activeProducts);
		return "Index";
	}
	
	@GetMapping("/signin")
	public String loginPage() {
		return "Login";
	}
	
	@GetMapping("/signup") 
	public String singupPage() {
		return "Registration";
	}
	
	@GetMapping("/products")
	public String productPage(Model model, @RequestParam(defaultValue="", value="category") String category, @RequestParam(defaultValue="0") int pageNo) {
		Page<Product> page = productService.getProductsByCategory(category, pageNo);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("products", page.getContent());
		model.addAttribute("isSearch", false);
		model.addAttribute("paramValue", category);
		return "Product";
	}
	
	@GetMapping("/product/{id}")
	public String viewProduct(Model model, @PathVariable("id") int id) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "ViewProduct";
	}
	
	@GetMapping("/admin")
	public String adminLoginPage() {
		return "AdminLogin";
	}
	
	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "ForgotPassword";
	}
	
	@PostMapping("/checkEmailAndMobileNumber")
	public String checkEmailAndMobileNumber(HttpSession session, @RequestParam("email") String email, @RequestParam("mobileNumber") String mobileNumber) {
		User user = userService.getUserByEmailAndMobileNumber(email, mobileNumber);
		if (user!=null) {
			session.setAttribute("getUser", user);
			return "redirect:/next";
		}
		else {
			session.setAttribute("failMsg", "Invalid credientials");
			return "redirect:/forgotpassword";
		}
	}
	
	@GetMapping("/next")
	public String forgotPassword1(HttpSession session) {
		User user = (User) session.getAttribute("getUser");
		if (user!=null) {
			return "ForgotPassword1";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/changePassword")
	public String changePassword(HttpSession session, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
		User user = (User) session.getAttribute("getUser");
		if (user!=null) {
			if (newPassword.equals(confirmPassword)) {
				user.setPassword(newPassword);
				User saveUser = userService.saveUser(user);
				if (saveUser!=null) {
					session.setAttribute("successMsg", "password has been changed");
				}
				else {
					session.setAttribute("failMsg", "something went wrong");
				}
				session.removeAttribute("getUser");
				return "redirect:/signin";
			}
			else {
				session.setAttribute("failMsg", "Both passwords most be same");
				return "redirect:/next";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/search")
	public String searchProduct(@RequestParam("ch") String ch, Model model) {
		List<Product> searchProducts = productService.searchProducts(ch).stream().sorted((p1, p2) -> p2.getId().compareTo(p1.getId())).collect(Collectors.toList());
		model.addAttribute("isSearch", true);
		model.addAttribute("products",searchProducts);
		return "Product";
	}

}

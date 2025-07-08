package com.siva.shopping.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.siva.shopping.model.Cart;
import com.siva.shopping.model.Category;
import com.siva.shopping.model.OrderRequest;
import com.siva.shopping.model.OrderStatus;
import com.siva.shopping.model.Product;
import com.siva.shopping.model.ProductOrder;
import com.siva.shopping.model.User;
import com.siva.shopping.service.ICartService;
import com.siva.shopping.service.ICategoryService;
import com.siva.shopping.service.IProductOrderService;
import com.siva.shopping.service.IProductService;
import com.siva.shopping.service.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IProductOrderService productOrderService;
	
	@ModelAttribute
	public void commonData(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");			
		List<Category> categories = categoryService.activeCategories();
		model.addAttribute("categories", categories);
		Integer countCarts = cartService.countCartByUser(user);
		model.addAttribute("countCarts", countCarts);
	}
	
	@PostMapping("/saveUserInformation")
	public String saveUserInformation(@ModelAttribute User user,HttpSession session, @RequestParam("image") MultipartFile multipartFile) {
		try {
			Boolean emailStatus = userService.existsByEmail(user.getEmail());
			Boolean mobileNumberStatus = userService.existsByMobileNumber(user.getMobileNumber());
			if (emailStatus && mobileNumberStatus) {
				session.setAttribute("warningMsg", "Email and mobile number already exists");
			}
			else if (emailStatus) {
				session.setAttribute("warningMsg", "Email already exists");
			}
			else if (mobileNumberStatus) {
				session.setAttribute("warningMsg", "Mobile number already exists");
			}
			else {		
				if (multipartFile.isEmpty()) {
					session.setAttribute("warningMsg", "please upload the profile");
				}
				else {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					user.setProfileImage(multipartFile.getOriginalFilename());
					User saved = userService.saveUser(user);
					if (saved!=null) {
						session.setAttribute("successMsg", "Regisration success");
					} 
					else {
						session.setAttribute("failMsg", "Something went wrong");
					}
				}
			}
			return "redirect:/signup";
		} catch (Exception e) {
			session.setAttribute("warningMsg", "Image location not found");
			System.out.println(e.getMessage());
			return "redirect:/signup";
		}
	}

	@PostMapping("/getUserData")
	public String getUserData(HttpSession session, Model model, @RequestParam("email") String email, @RequestParam("password") String password) {
		User user = userService.getUserByEmailAndPassword(email, password);
		if (user!=null) {
			session.setAttribute("user", user);
			return "redirect:/user/home";
		}
		else {
			session.setAttribute("failMsg", "Invalid credientails");
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/home")
	public String userHomePage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("user", user);
			List<Category> activeCategories = categoryService.activeCategories().stream().sorted((c1, c2) -> c2.getId().compareTo(c1.getId())).limit(6).toList();
			List<Product> activeProducts = productService.activeProducts().stream().sorted((p1, p2) -> p2.getId().compareTo(p1.getId())).limit(8).toList();
			model.addAttribute("category",  activeCategories);
			model.addAttribute("products", activeProducts);
			return "User/Home";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/products")
	public String productsPage(HttpSession session, Model model, @RequestParam(value="category", defaultValue="") String category, @RequestParam(defaultValue="0") int pageNo) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Page<Product> page = productService.getProductsByCategory(category, pageNo);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("products", page.getContent());
			model.addAttribute("isSearch", false);
			model.addAttribute("paramValue", category);
			return "User/Products";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/search")
	public String searchProduct(@RequestParam("ch") String ch, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Product> searchProducts = productService.searchProducts(ch).stream().sorted((p1, p2) -> p2.getId().compareTo(p1.getId())).collect(Collectors.toList());
			model.addAttribute("isSearch", true);
			model.addAttribute("products",searchProducts);
			return "User/Products";
		} 
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/product/{id}")
	public String getProductInformation(HttpSession session, Model model, @PathVariable("id") int id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("user", user);
			Product product = productService.getProductById(id);
			model.addAttribute("product", product);
			return "User/ViewProduct";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/addCart")
	public String addToCartPage(HttpSession session, @RequestParam("pid") Integer pid, @RequestParam("uid") Integer uid) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Cart saveCart = cartService.saveCart(uid, pid);
			if (saveCart!=null) {
				session.setAttribute("successMsg", "product added to cart");
				return "redirect:/user/product/"+pid;
			}
			else {
				session.setAttribute("failMsg", "product added to cart failed");
				return "redirect:/user/product/"+pid;
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/cart")
	public String viewCartPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Cart> carts = cartService.getCartByUser(user);
			if (carts.size()>0) {				
				Double totalOrderPrice = carts.get(carts.size()-1).getTotalOrderPrice();
				model.addAttribute("totalOrderPrice", totalOrderPrice);
			}
			model.addAttribute("carts", carts);
			return "User/ViewCart";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(HttpSession session, @RequestParam("status")String status, @RequestParam("cid") Integer cid) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Boolean isUpdated = cartService.updateQuantity(status, cid);
			if (isUpdated) {
				System.out.println("product quantity updated");
			}
			return "redirect:/user/cart";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/order")
	public String OrdeerPage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			List<Cart> carts = cartService.getCartByUser(user);
			if (carts.size()>0) {
				Double orderPrice = carts.get(carts.size()-1).getTotalOrderPrice();
				model.addAttribute("orderPrice", orderPrice);
				Double totalOrderPrice = carts.get(carts.size()-1).getTotalOrderPrice()+50+50;
				model.addAttribute("totalOrderPrice", totalOrderPrice);
			}
			return "User/Order";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/orderStatus")
	public String orderStatusPage(HttpSession session, Model model, @ModelAttribute OrderRequest orderRequest) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Boolean saveProductOrder = productOrderService.saveProductOrder(user, orderRequest);
			if (saveProductOrder) {
				return "redirect:/user/orderSuccess";
			}
			else {
				session.setAttribute("successMsg", "something went wrong");
				return "redirect:/user/order";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/orderSuccess")
	public String orderSuccessPage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			return "User/Success";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/myOrders")
	public String yourOrders(HttpSession session, Model model, @RequestParam(defaultValue="0") int pageNo) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			Page<ProductOrder> page = productOrderService.getProductOrderByUser(user, pageNo);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("productOrders", page.getContent());
			return "User/MyOrders";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/updatestatus")
	public String updateStatus(HttpSession session, Model model, @RequestParam("status") Integer st, @RequestParam("orderId") Integer orderId) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			OrderStatus[] values = OrderStatus.values();
			String status = null;
			for (OrderStatus orderStatus : values) {
				if (orderStatus.getId().equals(st)) {
					status = orderStatus.getName();
				}
			}
			Boolean isUpdated = productOrderService.updateOrderStatus(orderId, status);
			if (isUpdated) {
				session.setAttribute("successMsg", "status updated");
			}
			else {
				session.setAttribute("failMsg", "something went wrong");
			}
			return "redirect:/user/myOrders";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/deleteOrder/{id}")
	public String deleteProductOrder(HttpSession session, @PathVariable("id") Integer id) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			ProductOrder productOrder = productOrderService.getProductOrderByOrderId(id);
			if (productOrder!=null) {
				Boolean isDelete = productOrderService.deleteProductOrder(productOrder);
				if (isDelete) {
					session.setAttribute("successMsg", "order deleted");
					return "redirect:/user/myOrders";
				}
				else {
					session.setAttribute("failMsg", "something went wrong");
					return "redirect:/user/myOrders";
				}
			}
			else {
				session.setAttribute("failMsg", "order not found");
				return "redirect:/user/myOrders";
			}
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@GetMapping("/profile")
	public String profilePage(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			model.addAttribute("user", user);
			return "User/MyProfile";
		}
		else {
			return "redirect:/signin";
		}
	}
	
	@PostMapping("/updateUserInformation")
	public String updateUserInformation(HttpSession session, Model model, @ModelAttribute User user, @RequestParam("image") MultipartFile multipartFile) {
		try {
			User oldUser = (User) session.getAttribute("user");
			if (oldUser!=null) {
				if (!multipartFile.isEmpty()) {
					File file = new ClassPathResource("/static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					user.setProfileImage(multipartFile.getOriginalFilename());
				}
				else {
					user.setProfileImage(oldUser.getProfileImage());
				}
				User saveUser = userService.saveUser(user);
				if (saveUser!=null) {
					session.setAttribute("user", saveUser);
					session.setAttribute("successMsg", "User inforamtion updated");
					return "redirect:/user/profile";
				}
				else {
					session.setAttribute("failMsg", "Email or mobile number exists");
					return "redirect:/user/profile";
				}
			}
			else {
				return "redirect:/signin";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("failMsg", "something went wrong");
			return "redirect:/user/profile";
		}
	}
	  
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user!=null) {
			session.removeAttribute("user");
			session.setAttribute("logoutInfo", "you have been logged out");
			return "redirect:/signin";
		}
		else {
			return "redirect:/signin";
		}
	}
	
}

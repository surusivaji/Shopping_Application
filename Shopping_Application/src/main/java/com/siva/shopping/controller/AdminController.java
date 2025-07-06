package com.siva.shopping.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

import com.siva.shopping.model.Admin;
import com.siva.shopping.model.Category;
import com.siva.shopping.model.OrderStatus;
import com.siva.shopping.model.Product;
import com.siva.shopping.model.ProductOrder;
import com.siva.shopping.model.User;
import com.siva.shopping.service.IAdminService;
import com.siva.shopping.service.ICategoryService;
import com.siva.shopping.service.IProductOrderService;
import com.siva.shopping.service.IProductService;
import com.siva.shopping.service.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private  ICategoryService categoryService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProductOrderService productOrderService;
	
	@PostMapping("/getAdminInfo")
	public String adminLogin(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password) {
		Admin admin = adminService.getAdminByEmailAndPassword(email, password);
		if (admin!=null) {
			session.setAttribute("admin", admin);
			return "redirect:/admin/home";
		}
		else {
			session.setAttribute("failMsg", "Invalid Credientials");
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/home") 
	public String adminHomePage(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			return "Admin/Home";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/addproduct")
	public String addProductPage(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			List<Category> categories = categoryService.allCategories();
			model.addAttribute("categories", categories);
			return "Admin/AddProduct";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/saveProductInformation")
	public String saveProductInformation(HttpSession session, @ModelAttribute Product product, @RequestParam("productImage") MultipartFile multipartFile) {
		try {
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin!=null) {
				if (!multipartFile.isEmpty()) {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"product"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					product.setImage(multipartFile.getOriginalFilename());
				}
				else {
					session.setAttribute("warningMsg", "please upload the image");
					return "redirect:/admin/addproduct";
				}
				product.setDiscount(0);
				product.setDiscountPrice(product.getPrice());
				Product saveProduct = productService.saveProduct(product);
				if (saveProduct!=null) {
					session.setAttribute("successMsg", "product added successfully");
					return "redirect:/admin/addproduct";
				} 
				else {
					session.setAttribute("failMsg", "something went wrong");
					return "redirect:/admin/addproduct";
				}
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			session.setAttribute("warningMsg", "something went wrong");
			return "redirect:/admin/addproduct";
		}
	}
	
	@GetMapping("/products")
	public String productsPage(HttpSession session, Model model, @RequestParam(defaultValue = "0") int pageNo) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<Product> page = productService.getAllProducts(pageNo);
			if (page!=null) {
				model.addAttribute("currentPage", pageNo);
				model.addAttribute("totalPages", page.getTotalPages());
				model.addAttribute("products", page.getContent());
				return "/Admin/Products";
			}
			else {
				return "redirect:/admin/home";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/deleteproduct/{id}")
	public String deleteProduct(HttpSession session, @PathVariable("id") int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Product product = productService.getProductById(id);
			if (product!=null) {
				boolean deleteProduct = productService.deleteProduct(product);
				if (deleteProduct) {
					return "redirect:/admin/products";
				}
				else {
					session.setAttribute("failMsg", "something went wrong on the server");
					return "redirect:/admin/products";
				}
			} 
			else {
				session.setAttribute("failMsg", "product is not found");
				return "redirect:/admin/products";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/editproduct/{id}")
	public String editProduct(HttpSession session, @PathVariable("id") int id, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Product product = productService.getProductById(id);
			if (product!=null) {
				List<Category> categories = categoryService.allCategories();
				model.addAttribute("categories", categories);
				model.addAttribute("product", product);
				return "/Admin/EditProduct";
			}
			else {
				session.setAttribute("failMsg", "product is not found");
				return "redirect:/admin/products";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateproductInformation")
	public String updateProductInformation(HttpSession session, @ModelAttribute Product product,Model model, @RequestParam("productImage") MultipartFile multipartFile) {
		try {
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin!=null) {
				Product oldProduct = productService.getProductById(product.getId());
				if (!multipartFile.isEmpty()) {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"product"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
					product.setImage(multipartFile.getOriginalFilename());
				}
				else {
					product.setImage(oldProduct.getImage());
				}
				if (product.getDiscount()<0 || product.getDiscount()>100) {
					session.setAttribute("warningMsg", "Invalid Discount");
					return "redirect:/admin/editproduct/"+product.getId();
				}
				Double productDiscount = product.getPrice()-product.getPrice()*(product.getDiscount()/100.0);
				product.setDiscountPrice(productDiscount);
				Product updated = productService.saveProduct(product);
				if (updated!=null) {
					model.addAttribute("product", updated);
					session.setAttribute("successMsg", "product is updated successfully");
					return "redirect:/admin/products";
				}
				else {
					session.setAttribute("failMsg", "somethig went wrong on the server");
					return "redirect:/admin/editproduct/"+product.getId();
				}
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			session.setAttribute("warningMsg", "something went wrong");
			return "redirect:/admin/editproduct/"+product.getId();
		}
	}
	
	@GetMapping("/categories")
	public String displayCateoriesPage(HttpSession session,@RequestParam(defaultValue = "0") int pageNo, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<Category> page = categoryService.getAllCategories(pageNo);
			if (page!=null) {
				model.addAttribute("currentPage", pageNo);
				model.addAttribute("totalPages", page.getTotalPages());
				model.addAttribute("categories", page.getContent());
				return "/Admin/Category";
			}
			else {	
				return "redirect:/admin/home";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/saveCategoryInformation")
	public String storeCategoryInformation(HttpSession session, @ModelAttribute Category category, @RequestParam("uploadImage") MultipartFile uploadImage) {
		try {
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin!=null) {
				if (!categoryService.isCategoryExists(category.getName())) {
					if (uploadImage.isEmpty()) {
						session.setAttribute("warningMsg", "You are not uploading the category image");
						return "redirect:/admin/categories";
					}
					else {
						File file = new ClassPathResource("static/images").getFile();
						Path path = Paths.get(file.getAbsolutePath()+File.separator+"category"+File.separator+uploadImage.getOriginalFilename());
						Files.copy(uploadImage.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
						category.setCategoryImage(uploadImage.getOriginalFilename());
						
						Category saveCategory = categoryService.saveCategory(category);
						if (saveCategory!=null) {
							session.setAttribute("successMsg", "category added");
						}
						else {
							session.setAttribute("failMsg", "category not added");
						}
						return "redirect:/admin/categories";
					}
				}
				else {
					session.setAttribute("warningMsg", "Category exists");
					return "redirect:/admin/categories";
				}
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			session.setAttribute("failMsg", "Something went wrong");
			return "redirect:/admin/categories";
		}
	}
	
	@GetMapping("/editcategory/{id}")
	public String editCategory(@PathVariable("id") int id, HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Category category = categoryService.getCategoryById(id);
			if (category!=null) {
				model.addAttribute("category", category);
				return "/Admin/EditCategory";
			}
			else {
				session.setAttribute("errorInfo", "this id is not present");
				return "redirect:/admin/categories";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateCategoryInformation")
	public String updateCategoryInformation(@ModelAttribute Category category, HttpSession session, @RequestParam("uploadImage")MultipartFile multipartFile) {
		try {
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin!=null) {
				Category oldCategory = categoryService.getCategoryById(category.getId());
				if (!multipartFile.isEmpty()) {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"category"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
					category.setCategoryImage(multipartFile.getOriginalFilename());
				}
				else {
					category.setCategoryImage(oldCategory.getCategoryImage());
				}
				Category updated = categoryService.saveCategory(category);
				if (updated!=null) {
					session.setAttribute("updateSuccess", "category updated successfully");
				}
				else {
					session.setAttribute("updateFail", "something went wrong on the server");
				}
				return "redirect:/admin/categories";
			} 
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			session.setAttribute("updateFail", "something went wrong");
			System.out.println(e.getMessage());
			return "redirect:/admin/categories";
		}
	}
	
	@GetMapping("/deletecategory/{id}")
	public String deleteCategory(HttpSession session, @PathVariable("id") int id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Category category = categoryService.getCategoryById(id);
			if (category!=null) {
				boolean deleteCategory = categoryService.deleteCategory(category);
				if (deleteCategory) {
					return "redirect:/admin/categories";
				}
				else {
					session.setAttribute("errorInfo", "something went wrong on the server");
					return "redirect:/admin/categories";
				}
			}
			else {
				session.setAttribute("errorInfo", "this category is not present");
				return "redirect:/admin/categories";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/users")
	public String getAllUsers(HttpSession session, @RequestParam(defaultValue = "0") int pageNo, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<User> page = userService.getAllUsers(pageNo);
			if (page!=null) {
				model.addAttribute("currentPage", pageNo);
				model.addAttribute("totalPages", page.getTotalPages());
				model.addAttribute("users", page.getContent());
				return "Admin/UserDetails";
			}
			else {
				return "redirect:/admin/home";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/orders")
	public String getAllOrders(HttpSession session, @RequestParam(defaultValue="0") int pageNo, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<ProductOrder> page = productOrderService.getAllProducts(pageNo);
			if (page!=null) {
				model.addAttribute("currentPage", pageNo);
				model.addAttribute("totalPages", page.getTotalPages());
				model.addAttribute("orders", page.getContent());
				model.addAttribute("search", false);
				return "Admin/Orders";
			}
			else {
				return "redirect:/admin/home";
			}
		} 
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/update-order-status")
	public String updateOrderStatus(HttpSession session, @RequestParam("id") Integer id, @RequestParam("status") Integer status) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			OrderStatus[] values = OrderStatus.values();
			String updateStatus = null;
			for (OrderStatus orderStatus : values) {
				if (orderStatus.getId().equals(status)) {
					updateStatus = orderStatus.getName();
				}
			}
			Boolean isUpdated = productOrderService.updateOrderStatus(id, updateStatus);
			if (isUpdated) {
				session.setAttribute("successMsg", "order status updated");
				return "redirect:/admin/orders";
			}
			else {
				session.setAttribute("failMsg", "something went wrong");
				return "redirect:/admin/orders";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/deleteOrder/{id}")
	public String removeOrder(HttpSession session, @PathVariable("id") Integer Id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			ProductOrder productOrder = productOrderService.getProductOrderByOrderId(Id);
			if (productOrder!=null) {
				Boolean deleteProductOrder = productOrderService.deleteProductOrder(productOrder);
				if (deleteProductOrder) {
					session.setAttribute("successMsg", "product order deleted");
					return "redirect:/admin/orders";
				}
				else {
					session.setAttribute("failMsg", "something went wrong");
					return "redirect:/admin/orders";
				}
			}
			else {
				session.setAttribute("failMsg", "product is not found");
				return "redirect:/admin/orders";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/search-order")
	public String searchOrder(HttpSession session, @RequestParam("orderId") String orderId, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			ProductOrder productOrder = productOrderService.getProductOrderByOrderId(orderId);
			if (productOrder!=null) {
				model.addAttribute("productOrder", productOrder);
			}
			else {
				model.addAttribute("productOrder", null);
				model.addAttribute("infoMsg", "order not found");
			}
			model.addAttribute("search", true);
			return "Admin/Orders";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/addAdmin")
	public String addAdmin(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			return "Admin/AddAdmin";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/saveAdminInformation")
	public String saveAdminInformation(HttpSession session, @ModelAttribute Admin admin,  @RequestParam("image") MultipartFile multipartFile) {
		try {	
			Admin adminInfo = (Admin) session.getAttribute("admin");
			if (adminInfo!=null) {
				Boolean emailStatus = adminService.checkEmailIsPresentOrNot(admin.getEmail());
				Boolean mobileNumberStatus = adminService.checkMobileNumberIsPresentOrNot(admin.getMobileNumber());
				if (emailStatus && mobileNumberStatus) {
					session.setAttribute("warningMsg", "Email and Mobile Number are already exists");
				}
				else if (emailStatus) {
					session.setAttribute("warningMsg", "Email already exists");
				}
				else if (mobileNumberStatus) {
					session.setAttribute("warningMsg", "Mobile number already exists");
				}
				else {		
					if (multipartFile.isEmpty()) {
						admin.setProfileImage("user.png");
					}
					else {
						File file = new ClassPathResource("/static/images").getFile();
						Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+multipartFile.getOriginalFilename());
						Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
						admin.setProfileImage(multipartFile.getOriginalFilename());
					}
					Admin saveAdmin = adminService.saveAdmin(admin);
					if (saveAdmin!=null) {
						session.setAttribute("successMsg", "Admin added successfully");
					}
					else {
						session.setAttribute("failMsg", "Something went wrong");
					}
				}
				return "redirect:/admin/addAdmin";
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("warningMsg", "Image location not found");
			return "redirect:/addAdmin";
		}
	}
	
	@GetMapping("/admins")
	public String showAdminDetails(HttpSession session, @RequestParam(defaultValue = "0") int pageNo, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Page<Admin> page = adminService.getAllAdmins(pageNo);
			if (page!=null) {
				model.addAttribute("currentPage", pageNo);
				model.addAttribute("totalPages", page.getTotalPages());
				model.addAttribute("admins", page.getContent());
				return "Admin/AdminDetails";
			}
			else {
				return "redirect:/admin/home";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/updateAdmin/{id}")
	public String updateAdminInformation(HttpSession session, @PathVariable("id") Integer id, Model model) {
		Admin adminInfo = (Admin) session.getAttribute("admin");
		if (adminInfo!=null) {
			Admin admin = adminService.getAdminById(id);
			if (admin!=null) {
				model.addAttribute("admin", admin);
				return "Admin/EditAdmin";
			}
			else {
				session.setAttribute("failMsg", "admin not found");
				return "redirect:/admin/admins";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateAdminInformation")
	public String updateAdminInfomration(HttpSession session, @ModelAttribute Admin admin, @RequestParam("image") MultipartFile multipartFile) {
		try {
			Admin adminInfo = (Admin) session.getAttribute("admin");
			if (adminInfo!=null) {
				Admin oldAdminInfo = adminService.getAdminById(admin.getId());
				if (multipartFile.isEmpty()) {
					admin.setProfileImage(oldAdminInfo.getProfileImage());
				}
				else {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					admin.setProfileImage(multipartFile.getOriginalFilename());
				}
				admin.setPassword(oldAdminInfo.getPassword());
				Admin saveAdmin = adminService.saveAdmin(admin);
				if (saveAdmin!=null) {
					session.setAttribute("successMsg", "admin information updation successfully completed");
					return "redirect:/admin/admins";
				}
				else {
					session.setAttribute("failMsg", "Email or mobile number already exists");
					return "redirect:/admin/updateAdmin"+admin.getId();
				}
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("failMsg", "something went wrong while uploading the image");
			return "redirect:/admin/updateAdmin"+admin.getId();
		}
	}
	
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(HttpSession session, @PathVariable("id") Integer id) {
		Admin adminInfo = (Admin) session.getAttribute("admin");
		if (adminInfo!=null) {
			Admin admin = adminService.getAdminById(id);
			if (admin!=null) {
				Boolean isDeleted = adminService.deleteAdmin(admin);
				if (isDeleted) {
					return "redirect:/admin/admins";
				}
				else {
					session.setAttribute("failMsg", "Sorry, You Can't Perform Delete Operation");
					return "redirect:/admin/admins";
				}
			}
			else {
				session.setAttribute("failMsg", "Admin not found");
				return "redirect:/admin/admins";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/profile") 
	public String adminProfile(HttpSession session, Model model) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			model.addAttribute("admin", admin);
			return "Admin/AdminProfile";
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/edituser/{id}")
	public String editUser(HttpSession session, Model model, @PathVariable("id") Integer id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			User user = userService.getUserById(id);
			if (user!=null) {
				model.addAttribute("user", user);
				return "Admin/EditUser";
			}
			else {
				session.setAttribute("failMsg", "user is not found");
				return "redirect:/admin/users";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/updateUserInformation")
	public String updateUserInformation(HttpSession session, @ModelAttribute User user, @RequestParam("image")MultipartFile multipartFile) {
		try {	
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin!=null) {
				User oldUser = userService.getUserById(user.getId());
				if (multipartFile.isEmpty()) {
					user.setProfileImage(oldUser.getProfileImage());
				}
				else {
					File file = new ClassPathResource("static/images").getFile();
					Path path = Paths.get(file.getAbsolutePath()+File.separator+"users"+File.separator+multipartFile.getOriginalFilename());
					Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
					user.setProfileImage(multipartFile.getOriginalFilename());
				}
				User saveUser = userService.saveUser(user);
				if (saveUser!=null) {
					session.setAttribute("successMsg", "user information updated successfully");
					return "redirect:/admin/users";
				}
				else {
					session.setAttribute("failMsg", "email or mobile number already exists");
					return "redirect:/admin/edituser/"+user.getId();
				}
			}
			else {
				return "redirect:/admin";
			}
		} catch (Exception e) {
			session.setAttribute("failMsg", "something went wrong while uploading the image");
			System.out.println(e.getMessage());
			return "redirect:/admin/edituser/"+user.getId();
		}
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(HttpSession session, @PathVariable("id") Integer id) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			User user = userService.getUserById(id);
			if (user!=null) {
				Boolean isDelete = userService.deleteUser(user);
				if (isDelete) {
					return "redirect:/admin/users";
				}
				else {
					session.setAttribute("failMsg", "Something went wrong on the server");
					return "redirect:/admin/users";
				}
			}
			else {
				session.setAttribute("failMsg", "User information is not found");
				return "redirect:/admin/users";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@PostMapping("/changepassword")
	public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			Admin admin1 = adminService.getAdminByEmailAndPassword(admin.getEmail(), oldPassword);
			if (admin1!=null) {
				admin1.setPassword(newPassword);
				Admin saveAdmin = adminService.saveAdmin(admin1);
				if (saveAdmin!=null) {
					session.setAttribute("infoMsg", "password changed");
				}
				else {
					session.setAttribute("failMsg", "something went wrong");
				}
				return "redirect:/admin/admins";
			}
			else {
				session.setAttribute("failMsg", "invalid password");
				return "redirect:/admin/admins";
			}
		}
		else {
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if (admin!=null) {
			session.removeAttribute("admin");
			session.setAttribute("logoutInfo", "you have successfully logout");
			return "redirect:/admin";
		}
		else {
			return "redirect:/admin";
		}
	}
	

}

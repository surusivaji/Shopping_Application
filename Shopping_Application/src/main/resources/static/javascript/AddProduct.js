// VALIDATION FOR ADD PRODUCT

let title = document.getElementById("title");
let description = document.getElementById("description");
let category = document.getElementById("category");
let price = document.getElementById("price");
let stock = document.getElementById("stock");
let productImage = document.getElementById("productImage");
let myForm = document.getElementById("myForm");

price.addEventListener("input", function() {
	let priceValue = price.value;
	if (priceValue === "") {
		price.style.border = "1px solid #FF0000";
	}
	else if (priceValue < 0) {
		price.style.border = "1px solid #FF0000";
	}
	else {
		price.style.border = "1px solid #ced4da";
	}
});

stock.addEventListener("input", function() {
	let stockValue = stock.value;
	if (stockValue === "") {
		stock.style.border = "1px solid #FF0000";
	}
	else if (stockValue < 0) {
		stock.style.border = "1px solid #FF0000";
	}
	else {
		stock.style.border = "1px solid #ced4da";
	}
});

myForm.addEventListener("submit", function(e) {
	e.preventDefault();
	
	let titleValue = title.value;
	let descriptionValue = description.value;
	let categoryValue = category.value;
	let priceValue = price.value;
	let stockValue = stock.value;
	let productImageValue = productImage.files[0];
	
	let isValid = true;
	
	if (titleValue === "") {
		title.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		title.style.border = "1px solid #ced4da";
	}
	
	if (descriptionValue === "") {
		description.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		description.style.border = "1px solid #ced4da";
	}
	
	if (categoryValue === "") {
		category.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		category.style.border = "1px solid #ced4da";
	}
	
	if (priceValue === "") {
		price.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (priceValue < 0) {
		price.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		price.style.border = "1px solid #ced4da";
	}
	
	if (stockValue === "") {
		stock.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (stockValue < 0) {
		stock.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		stock.style.border = "1px solid #ced4da";
	}
	
	if (!productImageValue) {
		productImage.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		productImage.style.border = "1px solid #ced4da";
	}
	
	if (isValid) {
		this.submit();
	}
	
	
})

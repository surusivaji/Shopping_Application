/* REGISTRATION PAGE VALIDATIONS */

let fullName = document.getElementById("fullName");
let mobileNumber = document.getElementById("mobileNumber");
let email = document.getElementById("email");
let address = document.getElementById("address");
let city = document.getElementById("city");
let state = document.getElementById("state");
let pincode = document.getElementById("pincode");
let password = document.getElementById("password");
let confirmPassword = document.getElementById("confirmPassword");
let image = document.getElementById("image");
let myForm = document.getElementById("myForm");

fullName.addEventListener("input", function() {
	let fullNameValue = fullName.value;
	let namePattern = /^[A-Za-z\s]+$/;
	if (fullNameValue === "") {
		fullName.style.border = "1px solid #FF0000";
	}
	else if (!namePattern.test(fullNameValue)) {
		fullName.style.border = "1px solid #FF0000";
	}
	else {
		fullName.style.border = "1px solid #ced4da";
	}
});

mobileNumber.addEventListener("input", function() {
	let mobileNumberValue = mobileNumber.value;
	let mobilePattern = /^\d{10}$/;
	if (mobileNumberValue === "") {
		mobileNumber.style.border = "1px solid #FF0000";
	}
	else if (!mobilePattern.test(mobileNumberValue)) {
		mobileNumber.style.border = "1px solid #FF0000";
	}
	else {
		mobileNumber.style.border = "1px solid #ced4da";
	}
});

email.addEventListener("input", function() {
	let emailValue = email.value;
	let emailPattern =  /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	if (emailValue === "") {
		email.style.border = "1px solid #FF0000";
	}
	else if (!emailPattern.test(emailValue)) {
		email.style.border = "1px solid #FF0000";
	}
	else {
		email.style.border = "1px solid #ced4da";
	}
});

address.addEventListener("input", function() {
	let addressValue = address.value;
	if (addressValue === "") {
		address.style.border = "1px solid #FF0000";
	}
	else {
		address.style.border = "1px solid #ced4da";
	}
});

city.addEventListener("input", function() {
	let cityValue = city.value;
	if (cityValue === "") {
		city.style.border = "1px solid #FF0000";
	}
	else {
		city.style.border = "1px solid #c3d4da";
	}
});

state.addEventListener("input", function() {
	let stateValue = state.value;
	if (stateValue === "") {
		state.style.border = "1px solid #FF0000";
	}
	else {
		state.style.border = "1px solid #ced4da";
	}
});

pincode.addEventListener("input", function() {
	let pincodeValue = pincode.value;
	let pincodePattern = /^[1-9][0-9]{5}$/
	if (pincodeValue === "") {
		pincode.style.border = "1px solid #FF0000";
	}
	else if (!pincodePattern.test(pincodeValue)) {
		pincode.style.border = "1px solid #FF0000";
	}
	else {
		pincode.style.border = "1px solid #ced4da";
	}
});

password.addEventListener("input", function() {
	let passwordValue = password.value;
	let passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{6,}$/;
	if (passwordValue === "") {
		password.style.border = "1px solid #FF0000";
	}
	else if (!passwordPattern.test(passwordValue)) {
		password.style.border = "1px solid #FF0000";
	}
	else {
		password.style.border = "1px solid #ced4da";
	}
})

confirmPassword.addEventListener("input", function() {
	let passwordValue = password.value;
	console.log(passwordValue);
	let confirmPasswordValue = confirmPassword.value;
	if (confirmPasswordValue === "") {
		confirmPassword.style.border = "1px solid #FF0000";
	}
	else if (passwordValue !== confirmPasswordValue) {
		confirmPassword.style.border = "1px solid #FF0000";
	}
	else {
		confirmPassword.style.border = "1px solid #ced4da";
	}
});

image.addEventListener("input", function() {
	let imageValue = image.files[0];
	if (!imageValue) {
		image.style.border = "1px solid #FF0000";
	}
	else {
		image.style.border = "1px solid #ced4da";
	}
});

myForm.addEventListener("submit", function(e) {
	e.preventDefault();
	
	let fullNameValue = fullName.value;
	let mobileNumberValue = mobileNumber.value;
	let emailValue = email.value;
	let addressValue = address.value;
	let cityValue = city.value;
	let stateValue = state.value;
	let pincodeValue = pincode.value;
	let passwordValue = password.value;
	let confirmPasswordValue = confirmPassword.value;
	let imageValue = image.files[0];
	
	let namePattern = /^[A-Za-z\s]+$/;
	let mobilePattern = /^\d{10}$/;
	let emailPattern =  /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	let pinCodePattern = /^[1-9][0-9]{5}$/;
	let passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=!]).{6,}$/;
	
	let isValid = true;
	
	//name validation
	if (fullNameValue === "") {
		fullName.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (!namePattern.test(fullNameValue)) {
		fullName.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		fullName.style.border = "1px solid #ced4da";
	}
	
	//mobile number validation
	if (mobileNumberValue === "") {
		mobileNumber.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (!mobilePattern.test(mobileNumberValue)) {
		mobileNumber.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		mobileNumber.style.border = "1px solid #ced4da";
	}
	
	//email validation
	if (emailValue === "") {
		email.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (!emailPattern.test(emailValue)) {
		email.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		email.style.border = "1px solid #ced4da";
	}
	
	//address validation
	if (addressValue === "") {
		address.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		address.style.border = "1px solid #ced4da";
	}
	
	//city validation
	if (cityValue === "") {
		city.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		city.style.border = "1px solid #ced4da";
	}
	
	//state validation
	if (stateValue === "") {
		state.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		state.style.border = "1px solid #ced4da";
	}
	
	//pincode validation
	if (pincodeValue === "") {
		pincode.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (!pinCodePattern.test(pincodeValue)) {
		pincode.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		pincode.style.border = "1px solid #ced4da";
	}
	
	//password validation
	if (passwordValue === "") {
		password.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (!passwordPattern.test(passwordValue)) {
		password.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		password.style.border = "1px solid #ced4da";
	}
	
	//confirm password validation
	if (confirmPasswordValue === "") {
		confirmPassword.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (passwordValue !== confirmPasswordValue) {
		confirmPassword.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		confirmPassword.style.border = "1px solid #ced4da";
	}
	
	//image validation
	if (!imageValue) {
		image.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		image.style.border = "1px solid #ced4da";
	}
	
	if (isValid) {
		this.submit();
	}
})
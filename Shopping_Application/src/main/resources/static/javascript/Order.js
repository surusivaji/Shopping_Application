/* Order Validation */

let firstName = document.getElementById("firstName");
let lastName = document.getElementById("lastName");
let email = document.getElementById("email");
let mobileNumber = document.getElementById("mobileNumber");
let address = document.getElementById("address");
let city = document.getElementById("city");
let state = document.getElementById("state");
let pincode = document.getElementById("pincode");
let payment = document.getElementById("payment");
let myForm = document.getElementById("myForm");

myForm.addEventListener("submit", function(e) {
	e.preventDefault();
		
	let firstNameValue = firstName.value;
	let lastNameValue = lastName.value;
	let emailValue = email.value;
	let mobileNumberValue = mobileNumber.value;
	let addressValue = address.value;
	let cityValue = city.value;
	let stateValue = state.value;
	let pincodeValue = pincode.value;
	let paymentType = payment.value;
		
	let namePattern = /^[A-Za-z\s]+$/;
	let mobilePattern = /^\d{10}$/;
	let emailPattern =  /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	let pinCodePattern = /^[1-9][0-9]{5}$/;

	let isValid = true;

	//first name validation
	if (firstNameValue === "") {
		firstName.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else if (!namePattern.test(firstNameValue)) {
		firstName.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		firstName.style.border = "1px solid #ced4da";
	}
	
	//lastname validation
	if (lastNameValue === "") {
			lastName.style.border = "1px solid #FF0000";
			isValid = false;
		}
		else if (!namePattern.test(lastNameValue)) {
			lastName.style.border = "1px solid #FF0000";
			isValid = false;
		}
		else {
			lastName.style.border = "1px solid #ced4da";
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
	
	//payment option validation
	if (paymentType === "") {
		payment.style.border = "1px solid #FF0000";
		isValid = false;
	}
	else {
		payment.style.border = "1px solid #ced4da";	
	}
	
	if (isValid) {
		myForm.submit();
	}
	
})
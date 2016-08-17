
function isnull(str) {
	if (str == null || str == "" || str == "undefine") {
		return true;
	}
	return false;
}
function insertButton(theForm) {
	if (!Validator.Validate($(theForm))) {
		Validator.Validate($(theForm), 3);
		return false;
	} else {
		document.getElementById(theForm).submit();
	}
}
function checkBox(boxName) {
	if (eval(document.getElementsByName(boxName)).length) {
		for (var counter = 0; counter < eval(document.getElementsByName(boxName)).length; counter++) {
			if (eval(document.getElementsByName(boxName))[counter].checked) {
				return true;
			}
		}
		return false;
	} else {
		return false;
	}
}
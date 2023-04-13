export function validateEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
}

export function validatePassword(password) {
    return password.length >= 8;
}

export function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

export function isNullOrEmpty(){
    function check(arg){
        return arg === null || arg === undefined || arg === '';
    }

    if (arguments.length === 0) {
        return true;
    }
    if(arguments.length === 1){
        return check(arguments[0]);
    }
    return [...arguments].some(check);
}
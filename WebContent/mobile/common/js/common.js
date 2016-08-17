/**
 * 验证密码级别
 * @param password
 * @returns {Number}
 */
function AnalyzePasswordSecurityLevel(password) {
    var securityLevelFlag = 0;
    if (password.length < 6) {
        return 0;
    }
    else {
        if (/[a-z]/.test(password)){
            securityLevelFlag++;    //lowercase
        }
        if (/[A-Z]/.test(password)){
            securityLevelFlag++;    //uppercase
        } 
        if(/[0-9]/.test(password)){
            securityLevelFlag++;    //digital
        }
        if(containSpecialChar(password)){
            securityLevelFlag++;    //specialcase
        }
        return securityLevelFlag;
    }
}

function containSpecialChar(str)   
{   
    var containSpecial = RegExp(/[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/);
    return (containSpecial.test(str));   
}
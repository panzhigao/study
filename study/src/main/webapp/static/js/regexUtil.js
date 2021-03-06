var verifyReg = {
        // 身份证
        identityCard : /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
        //identityCard : /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}\d|[X]{1}$/,
        // 手机号
        mobile: /^((1[3|4|5|7|8][0-9]{1})+\d{8})$/,
        // 电子邮箱
        email: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
        // 座机
        phone: /^0\d{2,3}-?\d{7,8}$/,
        // 数字
        number: /^[0-9]*$/,
        // 非零正整数
        NZ_number: /^\+?[1-9][0-9]*$/,
        // 非零负整数
        _NZ_number: /^\-[1-9][0-9]*$/,
        // 非负整数（正整数 + 0）
        _noIntNumber: /^\d+$/,
        // 非正整数（负整数 + 0）
        noIntNumber: /^((-\d+)|(0+))$/,
        // 整数
        intNumber: /^-?\d+$/,
        // 正浮点小数
        doubleNumber: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
        // 非正浮点小数（负浮点小数 + 0）
        _noDoubleNum: /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/,
        // 负浮点小数
        _doubleNumber: /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/,
        // 浮点数
        double: /^(-?\d+)(\.\d+)?$/,
        //密码
        password:/^[a-zA-Z0-9_\.\-]{6,12}$/
};

/**
 * 验证工具类
 */
var verifyRegUtil = {
    /** 非空验证 */
    isNotNull: function(source){
        if(source != null && source != undefined && source != 'undefined' && source != "")
            return true;
        return false;
    },
    /**去空格后非空验证*/
    isNotNullTrim: function(source){
        if(source != null && source != undefined && source != 'undefined' && $.trim(source) != "")
            return true;
        return false;
    },
    /**
     * 验证字符串最大长度【注：一个汉字的长度为2】
     * @param source 字符串
     * @param num 指定的长度
     */
    textMaxVer: function (source, num) {
        if (source.replace(/[^\x00-\xff]/g, "**").length <= num)
            return true;
        return false;
    },
    /**
     * 验证字符串最小长度【注：一个汉字的长度为2】
     * @param source 字符串
     * @param num 指定的长度
     */
    textMinVer: function (source, num) {
        if (source.replace(/[^\x00-\xff]/g, "**").length >= num)
            return true;
        return false;
    },
    /** 身份证验证 */
    identityVer: function(identity){
        if(identity != null && identity != "" && verifyReg.identityCard.test(identity)){
            return true;
        }
        return false;
    },
    /** 手机号验证 */
    mobileVer: function(mobile){
        if(mobile != null && mobile != "" && verifyReg.mobile.test(mobile)){
            return true;
        }
        return false;
    },
    /** 邮箱验证 */
    emailVer: function(email){
        if(email != null && email != "" && verifyReg.email.test(email)){
            return true;
        }
        return false;
    },
    /** 电话验证 */
    phoneVer: function(phone){
        if(phone != null && phone != "" && verifyReg.phone.test(mobile)){
            return true;
        }
        return false;
    },
    /** 验证是否为正整数 */
    numberVer: function(num) {
        if(num != "") {
            return verifyReg.number.test(num);
        } else {
            return false;
        }
    },
    /** 验证非零正整数 */
    NZ_numberVer: function(num) {
        if(num != "") {
            return verifyReg.NZ_number.test(num);
        } else {
            return false;
        }
    },
    /** 验证非零负整数 */
    _NZ_numberVer: function(num) {
        if(num != "") {
            return verifyReg._NZ_number.test(num);
        } else {
            return false;
        }
    },
    /** 验证非负整数（正整数 + 0） */
    _noIntNumberVer: function(num) {
        if(num != "") {
            return verifyReg._noIntNumber.test(num);
        } else {
            return false;
        }
    },
    /** 验证非正整数（负整数 + 0） */
    noIntNumberVer: function(num) {
        if(num != "") {
            return verifyReg.noIntNumber.test(num);
        } else {
            return false;
        }
    },
    /** 验证整数 */
    intNumberVer: function(num) {
        if(num != "") {
            return verifyReg.intNumber.test(num);
        } else {
            return false;
        }
    },
    /** 验证正浮点小数 */
    doubleNumberVer: function(num) {
        if(num != "") {
            return verifyReg.doubleNumber.test(num);
        } else {
            return false;
        }
    },
    /** 验证非正浮点小数（负浮点小数 + 0） */
    _noDoubleNumVer: function(num) {
        if(num != "") {
            return verifyReg._noDoubleNum.test(num);
        } else {
            return false;
        }
    },
    /** 验证负浮点小数 */
    _doubleNumberVer: function(num) {
        if(num != "") {
            return verifyReg._doubleNumber.test(num);
        } else {
            return false;
        }
    },
    /** 验证浮点小数 */
    doubleVer: function(num) {
        if(num != "") {
            return verifyReg.double.test(num);
        } else {
            return false;
        }
    },
    checkPwd:function(pwd){
    	if(pwd != null && pwd != "" && verifyReg.password.test(pwd)){
            return true;
        }
        return false;
    }
}

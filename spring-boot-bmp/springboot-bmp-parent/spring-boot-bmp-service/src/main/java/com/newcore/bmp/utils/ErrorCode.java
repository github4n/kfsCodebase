package com.newcore.bmp.utils;

public enum ErrorCode {

    /*   
     * */
    ProcessIsSuccess("0000","处理成功"),
    PerformFaild("9999","执行失败"),
    
    DataNotFound("90001","没有查到任何数据:[%s]"),
    ParameterIsNull("90002","缺少必要参数:[%s]"),
    ParameterFormatException("90003","输入参数格式错误:[%s]  "),
    PerformException("90004","执行操作异常:[%s]  "),
    ReturnResultIsNullOrEmpty("90005","返回结果为NULL or 结果集没有元素:[%s] "),
    ClerkIsNotFound("90006","无此工号:[%s]"),
    
    FileNotFoundException("0005","找不到文件"),
    TokenInvalidException("0006","用户认证失败"),
    ConnectException("0007","连接被重置"),
    ValidateException("0008","数据校验失败"),
    CacheOperationException("0009","缓存操作失败"),
    
    WebServiceException("2001","WebService服务调用失败"),
    ProcessingException("2002","RESTful服务调用失败"),
    SOAPFaultException("2003","SOAP服务调用失败"),
    Fault("2011","服务执行失败"),
    
    BusinessException("3000","BusinessException"),
    
    ClassNotFoundException("1001","ClassNotFoundException"),
    NoSuchMethodException("1002","NoSuchMethodException"),
    IllegalArgumentException("1003","IllegalArgumentException"),
    NumberFormatException("1004","NumberFormatException"),
    IndexOutOfBoundsException("1005","IndexOutOfBoundsException"),
    UnsupportedClassVersionError("1006","UnsupportedClassVersionError"),
    JspException("1007","JspException");
    
    private String code;
    private String msg;
    
    private ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
 // 普通方法
    public static String getMsg(String code) {
        for (ErrorCode r : ErrorCode.values()) {
            if (r.getCode().equals(code)) {
                return r.msg;
            }
        }
        return null;
    }
    
    public static String getCode(String msg){
        for (ErrorCode r : ErrorCode.values()) {
            if (r.getMsg().equals(msg)) {
                return r.code;
            }
        }
        return null;
    }
}

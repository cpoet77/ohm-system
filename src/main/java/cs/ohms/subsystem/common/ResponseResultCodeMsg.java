// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/2.
package cs.ohms.subsystem.common;

/**
 * 定义常用返回状态信息
 * HttpStatus需使用spring中的定义
 * @see org.springframework.http.HttpStatus
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public enum ResponseResultCodeMsg {
    /**
     * code : 1000
     * codeParse : 请求成功
     */
    RESULT_SUCCESS(1000, "Request succeeded"),

    /**
     * code : 1001
     * codeParse : 请求失败
     */
    RESULT_FAIL(1001 ,"request was aborted"),

    /**
     * code : 1002
     * codeParse : 未知错误
     */
    RESULT_ERROR(1002, "unknown error");

    // 请求返回code
    private final int code;

    // 请求返回code解释
    private final String codeParse;

    /**
     * @param code 请求返回code
     * @param codeParse 请求返回code解释
     */
    ResponseResultCodeMsg(int code, String codeParse) {
        this.code = code;
        this.codeParse = codeParse;
    }

    /**
     * Gets the value of code.
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the value of codeParse.
     *
     * @return codeParse
     */
    public String getCodeParse() {
        return codeParse;
    }
}

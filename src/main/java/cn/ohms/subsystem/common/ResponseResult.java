// The code file was created by nsleaf (email:nsleaf@foxmail.com) on 2020/4/1.
package cn.ohms.subsystem.common;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 对响应返回的数据进行格式化
 * 1、必需包含响应code
 * 2、必需包含响应msg
 * 3、其他数据
 *
 * @author <a href="https://www.nsleaf.cn">nsleaf</a>
 */
public class ResponseResult implements Serializable {
    // 返回状态码
    private int code;

    // 提示信息
    private String msg;

    // 返回的数据
    private Map<String, Object> data;

    /**
     * 构造器
     */
    public ResponseResult() {
        this.data = new HashMap<>();
    }

    /**
     * 构造器
     *
     * @param code 状态码
     * @param msg  返回信息
     */
    public ResponseResult(int code, String msg) {
        this();
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造器
     *
     * @param code 状态码
     * @param msg  返回信息
     * @param data 返回的结果集
     */
    public ResponseResult(int code, String msg, Map<String, Object> data) {
        this();
        this.code = code;
        this.msg = msg;
        this.data.putAll(data);
    }


    /**
     * 构造器
     *
     * @param codeMsg 返回状态信息
     */
    public ResponseResult(@NotNull ResponseResultCodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getCodeParse());
    }

    /**
     * 构造器
     *
     * @param codeMsg 返回状态信息
     * @param data    结果集
     */
    public ResponseResult(@NotNull ResponseResultCodeMsg codeMsg, Map<String, Object> data) {
        this(codeMsg.getCode(), codeMsg.getCodeParse(), data);
    }

    /**
     * 添加结果集
     *
     * @param data 结果集
     * @return ResponseResult对象
     */
    public ResponseResult add(@NotNull Map<String, Object> data) {
        this.data.putAll(data);
        return this;
    }

    /**
     * 添加结果，类名为结果的key
     *
     * @param data 结果
     * @return ResponseResult对象
     */
    public ResponseResult add(Object data) {
        this.data.put(data.getClass().getSimpleName(), data);
        return this;
    }

    /**
     * 添加多个结果，类名为结果名
     *
     * @param data 需要添加的结果
     * @return ResponseResult对象
     */
    public ResponseResult add(@NotNull Object... data) {
        for (Object o : data) {
            this.data.put(o.getClass().getSimpleName(), o);
        }
        return this;
    }

    /**
     * 添加结果
     *
     * @param key  结果的key
     * @param data 结果集
     * @return ResponseResult
     */
    public ResponseResult add(@NotNull String key, Object data) {
        this.data.put(key, data);
        return this;
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
     * Sets the code.
     *
     * @param code code
     * @return ResponseResult
     */
    public ResponseResult setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * Gets the value of msg.
     *
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the msg.
     *
     * @param msg msg
     * @return ResponseResult
     */
    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * Gets the value of data.
     *
     * @return data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 快速获得ResponseResult
     *
     * @param code 状态码
     * @param msg  返回信息
     * @return ResponseResult
     */
    @NotNull
    @Contract("_, _ -> new")
    public static ResponseResult en(int code, String msg) {
        return (new ResponseResult(code, msg));
    }

    /**
     * 快速获得ResponseResult
     *
     * @param codeMsg 返回状态信息
     * @param data    返回结果
     * @return ResponseResult
     */
    @NotNull
    @Contract("_, _ -> new")
    public static ResponseResult en(ResponseResultCodeMsg codeMsg, Map<String, Object> data) {
        return (new ResponseResult(codeMsg, data));
    }

    /**
     * 快速获得ResponseResult
     *
     * @param codeMsg 返回状态信息
     * @return ResponseResult
     */
    @NotNull
    @Contract("_ -> new")
    public static ResponseResult en(ResponseResultCodeMsg codeMsg) {
        return (new ResponseResult(codeMsg));
    }

    /**
     * 返回成功提示
     * 快速获得ResponseResult
     *
     * @return ResponseResult
     */
    @NotNull
    @Contract(" -> new")
    public static ResponseResult enSuccess() {
        return (new ResponseResult(ResponseResultCodeMsg.RESULT_SUCCESS));
    }

    /**
     * 返回成功信息
     * 快速获得ResponseResult
     *
     * @param data 返回结果
     * @return ResponseResult
     */
    @NotNull
    @Contract("_ -> new")
    public static ResponseResult enSuccess(Map<String, Object> data) {
        return (new ResponseResult(ResponseResultCodeMsg.RESULT_SUCCESS, data));
    }

    /**
     * 返回错误
     * 快速获得ResponseResult
     *
     * @return ResponseResult
     */
    @NotNull
    @Contract(" -> new")
    public static ResponseResult enError() {
        return (new ResponseResult(ResponseResultCodeMsg.RESULT_ERROR));
    }

    /**
     * 返回失败
     * 快速获得ResponseResult
     *
     * @return ResponseResult
     */
    @NotNull
    @Contract(" -> new")
    public static ResponseResult enFail() {
        return (new ResponseResult(ResponseResultCodeMsg.RESULT_FAIL));
    }
}

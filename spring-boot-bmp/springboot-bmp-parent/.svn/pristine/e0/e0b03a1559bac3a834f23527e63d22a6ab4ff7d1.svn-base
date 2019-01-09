package top.ywwxhz.utils;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class R extends HashMap<String, Object> {
	
	public static final Integer SUCCESS = 0;
	public static final Integer INFO = 101;
	public static final Integer VALIDATE = 102;
	public static final Integer ERROR = -1;
	public static final Integer FAILED = -2;

	private R(Integer code) {
		setCode(code);
		put("timestamp", System.currentTimeMillis());
	}

	private R(Integer code, Object data) {
		this(code);
		setData(data);
	}

	public R(Integer code, String message) {
		this(code);
		setMessage(message);
	}

	public R(Integer code, String message, Object data) {
		this(code, message);
		setData(data);
	}

	public static R create(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	public static R create(Integer code, Object data) {
		return new R(code, data);
	}

	public static R create(Integer code, String message) {
		return new R(code, message);
	}

	public static R create(Integer code) {
		return new R(code);
	}

	public static R success() {
		return new R(SUCCESS);
	}

	public static R success(String message, Object data) {
		return new R(SUCCESS, message, data);
	}

	public static R success(String message) {
		return new R(SUCCESS, message);
	}

	public static R success(Object data) {
		return new R(SUCCESS, data);
	}

	public static R error(String message, Object data) {
		return new R(ERROR, message, data);
	}

	public static R error(Object data) {
		return new R(ERROR, data);
	}

	public static R error(String message) {
		return new R(ERROR, message);
	}

	public static R failed(String message, Object data) {
		return new R(FAILED, message, data);
	}

	public static R failed(Object data) {
		return new R(FAILED, data);
	}

	public static R failed(String message) {
		return new R(FAILED, message);
	}

	public static R info(String message, Object data) {
		return new R(INFO, message, data);
	}
	
	public static R info() {
		return new R(INFO);
	}

	public static R info(Object data) {
		return new R(INFO, data);
	}

	public static R info(String message) {
		return new R(INFO, message);
	}

	public static R validate(String message, Object data) {
		return new R(VALIDATE, message, data);
	}

	public static R validate(Object data) {
		return new R(VALIDATE, data);
	}

	public static R validate(String message) {
		return new R(VALIDATE, message);
	}

	public Integer getCode() {
		return (Integer) get("code");
	}

	private R setCode(Integer code) {
		return put("code", code);
	}

	public String getMessage() {
		return (String) get("message");
	}

	public R setMessage(String message) {
		return put("message", message);
	}

	@SuppressWarnings("unchecked")
	public <T> T getData() {
		return (T) get("data");
	}

	public R setData(Object data) {
		return put("data", data);
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

}
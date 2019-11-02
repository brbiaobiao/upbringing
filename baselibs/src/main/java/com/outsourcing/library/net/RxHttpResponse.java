package com.outsourcing.library.net;

/**
 * @author: biao
 * @create: 2019/4/8
 * @Describe:
 */
public class RxHttpResponse<T> {
    private T data;//数据
    private Status status;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public class Status{
        private int code;//标志码

        private String message;//错误描述

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

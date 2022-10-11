package com.example.server.vo;

import com.example.server.exception.YebException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RespBean {

    private Integer code;

    private String message;

    private Object obj;

    /**
     * 身份认证失败
     */
    public static final RespBean AUTH_FAILED_ERROR = new RespBean(401, "当前登录过期，请重新登录", null);

    public YebException toException() {

        return new YebException(code, message);
    }

    public static RespBean success(Integer code, String msg, Object data) {

        return new RespBean(code, msg, data);
    }

    public static RespBean success(Object data) {

        return success(200, "请求成功", data);
    }

    public static RespBean success() {

        return success(200, "请求成功", null);
    }

    public static RespBean error(Integer code, String msg) {

        return new RespBean(code, msg, null);
    }


}

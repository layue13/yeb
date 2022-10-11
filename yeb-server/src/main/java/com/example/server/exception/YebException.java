package com.example.server.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YebException extends RuntimeException {

    private Integer code;

    private String msg;

    public YebException(Integer code, String msg) {
        super(msg);

        this.code = code;
        this.msg = msg;
    }
}

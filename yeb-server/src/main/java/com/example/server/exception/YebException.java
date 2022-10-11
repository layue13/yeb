package com.example.server.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class YebException extends RuntimeException {

    private Integer code;

    private String msg;

    public YebException(Integer code, String msg) {
        super(msg);

        this.code = code;
        this.msg = msg;
    }
}

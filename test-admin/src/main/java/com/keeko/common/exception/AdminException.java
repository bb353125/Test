package com.keeko.common.exception;

import com.keeko.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常相关
 *
 * @author chensq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdminException extends RuntimeException{

    private static final long serialVersionUID = -4574044710599970444L;
    private ExceptionEnum exceptionEnum;

    public AdminException(String validResult) {
        super(validResult);
    }

    public AdminException(String message, ReflectiveOperationException e) {
        super(message);
        super.addSuppressed(e);
    }
}

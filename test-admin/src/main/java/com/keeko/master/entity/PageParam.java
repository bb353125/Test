package com.keeko.master.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页相关
 *
 * @author chensq
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
class PageParam implements Serializable {

    @Min(value = 1, message = "页码不能小于1")
    private int pageNum = 1;

    private int pageSize = 10;
}

package com.example.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 小红
 * @since 2022-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_request_log")
@ApiModel(value = "RequestLog对象", description = "")
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "描述方法的作用")
    private String methodName;

    private LocalDateTime endTime;

    private LocalDateTime startTime;

    @ApiModelProperty(value = "1 执行成功 0 执行失败")
    private Integer status;

    @ApiModelProperty(value = "执行的报错信息")
    private String msg;

    @ApiModelProperty(value = "方法的实际名称")
    private String method;

    private String host;


}

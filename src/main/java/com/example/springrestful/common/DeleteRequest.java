package com.example.springrestful.common;

import lombok.Data;

import java.io.Serializable;


/**
 * 删除请求
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Data
public class DeleteRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
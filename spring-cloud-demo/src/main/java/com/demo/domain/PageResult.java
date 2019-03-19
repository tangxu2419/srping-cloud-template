package com.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2019/3/718:03
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResult<T> {

    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private Integer pages;

    private List<T> list;
}

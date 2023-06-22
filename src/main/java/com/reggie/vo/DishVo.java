package com.reggie.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品信息分页查询数据
 */
@Data
public class DishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品名称
    private String name;


    //菜品分类id
    private Long categoryId;

    private String categoryName;


    //菜品价格
    private BigDecimal price;

    //图片
    private String image;

    //0 停售 1 起售
    private Integer status;




    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}

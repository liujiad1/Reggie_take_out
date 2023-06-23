package com.reggie.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.reggie.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    //分类名称
    private String categoryName;


    //口味
    private List<DishFlavor> flavors = new ArrayList<>();


    //菜品价格
    private BigDecimal price;

    //图片
    private String image;

    //0 停售 1 起售
    private Integer status;

    //描述信息
    private String description;



    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}

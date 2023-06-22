package com.reggie.dto;

import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增菜品dto
 */
@Data
public class DishDto extends Dish {

    //口味
    private List<DishFlavor> flavors = new ArrayList<>();
	
    private String categoryName;
	
    private Integer copies;
}
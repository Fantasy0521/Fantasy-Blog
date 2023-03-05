package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "tag_name")
    private String name;

    /**
     * 标签颜色(可选)
     */
    private String color;

    //该标签下的博客文章
    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();

}

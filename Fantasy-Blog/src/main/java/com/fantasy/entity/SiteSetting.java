package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@TableName("site_setting")
public class SiteSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nameEn;

    private String nameZh;

    private String value;

    /**
     * 1基础设置，2页脚徽标，3资料卡，4友链信息
     */
    private Integer type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SiteSetting{" +
                "id=" + id +
                ", nameEn=" + nameEn +
                ", nameZh=" + nameZh +
                ", value=" + value +
                ", type=" + type +
                "}";
    }
}

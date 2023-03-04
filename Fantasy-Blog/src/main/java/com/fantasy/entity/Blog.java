package com.fantasy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Fantasy0521
 * @since 2023-03-03
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章首图，用于随机文章展示
     */
    private String firstPicture;

    /**
     * 文章正文
     */
    private String content;

    /**
     * 描述
     */
    private String description;

    /**
     * 公开或私密
     */
    private Boolean isPublished;

    /**
     * 推荐开关
     */
    private Boolean isRecommend;

    /**
     * 赞赏开关
     */
    private Boolean isAppreciation;

    /**
     * 评论开关
     */
    private Boolean isCommentEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 浏览次数
     */
    private Integer views;

    /**
     * 文章字数
     */
    private Integer words;

    /**
     * 阅读时长(分钟)
     */
    private Integer readTime;

    /**
     * 文章分类
     */
    private Long categoryId;

    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 密码保护
     */
    private String password;

    /**
     * 文章作者
     */
    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Boolean getIsAppreciation() {
        return isAppreciation;
    }

    public void setIsAppreciation(Boolean isAppreciation) {
        this.isAppreciation = isAppreciation;
    }

    public Boolean getIsCommentEnabled() {
        return isCommentEnabled;
    }

    public void setIsCommentEnabled(Boolean isCommentEnabled) {
        this.isCommentEnabled = isCommentEnabled;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getWords() {
        return words;
    }

    public void setWords(Integer words) {
        this.words = words;
    }

    public Integer getReadTime() {
        return readTime;
    }

    public void setReadTime(Integer readTime) {
        this.readTime = readTime;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title=" + title +
                ", firstPicture=" + firstPicture +
                ", content=" + content +
                ", description=" + description +
                ", isPublished=" + isPublished +
                ", isRecommend=" + isRecommend +
                ", isAppreciation=" + isAppreciation +
                ", isCommentEnabled=" + isCommentEnabled +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", views=" + views +
                ", words=" + words +
                ", readTime=" + readTime +
                ", categoryId=" + categoryId +
                ", isTop=" + isTop +
                ", password=" + password +
                ", userId=" + userId +
                "}";
    }
}

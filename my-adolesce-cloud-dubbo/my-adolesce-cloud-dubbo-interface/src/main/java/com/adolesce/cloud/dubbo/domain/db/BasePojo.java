package com.adolesce.cloud.dubbo.domain.db;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class BasePojo implements Serializable {
    @TableField(fill = FieldFill.INSERT) //MP自动填充
    private LocalDateTime createTime;

    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //解决返回前端时 LocalDateTime 格式化问题的方式一，方式二为WebMvcConfig扩展mvc框架的消息转换器
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
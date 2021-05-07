package com.szb.java5.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 石致彬
 * @since 2021-04-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Questionnaire对象", description="")
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "questionnaire_id", type = IdType.AUTO)
    private Long questionnaireId;

    @ApiModelProperty(value = "问卷标题",required = true)
    private String questionnaireName;

    private Date beginTime;

    private Date overTime;

    private Long departmentId;

    @TableField(exist = false)
    @ApiModelProperty(value = "持续天数",required = true)
    private Integer lastDays;

    @TableField(exist = false)
    private List<Questions> questions;

    public Questionnaire(Long questionnaireId,String questionnaireName,Date beginTime,Date overTime,Long departmentId){
        this.questionnaireId=questionnaireId;
        this.questionnaireName=questionnaireName;
        this.beginTime=beginTime;
        this.overTime=overTime;
        this.departmentId=departmentId;
    }


}

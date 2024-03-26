package cn.iocoder.yudao.module.crm.controller.admin.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - CRM 产品单位新增/修改 Request VO")
@Data
public class CrmProductUnitSaveReqVO {

    @Schema(description = "单位编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "单位名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "吨")
    @NotEmpty(message = "单位名字不能为空")
    private String name;

    @Schema(description = "单位状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "单位状态不能为空")
    private Integer status;

}
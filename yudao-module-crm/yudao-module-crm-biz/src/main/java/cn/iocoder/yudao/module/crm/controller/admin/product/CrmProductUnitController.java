package cn.iocoder.yudao.module.crm.controller.admin.product;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.crm.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.crm.dal.dataobject.product.CrmProductUnitDO;
import cn.iocoder.yudao.module.crm.service.product.CrmProductUnitService;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - CRM 产品单位")
@RestController
@RequestMapping("/crm/product-unit")
@Validated
public class CrmProductUnitController {

    @Resource
    private CrmProductUnitService productUnitService;

    @PostMapping("/create")
    @Operation(summary = "创建CRM 产品单位")
    @PreAuthorize("@ss.hasPermission('crm:product-unit:create')")
    public CommonResult<Long> createProductUnit(@Valid @RequestBody CrmProductUnitSaveReqVO createReqVO) {
        return success(productUnitService.createProductUnit(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新CRM 产品单位")
    @PreAuthorize("@ss.hasPermission('crm:product-unit:update')")
    public CommonResult<Boolean> updateProductUnit(@Valid @RequestBody CrmProductUnitSaveReqVO updateReqVO) {
        productUnitService.updateProductUnit(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除CRM 产品单位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('crm:product-unit:delete')")
    public CommonResult<Boolean> deleteProductUnit(@RequestParam("id") Long id) {
        productUnitService.deleteProductUnit(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得CRM 产品单位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:product-unit:query')")
    public CommonResult<CrmProductUnitRespVO> getProductUnit(@RequestParam("id") Long id) {
        CrmProductUnitDO productUnit = productUnitService.getProductUnit(id);
        return success(BeanUtils.toBean(productUnit, CrmProductUnitRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得CRM 产品单位分页")
    @PreAuthorize("@ss.hasPermission('crm:product-unit:query')")
    public CommonResult<PageResult<CrmProductUnitRespVO>> getProductUnitPage(@Valid CrmProductUnitPageReqVO pageReqVO) {
        PageResult<CrmProductUnitDO> pageResult = productUnitService.getProductUnitPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CrmProductUnitRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得产品单位精简列表", description = "只包含被开启的单位，主要用于前端的下拉选项")
    public CommonResult<List<CrmProductUnitRespVO>> getProductUnitSimpleList() {
        List<CrmProductUnitDO> list = productUnitService.getProductUnitListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, unit -> new CrmProductUnitRespVO().setId(unit.getId()).setName(unit.getName())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出CRM 产品单位 Excel")
    @PreAuthorize("@ss.hasPermission('crm:product-unit:export')")
    @OperateLog(type = EXPORT)
    public void exportProductUnitExcel(@Valid CrmProductUnitPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CrmProductUnitDO> list = productUnitService.getProductUnitPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "CRM 产品单位.xls", "数据", CrmProductUnitRespVO.class,
                        BeanUtils.toBean(list, CrmProductUnitRespVO.class));
    }

}
package cn.iocoder.yudao.module.crm.service.product;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.crm.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.crm.dal.dataobject.product.CrmProductCategoryDO;
import cn.iocoder.yudao.module.crm.dal.dataobject.product.CrmProductUnitDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * CRM 产品单位 Service 接口
 *
 * @author 芋道源码
 */
public interface CrmProductUnitService {

    /**
     * 创建CRM 产品单位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductUnit(@Valid CrmProductUnitSaveReqVO createReqVO);

    /**
     * 更新CRM 产品单位
     *
     * @param updateReqVO 更新信息
     */
    void updateProductUnit(@Valid CrmProductUnitSaveReqVO updateReqVO);

    /**
     * 删除CRM 产品单位
     *
     * @param id 编号
     */
    void deleteProductUnit(Long id);

    /**
     * 获得CRM 产品单位
     *
     * @param id 编号
     * @return CRM 产品单位
     */
    CrmProductUnitDO getProductUnit(Long id);

    /**
     * 获得CRM 产品单位分页
     *
     * @param pageReqVO 分页查询
     * @return CRM 产品单位分页
     */
    PageResult<CrmProductUnitDO> getProductUnitPage(CrmProductUnitPageReqVO pageReqVO);

    /**
     * 获得指定状态的产品单位列表
     * @author  Chen Yi
     * @date  2024/3/20 16:29
     * @param status  状态
     * @return  List<CrmProductUnitDO> 产品单位列表
     */
    List<CrmProductUnitDO> getProductUnitListByStatus(Integer status);

    /**
     * 获得产品单位列表
     *
     * @param ids 编号数组
     * @return 产品单位 列表
     */
    List<CrmProductUnitDO> getProductUnitList(Collection<Long> ids);

    /**
     * 获得产品单位 Map
     *
     * @param ids 编号数组
     * @return 产品单位  Map
     */
    default Map<Long, CrmProductUnitDO> getProductUnitMap(Collection<Long> ids) {
        return convertMap(getProductUnitList(ids), CrmProductUnitDO::getId);
    }

}
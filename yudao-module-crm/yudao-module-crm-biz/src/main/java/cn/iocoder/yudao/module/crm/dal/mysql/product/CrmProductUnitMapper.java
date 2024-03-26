package cn.iocoder.yudao.module.crm.dal.mysql.product;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.crm.dal.dataobject.product.CrmProductUnitDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.crm.controller.admin.product.vo.*;

/**
 * CRM 产品单位 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CrmProductUnitMapper extends BaseMapperX<CrmProductUnitDO> {

    default PageResult<CrmProductUnitDO> selectPage(CrmProductUnitPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CrmProductUnitDO>()
                .likeIfPresent(CrmProductUnitDO::getName, reqVO.getName())
                .eqIfPresent(CrmProductUnitDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CrmProductUnitDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(CrmProductUnitDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CrmProductUnitDO::getId));
    }

    /**
     * 根据状态查询产品单位
     * @author  Chen Yi
     * @date  2024/3/20 16:30
     * @param status 状态
     * @return  List<CrmProductUnitDO> 产品单位
     */
    default List<CrmProductUnitDO> selectListByStatus(Integer status) {
        return selectList(CrmProductUnitDO::getStatus, status);
    }

}
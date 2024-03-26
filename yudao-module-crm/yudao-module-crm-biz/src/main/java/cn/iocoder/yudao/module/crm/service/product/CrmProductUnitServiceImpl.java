package cn.iocoder.yudao.module.crm.service.product;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.crm.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.crm.dal.dataobject.product.CrmProductUnitDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.crm.dal.mysql.product.CrmProductUnitMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.crm.enums.ErrorCodeConstants.*;

/**
 * CRM 产品单位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CrmProductUnitServiceImpl implements CrmProductUnitService {

    @Resource
    private CrmProductUnitMapper productUnitMapper;

    @Override
    public Long createProductUnit(CrmProductUnitSaveReqVO createReqVO) {
        // 插入
        CrmProductUnitDO productUnit = BeanUtils.toBean(createReqVO, CrmProductUnitDO.class);
        productUnitMapper.insert(productUnit);
        // 返回
        return productUnit.getId();
    }

    @Override
    public void updateProductUnit(CrmProductUnitSaveReqVO updateReqVO) {
        // 校验存在
        validateProductUnitExists(updateReqVO.getId());
        // 更新
        CrmProductUnitDO updateObj = BeanUtils.toBean(updateReqVO, CrmProductUnitDO.class);
        productUnitMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductUnit(Long id) {
        // 校验存在
        validateProductUnitExists(id);
        // 删除
        productUnitMapper.deleteById(id);
    }

    private void validateProductUnitExists(Long id) {
        if (productUnitMapper.selectById(id) == null) {
            throw exception(PRODUCT_UNIT_NOT_EXISTS);
        }
    }

    @Override
    public CrmProductUnitDO getProductUnit(Long id) {
        return productUnitMapper.selectById(id);
    }

    @Override
    public PageResult<CrmProductUnitDO> getProductUnitPage(CrmProductUnitPageReqVO pageReqVO) {
        return productUnitMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CrmProductUnitDO> getProductUnitListByStatus(Integer status) {
        return productUnitMapper.selectListByStatus(status);
    }

    /**
     * 获得产品单位列表
     *
     * @param ids 编号数组
     * @return 产品单位 列表
     */
    @Override
    public List<CrmProductUnitDO> getProductUnitList(Collection<Long> ids) {
        return productUnitMapper.selectBatchIds(ids);
    }
}
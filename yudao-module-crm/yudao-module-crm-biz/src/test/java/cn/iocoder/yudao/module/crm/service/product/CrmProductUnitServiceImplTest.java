package java.cn.iocoder.yudao.module.crm.service.product;

import cn.iocoder.yudao.module.crm.service.product.CrmProductUnitServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.crm.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.crm.dal.dataobject.product.CrmProductUnitDO;
import cn.iocoder.yudao.module.crm.dal.mysql.product.CrmProductUnitMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.crm.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link CrmProductUnitServiceImpl} 的单元测试类
 *
 * @author 芋道源码
 */
@Import(CrmProductUnitServiceImpl.class)
public class CrmProductUnitServiceImplTest extends BaseDbUnitTest {

    @Resource
    private CrmProductUnitServiceImpl productUnitService;

    @Resource
    private CrmProductUnitMapper productUnitMapper;

    @Test
    public void testCreateProductUnit_success() {
        // 准备参数
        CrmProductUnitSaveReqVO createReqVO = randomPojo(CrmProductUnitSaveReqVO.class).setId(null);

        // 调用
        Long productUnitId = productUnitService.createProductUnit(createReqVO);
        // 断言
        assertNotNull(productUnitId);
        // 校验记录的属性是否正确
        CrmProductUnitDO productUnit = productUnitMapper.selectById(productUnitId);
        assertPojoEquals(createReqVO, productUnit, "id");
    }

    @Test
    public void testUpdateProductUnit_success() {
        // mock 数据
        CrmProductUnitDO dbProductUnit = randomPojo(CrmProductUnitDO.class);
        productUnitMapper.insert(dbProductUnit);// @Sql: 先插入出一条存在的数据
        // 准备参数
        CrmProductUnitSaveReqVO updateReqVO = randomPojo(CrmProductUnitSaveReqVO.class, o -> {
            o.setId(dbProductUnit.getId()); // 设置更新的 ID
        });

        // 调用
        productUnitService.updateProductUnit(updateReqVO);
        // 校验是否更新正确
        CrmProductUnitDO productUnit = productUnitMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, productUnit);
    }

    @Test
    public void testUpdateProductUnit_notExists() {
        // 准备参数
        CrmProductUnitSaveReqVO updateReqVO = randomPojo(CrmProductUnitSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> productUnitService.updateProductUnit(updateReqVO), PRODUCT_UNIT_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductUnit_success() {
        // mock 数据
        CrmProductUnitDO dbProductUnit = randomPojo(CrmProductUnitDO.class);
        productUnitMapper.insert(dbProductUnit);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductUnit.getId();

        // 调用
        productUnitService.deleteProductUnit(id);
       // 校验数据不存在了
       assertNull(productUnitMapper.selectById(id));
    }

    @Test
    public void testDeleteProductUnit_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productUnitService.deleteProductUnit(id), PRODUCT_UNIT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetProductUnitPage() {
       // mock 数据
       CrmProductUnitDO dbProductUnit = randomPojo(CrmProductUnitDO.class, o -> { // 等会查询到
           o.setName(null);
           o.setStatus(null);
           o.setCreator(null);
           o.setCreateTime(null);
       });
       productUnitMapper.insert(dbProductUnit);
       // 测试 name 不匹配
       productUnitMapper.insert(cloneIgnoreId(dbProductUnit, o -> o.setName(null)));
       // 测试 status 不匹配
       productUnitMapper.insert(cloneIgnoreId(dbProductUnit, o -> o.setStatus(null)));
       // 测试 creator 不匹配
       productUnitMapper.insert(cloneIgnoreId(dbProductUnit, o -> o.setCreator(null)));
       // 测试 createTime 不匹配
       productUnitMapper.insert(cloneIgnoreId(dbProductUnit, o -> o.setCreateTime(null)));
       // 准备参数
       CrmProductUnitPageReqVO reqVO = new CrmProductUnitPageReqVO();
       reqVO.setName(null);
       reqVO.setStatus(null);
       reqVO.setCreator(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<CrmProductUnitDO> pageResult = productUnitService.getProductUnitPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbProductUnit, pageResult.getList().get(0));
    }

}
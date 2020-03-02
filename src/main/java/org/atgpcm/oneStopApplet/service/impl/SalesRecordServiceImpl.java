package org.atgpcm.oneStopApplet.service.impl;

import org.atgpcm.oneStopApplet.domain.entity.SalesRecord;
import org.atgpcm.oneStopApplet.dao.SalesRecordDao;
import org.atgpcm.oneStopApplet.service.SalesRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 销售记录 服务实现类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout = 10,rollbackFor = Exception.class)
public class SalesRecordServiceImpl extends ServiceImpl<SalesRecordDao, SalesRecord> implements SalesRecordService {

    @Autowired
    private SalesRecordDao salesRecordDao;

    @Override
    public SalesRecord selectBrandInfo(String time, Integer brandId) {
        return this.salesRecordDao.selectBrandInfo(time,brandId);
    }

    @Override
    public List<SalesRecord> selectCompeteInfo(String time, Integer brandId) {
        return this.salesRecordDao.selectCompeteInfo(time,brandId);
    }

    @Override
    public List<SalesRecord> selectSalesRecordGroupByProvince(String time, List<Integer> brandIdList) {
        return this.salesRecordDao.selectSalesRecordGroupByProvince(time,brandIdList);
    }

    @Override
    public List<SalesRecord> selectSalesRecordByProvinceId(String time, Integer provinceId, Integer brandId) {
        return this.salesRecordDao.selectSalesRecordByProvinceId(time,provinceId,brandId);
    }

    @Override
    public List<SalesRecord> selectSalesRecordGroupByBrandId(String time, Integer brandId) {
        return this.salesRecordDao.selectSalesRecordGroupByBrandId(time,brandId);
    }

    @Override
    public List<SalesRecord> selectSalesRecordGroupBySeriesId(String time, Integer seriesId) {
        return this.salesRecordDao.selectSalesRecordGroupBySeriesId(time,seriesId);
    }

    @Override
    public List<SalesRecord> selectSeriesSalesRecordGroupBySeriesId(String time, Integer brandId) {
        return this.salesRecordDao.selectSeriesSalesRecordGroupBySeriesId(time,brandId);
    }

    @Override
    public List<SalesRecord> selectModelSalesRecordGroupByModelId(String time, Integer seriesId) {
        return this.salesRecordDao.selectModelSalesRecordGroupByModelId(time,seriesId);
    }

    @Override
    public List<SalesRecord> selectTrendByBrandId(List<String> timeList, Integer brandId) {
        return this.salesRecordDao.selectTrendByBrandId(timeList,brandId);
    }

    @Override
    public List<SalesRecord> selectTrendBySeriesId(List<String> timeList, Integer seriesId) {
        return this.salesRecordDao.selectTrendBySeriesId(timeList,seriesId);
    }

    @Override
    public List<SalesRecord> selectRegionDistribution(String time, Integer brandId, Integer seriesId, String regionType) {
        return this.salesRecordDao.selectRegionDistribution(time,brandId,seriesId,regionType);
    }

    @Override
    public List<SalesRecord> selectCompeteRegionDistribution(String time, List<Integer> brandIdList, List<Integer> seriesIdList, String regionType, Integer paramterId) {
        return this.salesRecordDao.selectCompeteRegionDistribution(time,brandIdList,seriesIdList,regionType,paramterId);
    }

    @Override
    public List<SalesRecord> selectRegionDistributionInfo(String time, Integer brandId, Integer seriesId, Integer regionId, Integer provinceId) {
        return this.salesRecordDao.selectRegionDistributionInfo(time,brandId,seriesId,regionId,provinceId);
    }

    @Override
    public List<SalesRecord> selectCompeteRegionDistributionInfo(String time, List<Integer> brandIdList, List<Integer> seriesIdList, Integer provinceId, Integer cityId) {
        return this.salesRecordDao.selectCompeteRegionDistributionInfo(time,brandIdList,seriesIdList,provinceId,cityId);
    }

    @Override
    public List<SalesRecord> selectPercentList(String time, List<String> priceList, Integer brandId, Integer seriesId) {
        return this.salesRecordDao.selectPercentList(time,priceList,brandId,seriesId);
    }

    @Override
    public Integer getMaxSalesNumByBrand(List<Integer> ids, String time, String regionType) {
        return this.salesRecordDao.getMaxSalesNumByBrand(ids,time,regionType);
    }

    @Override
    public Integer getMaxSalesNumBySeries(List<Integer> ids, String time, String regionType) {
        return this.salesRecordDao.getMaxSalesNumBySeries(ids,time,regionType);
    }

    @Override
    public BigDecimal getMaxPriceByBrand(List<Integer> ids, String time, String regionType) {
        return this.salesRecordDao.getMaxPriceByBrand(ids,time,regionType);
    }

    @Override
    public BigDecimal getMaxPriceBySeries(List<Integer> ids, String time, String regionType) {
        return this.salesRecordDao.getMaxPriceBySeries(ids,time,regionType);
    }


}

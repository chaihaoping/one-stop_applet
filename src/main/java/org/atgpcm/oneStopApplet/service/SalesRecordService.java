package org.atgpcm.oneStopApplet.service;

import org.atgpcm.oneStopApplet.domain.entity.SalesRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 销售记录 服务类
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
public interface SalesRecordService extends IService<SalesRecord> {

    SalesRecord selectBrandInfo(String time, Integer brandId);

    List<SalesRecord> selectCompeteInfo(String time, Integer brandId);

    List<SalesRecord> selectSalesRecordGroupByProvince(String time, List<Integer> brandIdList);

    List<SalesRecord> selectSalesRecordByProvinceId(String time, Integer provinceId, Integer brandId);

    List<SalesRecord> selectSalesRecordGroupByBrandId(String time, Integer brandId);

    List<SalesRecord> selectSalesRecordGroupBySeriesId(String time, Integer seriesId);

    List<SalesRecord> selectSeriesSalesRecordGroupBySeriesId(String time, Integer brandId);

    List<SalesRecord> selectModelSalesRecordGroupByModelId(String time, Integer seriesId);

    List<SalesRecord> selectTrendByBrandId(List<String> timeList, Integer brandId);

    List<SalesRecord> selectTrendBySeriesId(List<String> timeList, Integer seriesId);

    List<SalesRecord> selectRegionDistribution(String time, Integer brandId, Integer seriesId, String regionType);

    List<SalesRecord> selectCompeteRegionDistribution(String time, List<Integer> brandIdList, List<Integer> seriesIdList, String regionType, Integer paramterId);

    List<SalesRecord> selectRegionDistributionInfo(String time, Integer brandId, Integer seriesId, Integer regionId, Integer provinceId);

    List<SalesRecord> selectCompeteRegionDistributionInfo(String time, List<Integer> brandIdList, List<Integer> seriesIdList, Integer provinceId, Integer cityId);

    List<SalesRecord> selectPercentList(String time, List<String> priceList, Integer brandId, Integer seriesId);

    Integer getMaxSalesNumByBrand(List<Integer> ids, String time, String regionType);

    Integer getMaxSalesNumBySeries(List<Integer> ids, String time, String regionType);

    BigDecimal getMaxPriceByBrand(List<Integer> ids, String time, String regionType);

    BigDecimal getMaxPriceBySeries(List<Integer> ids, String time, String regionType);
}

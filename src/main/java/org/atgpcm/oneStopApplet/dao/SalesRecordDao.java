package org.atgpcm.oneStopApplet.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.atgpcm.oneStopApplet.domain.entity.SalesRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 销售记录 Mapper 接口
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@Repository
public interface SalesRecordDao extends BaseMapper<SalesRecord> {

    @DS("master")
    SalesRecord selectBrandInfo(@Param("time") String time, @Param("brandId") Integer brandId);

    @DS("master")
    List<SalesRecord> selectCompeteInfo(@Param("time") String time, @Param("brandId") Integer brandId);

    @DS("master")
    List<SalesRecord> selectSalesRecordGroupByProvince(@Param("time") String time, @Param("brandIdList") List<Integer> brandIdList);

    @DS("master")
    List<SalesRecord> selectSalesRecordByProvinceId(@Param("time") String time, @Param("provinceId") Integer provinceId,
                                                    @Param("brandId") Integer brandId);

    @DS("master")
    List<SalesRecord> selectSalesRecordGroupByBrandId(@Param("time") String time, @Param("brandId") Integer brandId);

    @DS("master")
    List<SalesRecord> selectSalesRecordGroupBySeriesId(@Param("time") String time, @Param("seriesId") Integer seriesId);

    @DS("master")
    List<SalesRecord> selectSeriesSalesRecordGroupBySeriesId(@Param("time") String time, @Param("brandId") Integer brandId);

    @DS("master")
    List<SalesRecord> selectModelSalesRecordGroupByModelId(@Param("time") String time, @Param("seriesId") Integer seriesId);

    @DS("master")
    List<SalesRecord> selectTrendByBrandId(@Param("timeList") List<String> timeList, @Param("brandId") Integer brandId);

    @DS("master")
    List<SalesRecord> selectTrendBySeriesId(@Param("timeList") List<String> timeList, @Param("seriesId") Integer seriesId);

    @DS("master")
    List<SalesRecord> selectRegionDistribution(@Param("time") String time, @Param("brandId") Integer brandId,
                                               @Param("seriesId") Integer seriesId, @Param("regionType") String regionType);

    @DS("master")
    List<SalesRecord> selectCompeteRegionDistribution(@Param("time") String time, @Param("brandIdList") List<Integer> brandIdList,
                                                      @Param("seriesIdList") List<Integer> seriesIdList, @Param("regionType") String regionType,
                                                      @Param("paramterId") Integer paramterId);

    @DS("master")
    List<SalesRecord> selectRegionDistributionInfo(@Param("time") String time, @Param("brandId") Integer brandId,
                                                   @Param("seriesId") Integer seriesId, @Param("regionId") Integer regionId,
                                                   @Param("provinceId") Integer provinceId);

    @DS("master")
    List<SalesRecord> selectCompeteRegionDistributionInfo(@Param("time") String time, @Param("brandIdList") List<Integer> brandIdList,
                                                          @Param("seriesIdList") List<Integer> seriesIdList, @Param("provinceId") Integer provinceId,
                                                          @Param("cityId") Integer cityId);

    @DS("master")
    List<SalesRecord> selectPercentList(@Param("time") String time, @Param("priceList") List<String> priceList,
                                        @Param("brandId") Integer brandId, @Param("seriesId") Integer seriesId);

    @DS("master")
    Integer getMaxSalesNumByBrand(@Param("ids") List<Integer> ids, @Param("time") String time,
                                  @Param("regionType") String regionType);

    @DS("master")
    Integer getMaxSalesNumBySeries(@Param("ids") List<Integer> ids, @Param("time") String time,
                                   @Param("regionType") String regionType);

    @DS("master")
    BigDecimal getMaxPriceByBrand(@Param("ids") List<Integer> ids, @Param("time") String time,
                                  @Param("regionType") String regionType);

    @DS("master")
    BigDecimal getMaxPriceBySeries(@Param("ids") List<Integer> ids, @Param("time") String time,
                                   @Param("regionType") String regionType);
}

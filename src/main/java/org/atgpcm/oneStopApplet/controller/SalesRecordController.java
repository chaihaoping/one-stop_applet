package org.atgpcm.oneStopApplet.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.RedisKeys;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.BrandRelevanceCompete;
import org.atgpcm.oneStopApplet.domain.entity.SalesRecord;
import org.atgpcm.oneStopApplet.domain.entity.SeriesRelevanceCompete;
import org.atgpcm.oneStopApplet.service.BrandRelevanceCompeteService;
import org.atgpcm.oneStopApplet.service.SalesRecordService;
import org.atgpcm.oneStopApplet.service.SeriesRelevanceCompeteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 销售记录 前端控制器
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-06
 */
@RestController
@RequestMapping("/salesRecord")
@Slf4j
@Api(tags = "概况、量价相关模块")
public class SalesRecordController extends BaseController {

    @Autowired
    private SalesRecordService salesRecordService;
    @Autowired
    private BrandRelevanceCompeteService brandRelevanceCompeteService;
    @Autowired
    private SeriesRelevanceCompeteService seriesRelevanceCompeteService;

    @PostMapping("/getNationalSalesAndPrice")
    @ApiOperation(value="概况-全国销量/均价", notes="概况-全国销量/均价", produces="application/json")
    @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String")
    public Result getNationalSalesAndPrice(String time) {
        log.info("=============概况-全国销量/均价=============");
        try {
            // 本品信息
            SalesRecord salesRecord = this.salesRecordService.selectBrandInfo(time,this.getCurrentUser().getBrandId());
            // 竞品信息
            List<SalesRecord> competeList = this.salesRecordService.selectCompeteInfo(time,this.getCurrentUser().getBrandId());
            competeList.stream().forEach(sr -> {
                if (sr.getSalesNum() > salesRecord.getSalesNum()) {
                    sr.setSalesNumRed(true);
                } else {
                    sr.setSalesNumRed(false);
                }
                if (sr.getAvgPrice().compareTo(salesRecord.getAvgPrice()) == 1) {
                    sr.setAvgPriceRed(true);
                } else {
                    sr.setAvgPriceRed(false);
                }
            });
            salesRecord.setCompeteList(competeList);
            return Result.success(salesRecord);
        } catch (Exception e) {
            log.error("\n=============概况-全国销量/均价:{}",e);
            return Result.error(BizStatusEnum.NATIONAL_SALES_PRICE_FAIL.getCode(),BizStatusEnum.NATIONAL_SALES_PRICE_FAIL.getMsg());
        }
    }

    @PostMapping("/getAreaPercent")
    @ApiOperation(value="优劣区域占比", notes="优劣区域占比", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="order",value="优势:advantage,劣势:disadvantage",required=true,dataType="String")
    })
    public Result getAreaPercent(String time,String order) {
        log.info("=============优劣区域占比=============");
        try {
            List<Integer> brandIdList = this.brandRelevanceCompeteService.list(new QueryWrapper<BrandRelevanceCompete>().
                    eq("brand_id",this.getCurrentUser().getBrandId())).stream().map(BrandRelevanceCompete::getCompeteId).
                    collect(Collectors.toList());
            // 本品及竞品id集合
            brandIdList.add(this.getCurrentUser().getBrandId());
            // 本品及竞品信息
            List<SalesRecord> list = this.salesRecordService.selectSalesRecordGroupByProvince(time,brandIdList);
            // 本品信息
            List<SalesRecord> brandList = list.stream().filter(salesRecord -> salesRecord.getBrandId().intValue() == this.getCurrentUser().getBrandId()).
                    collect(Collectors.toList());
            // 若无本品数据，则返回null，无论竞品是否存在数据
            if (brandList == null || brandList.isEmpty()) {
                return Result.success();
            }
            // 竞品信息
            List<SalesRecord> competeList = list.stream().filter(salesRecord -> salesRecord.getBrandId().intValue() != this.getCurrentUser().getBrandId()).
                    collect(Collectors.toList());
            brandList.stream().forEach(salesRecord -> {
                AtomicReference<Integer> sum = new AtomicReference<>(salesRecord.getSalesNum());
                competeList.stream().forEach(sr -> {
                    if (salesRecord.getProvinceId().intValue() == sr.getProvinceId().intValue()) {
                        sum.set(sum.get() + sr.getSalesNum());
                    }
                });
                if (sum.get().intValue() == 0) {
                    salesRecord.setPercent(new BigDecimal(0));
                } else {
                    salesRecord.setPercent(new BigDecimal(salesRecord.getSalesNum()).divide(new BigDecimal(sum.get()),4,BigDecimal.ROUND_HALF_UP));
                }
            });
            List<SalesRecord> percentList = null;
            // 倒序
            if (order.equals("advantage")) {
                percentList = brandList.stream().sorted(Comparator.comparing(SalesRecord::getPercent).reversed()).
                        limit(5).collect(Collectors.toList());
            } else { // 正序
                percentList = brandList.stream().sorted(Comparator.comparing(SalesRecord::getPercent)).
                        limit(5).collect(Collectors.toList());
            }
            return Result.success(percentList);
        } catch (Exception e) {
            log.error("\n=============优劣区域占比:{}",e);
            return Result.error(BizStatusEnum.AREA_PERCENT_FAIL.getCode(),BizStatusEnum.AREA_PERCENT_FAIL.getMsg());
        }
    }

    @PostMapping("/getSeriesListByProvince")
    @ApiOperation(value="区域内车系详情", notes="区域内车系详情", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="provinceId",value="省级id",required=true,dataType="String")
    })
    public Result getSeriesListByProvince(String time,Integer provinceId) {
        log.info("=============区域内车系详情=============");
        try {
            List<SalesRecord> salesRecordList = this.salesRecordService.selectSalesRecordByProvinceId(time,provinceId,this.getCurrentUser().getBrandId());
            Integer sum = salesRecordList.stream().mapToInt(SalesRecord::getSalesNum).sum();
            salesRecordList.stream().forEach(salesRecord -> {
                if (sum.intValue() == 0) {
                    salesRecord.setPercent(new BigDecimal(0));
                } else {
                    salesRecord.setPercent(new BigDecimal(salesRecord.getSalesNum()).
                            divide(new BigDecimal(sum),4,BigDecimal.ROUND_HALF_UP));
                }
            });
            return Result.success(salesRecordList);
        } catch (Exception e) {
            log.error("\n=============区域内车系详情:{}",e);
            return Result.error(BizStatusEnum.PROVINCE_SERIES_FAIL.getCode(),BizStatusEnum.PROVINCE_SERIES_FAIL.getMsg());
        }
    }

    @PostMapping("/getBrandId")
    @ApiOperation(value="获取品牌id", notes="获取品牌id", produces="application/json")
    public Result getBrandId() {
        log.info("=============获取品牌id=============");
        try {
            return Result.success(this.getCurrentUser().getBrandId());
        } catch (Exception e) {
            log.error("\n=============获取品牌id:{}",e);
            return Result.error(BizStatusEnum.USER_IS_NOT_EXIST.getCode(),BizStatusEnum.USER_IS_NOT_EXIST.getMsg());
        }
    }

    @PostMapping("/getQuantityPriceInfo")
    @ApiOperation(value="量价概况", notes="量价概况", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="brandId",value="品牌id", dataType="int"),
            @ApiImplicitParam(name="seriesId",value="车系id", dataType="int")
    })
    public Result getQuantityPriceInfo(String time,Integer brandId,Integer seriesId) {
        log.info("=============量价概况=============");
        try {
            List<SalesRecord> salesRecordList = null;
            if (brandId != null) { // 品牌
                salesRecordList = this.salesRecordService.selectSalesRecordGroupByBrandId(time,brandId);
            } else {// 车系
                salesRecordList = this.salesRecordService.selectSalesRecordGroupBySeriesId(time,seriesId);
            }
            return Result.success(salesRecordList);
        } catch (Exception e) {
            log.error("\n=============量价概况:{}",e);
            return Result.error(BizStatusEnum.QUANTITY_PRICE_INFO_FAIL.getCode(),BizStatusEnum.QUANTITY_PRICE_INFO_FAIL.getMsg());
        }
    }

    @PostMapping("/getQuantityPriceSalesNum")
    @ApiOperation(value="车系/车型销量", notes="车系/车型销量", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="brandId",value="品牌id", dataType="int"),
            @ApiImplicitParam(name="seriesId",value="车系id", dataType="int")
    })
    public Result getQuantityPriceSalesNum(String time,Integer brandId,Integer seriesId) {
        log.info("=============车系/车型销量=============");
        try {
            List<SalesRecord> salesRecordList = null;
            if (brandId != null) { // 车系销量
                salesRecordList = this.salesRecordService.selectSeriesSalesRecordGroupBySeriesId(time,brandId);
            } else { // 车型销量
                salesRecordList = this.salesRecordService.selectModelSalesRecordGroupByModelId(time,seriesId);
            }
            Integer sum = salesRecordList.stream().mapToInt(SalesRecord::getSalesNum).sum();
            salesRecordList.stream().forEach(salesRecord -> {
                if (sum.intValue() == 0) {
                    salesRecord.setPercent(new BigDecimal(0));
                } else {
                    salesRecord.setPercent(new BigDecimal(salesRecord.getSalesNum()).
                            divide(new BigDecimal(sum),4,BigDecimal.ROUND_HALF_UP));
                }
            });
            return Result.success(salesRecordList);
        } catch (Exception e) {
            log.error("\n=============车系/车型销量:{}",e);
            return Result.error(BizStatusEnum.CAR_SALES_NUM_FAIL.getCode(),BizStatusEnum.CAR_SALES_NUM_FAIL.getMsg());
        }
    }

    /**
     * 获取一段时间内的每一天日期
     * @param start
     * @param end
     * @return
     */
    public List<String> getBetweenDate(String start, String end) {
        List<String> list = Lists.newArrayList();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.toString()));
        return list;
    }

    @PostMapping("/getQuantityPriceTrend")
    @ApiOperation(value="量价走势", notes="量价走势", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="brandId",value="品牌id", dataType="int"),
            @ApiImplicitParam(name="seriesId",value="车系id", dataType="int")
    })
    @Cacheable(value = RedisKeys.TIME_GROUP_10h, key = "#time+'-'+#brandId+'-'+#seriesId", unless = "#result eq null ")
    public Result getQuantityPriceTrend(String time,Integer brandId,Integer seriesId) {
        log.info("=============量价走势=============");
        try {
            List<String> timeList = this.getBetweenDate(time.split("_")[0],time.split("_")[1]);
            if (timeList == null || timeList.isEmpty()) {
                timeList.add("2020-01-31");
            }
            JSONArray jsonArray = new JSONArray();
            if (brandId != null) { // 品牌
                // 竞品id
                List<Integer> brandIdList = this.brandRelevanceCompeteService.list(new QueryWrapper<BrandRelevanceCompete>().
                        eq("brand_id",this.getCurrentUser().getBrandId())).stream().map(BrandRelevanceCompete::getCompeteId).
                        collect(Collectors.toList());
                // 加入本品id
                brandIdList.add(0,brandId);
                // 竞品及本品
                brandIdList.stream().forEach(competeId -> {
                    jsonArray.add(this.salesRecordService.selectTrendByBrandId(timeList,competeId));
                });
            } else { // 车系
                // 竞品id
                List<Integer> seriesIdList = this.seriesRelevanceCompeteService.list(new QueryWrapper<SeriesRelevanceCompete>().
                        eq("series_id",seriesId).select()).stream().map(SeriesRelevanceCompete::getCompeteId).collect(Collectors.toList());
                // 加入本品id
                seriesIdList.add(0,seriesId);
                // 竞品及本品
                seriesIdList.stream().forEach(competeId -> {
                    jsonArray.add(this.salesRecordService.selectTrendBySeriesId(timeList,competeId));
                });
            }
            return Result.success(jsonArray);
        } catch (Exception e) {
            log.error("\n=============量价走势:{}",e);
            return Result.error(BizStatusEnum.QUANTITY_PRICE_TREND_FAIL.getCode(),BizStatusEnum.QUANTITY_PRICE_TREND_FAIL.getMsg());
        }
    }

    @PostMapping("/getQuantityPriceRegionDistribution")
    @ApiOperation(value="区域分布", notes="区域分布", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="brandId",value="品牌id", dataType="int"),
            @ApiImplicitParam(name="seriesId",value="车系id", dataType="int"),
            @ApiImplicitParam(name="regionType",value="大区：region，省份：province，市：city",required=true,dataType="String"),
            @ApiImplicitParam(name="pageNum",value="当前页数",required=true,dataType="int"),
            @ApiImplicitParam(name="pageSize",value="当前页显示5条",required=true,dataType="int")
    })
    public Result getQuantityPriceRegionDistribution(String time,Integer brandId,Integer seriesId,String regionType,Integer pageNum,Integer pageSize) {
        log.info("=============区域分布=============");
        try {
            List<Integer> brandIdList = this.brandRelevanceCompeteService.list(new QueryWrapper<BrandRelevanceCompete>().
                    eq("brand_id",brandId).select()).stream().map(BrandRelevanceCompete::getCompeteId).collect(Collectors.toList());
            List<Integer> seriesIdList = this.seriesRelevanceCompeteService.list(new QueryWrapper<SeriesRelevanceCompete>().
                    eq("series_id",seriesId).select()).stream().map(SeriesRelevanceCompete::getCompeteId).collect(Collectors.toList());
            PageHelper.startPage(pageNum,pageSize);
            List<SalesRecord> salesRecordList = this.salesRecordService.selectRegionDistribution(time,brandId,seriesId,regionType);
            salesRecordList.stream().forEach(salesRecord -> {
                SalesRecord sr = new SalesRecord();
                sr.setSalesNum(salesRecord.getSalesNum());
                sr.setBrandId(salesRecord.getBrandId());
                sr.setBrandName(salesRecord.getBrandName());
                sr.setSeriesId(salesRecord.getSeriesId());
                sr.setSeriesName(salesRecord.getSeriesName());
                sr.setRegionId(salesRecord.getRegionId());
                sr.setRegionName(salesRecord.getRegionName());
                sr.setProvinceId(salesRecord.getProvinceId());
                sr.setProvinceName(salesRecord.getProvinceName());
                sr.setCityId(salesRecord.getCityId());
                sr.setCityName(salesRecord.getCityName());
                sr.setAvgPrice(salesRecord.getAvgPrice());
                if (brandId != null) { // 品牌
                    List<SalesRecord> list = null;
                    if (regionType.equals("region")) {
                        list = this.salesRecordService.selectCompeteRegionDistribution(time,brandIdList,null,regionType,salesRecord.getRegionId());
                    } else if (regionType.equals("province")) {
                        list = this.salesRecordService.selectCompeteRegionDistribution(time,brandIdList,null,regionType,salesRecord.getProvinceId());
                    } else if (regionType.equals("city")) {
                        list = this.salesRecordService.selectCompeteRegionDistribution(time,brandIdList,null,regionType,salesRecord.getCityId());
                    }
                    list.add(0,sr);
                    salesRecord.setCompeteList(list);
                }
                if (seriesId != null) { // 车系
                    List<SalesRecord> list = null;
                    if (regionType.equals("region")) {
                        list = this.salesRecordService.selectCompeteRegionDistribution(time,null,seriesIdList,regionType,salesRecord.getRegionId());
                    } else if (regionType.equals("province")) {
                        list = this.salesRecordService.selectCompeteRegionDistribution(time,null,seriesIdList,regionType,salesRecord.getProvinceId());
                    } else if (regionType.equals("city")) {
                        list = this.salesRecordService.selectCompeteRegionDistribution(time,null,seriesIdList,regionType,salesRecord.getCityId());
                    }
                    list.add(0,sr);
                    salesRecord.setCompeteList(list);
                }
            });
            PageInfo<SalesRecord> pageInfo = new PageInfo<>(salesRecordList);
            JSONObject object = new JSONObject();
            object.put("pageInfo",pageInfo);
            if (brandId != null) {
                brandIdList.add(brandId);
                object.put("max",this.salesRecordService.getMaxSalesNumByBrand(brandIdList,time,regionType));
                object.put("maxPrice",this.salesRecordService.getMaxPriceByBrand(brandIdList,time,regionType));
            }
            if (seriesId != null) {
                seriesIdList.add(seriesId);
                object.put("max",this.salesRecordService.getMaxSalesNumBySeries(seriesIdList,time,regionType));
                object.put("maxPrice",this.salesRecordService.getMaxPriceBySeries(seriesIdList,time,regionType));
            }
            return Result.success(object);
        } catch (Exception e) {
            log.error("\n=============区域分布:{}",e);
            return Result.error(BizStatusEnum.QUANTITY_PRICE_REGION_DISTRIBUTION_FAIL.getCode(),BizStatusEnum.QUANTITY_PRICE_REGION_DISTRIBUTION_FAIL.getMsg());
        }
    }

    @PostMapping("/getQuantityPriceRegionDistributionInfo")
    @ApiOperation(value="区域分布详情", notes="区域分布详情", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="brandId",value="品牌id", dataType="int"),
            @ApiImplicitParam(name="seriesId",value="车系id", dataType="int"),
            @ApiImplicitParam(name="regionId",value="大区id", dataType="int"),
            @ApiImplicitParam(name="provinceId",value="省id", dataType="int"),
            @ApiImplicitParam(name="pageNum",value="当前页数",required=true,dataType="int"),
            @ApiImplicitParam(name="pageSize",value="当前页显示5条",required=true,dataType="int")
    })
    public Result getQuantityPriceRegionDistributionInfo(String time,Integer brandId,Integer seriesId,Integer regionId,Integer provinceId,Integer pageNum,Integer pageSize) {
        log.info("=============区域分布详情=============");
        try {
            List<Integer> brandIdList = this.brandRelevanceCompeteService.list(new QueryWrapper<BrandRelevanceCompete>().
                    eq("brand_id",brandId).select()).stream().map(BrandRelevanceCompete::getCompeteId).collect(Collectors.toList());
            List<Integer> seriesIdList = this.seriesRelevanceCompeteService.list(new QueryWrapper<SeriesRelevanceCompete>().
                    eq("series_id",seriesId).select()).stream().map(SeriesRelevanceCompete::getCompeteId).collect(Collectors.toList());
            PageHelper.startPage(pageNum,pageSize);
            List<SalesRecord> salesRecordList = this.salesRecordService.selectRegionDistributionInfo(time,brandId,seriesId,regionId,provinceId);
            salesRecordList.stream().forEach(salesRecord -> {
                SalesRecord sr = new SalesRecord();
                sr.setSalesNum(salesRecord.getSalesNum());
                sr.setBrandId(salesRecord.getBrandId());
                sr.setBrandName(salesRecord.getBrandName());
                sr.setSeriesId(salesRecord.getSeriesId());
                sr.setSeriesName(salesRecord.getSeriesName());
                sr.setProvinceId(salesRecord.getProvinceId());
                sr.setProvinceName(salesRecord.getProvinceName());
                sr.setCityId(salesRecord.getCityId());
                sr.setCityName(salesRecord.getCityName());
                sr.setAvgPrice(salesRecord.getAvgPrice());
                if (brandId != null) { // 品牌
                    List<SalesRecord> list = this.salesRecordService.selectCompeteRegionDistributionInfo(time,brandIdList,null,salesRecord.getProvinceId(),salesRecord.getCityId());
                    list.add(0,sr);
                    salesRecord.setCompeteList(list);
                }
                if (seriesId != null) { // 车系
                    List<SalesRecord> list = this.salesRecordService.selectCompeteRegionDistributionInfo(time,null,seriesIdList,salesRecord.getProvinceId(),salesRecord.getCityId());
                    list.add(0,sr);
                    salesRecord.setCompeteList(list);
                }
            });
            PageInfo<SalesRecord> pageInfo = new PageInfo<>(salesRecordList);
            JSONObject object = new JSONObject();
            object.put("pageInfo",pageInfo);
            String regionType = "";
            if (regionId != null) {
                regionType = "province";
            }
            if (provinceId != null) {
                regionType = "city";
            }
            if (brandId != null) {
                brandIdList.add(brandId);
                object.put("max",this.salesRecordService.getMaxSalesNumByBrand(brandIdList,time,regionType));
                object.put("maxPrice",this.salesRecordService.getMaxPriceByBrand(brandIdList,time,regionType));
            }
            if (seriesId != null) {
                seriesIdList.add(seriesId);
                object.put("max",this.salesRecordService.getMaxSalesNumBySeries(seriesIdList,time,regionType));
                object.put("maxPrice",this.salesRecordService.getMaxPriceBySeries(seriesIdList,time,regionType));
            }
            return Result.success(object);
        } catch (Exception e) {
            log.error("\n=============区域分布详情:{}",e);
            return Result.error(BizStatusEnum.QUANTITY_PRICE_REGION_DISTRIBUTION_INFO_FAIL.getCode(),BizStatusEnum.QUANTITY_PRICE_REGION_DISTRIBUTION_INFO_FAIL.getMsg());
        }
    }

    @PostMapping("/getPricePercent")
    @ApiOperation(value="价格占比", notes="价格占比", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name="time",value="时间筛选",required=true,dataType="String"),
            @ApiImplicitParam(name="brandId",value="品牌id", dataType="int"),
            @ApiImplicitParam(name="seriesId",value="车系id", dataType="int")
    })
    public Result getPricePercent(String time,Integer brandId,Integer seriesId) {
        log.info("=============价格占比=============");
        try {
            List<String> priceList = Stream.of("8万以下","8万到12万","12万到18万","18万到25万","25万到40万","40万以上").collect(Collectors.toList());
            JSONArray jsonArray = new JSONArray();
            if (brandId != null) {
                 // 竞品id
                List<Integer> brandIdList = this.brandRelevanceCompeteService.list(new QueryWrapper<BrandRelevanceCompete>().
                        eq("brand_id",brandId).select()).stream().map(BrandRelevanceCompete::getCompeteId).collect(Collectors.toList());
                // 加入本品id
                brandIdList.add(0,brandId);
                brandIdList.stream().forEach(id -> {
                    jsonArray.add(this.salesRecordService.selectPercentList(time,priceList,id,null));
                });
            }
            if (seriesId != null) {
                // 竞品id
                List<Integer> seriesIdList = this.seriesRelevanceCompeteService.list(new QueryWrapper<SeriesRelevanceCompete>().
                        eq("series_id",seriesId).select()).stream().map(SeriesRelevanceCompete::getCompeteId).collect(Collectors.toList());
                // 加入本品id
                seriesIdList.add(0,seriesId);
                seriesIdList.stream().forEach(id -> {
                    jsonArray.add(this.salesRecordService.selectPercentList(time,priceList,null,id));
                });
            }
            return Result.success(jsonArray);
        } catch (Exception e) {
            log.error("\n=============价格占比:{}",e);
            return Result.error(BizStatusEnum.PRICE_PERCENT_FAIL.getCode(),BizStatusEnum.PRICE_PERCENT_FAIL.getMsg());
        }
    }
}

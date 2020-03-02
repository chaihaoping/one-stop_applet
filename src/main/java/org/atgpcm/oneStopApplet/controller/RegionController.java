package org.atgpcm.oneStopApplet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.Area;
import org.atgpcm.oneStopApplet.domain.entity.Region;
import org.atgpcm.oneStopApplet.service.AreaService;
import org.atgpcm.oneStopApplet.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 品牌所辖大区表 前端控制器
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@RestController
@RequestMapping("/region")
@Slf4j
@Api(tags = "品牌所辖大区相关模块")
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;
    @Autowired
    private AreaService areaService;

    /**
     * @Cacheable 先从缓存中读取数据，如果缓存中没有则从DB读取
     * unless 表示条件表达式如果成立的，则不放入缓存
     *
     * @return
     */
    @PostMapping("/getRegionInfoList")
    @ApiOperation(value="获取当前品牌所辖大区及省市信息", notes="大区包含省市信息", produces="application/json")
    public Result getRegionInfoList() {
        log.info("=============获取当前品牌所辖大区及省市信息=============");
        try {
            List<Region> regionList = this.regionService.selectRegionList(this.getCurrentUser().getBrandId());
            // 大区
            regionList.stream().forEach(region -> {
                // 省
                region.getProvinceList().stream().forEach(province -> {
                    // 市
                    List<Area> areas = this.areaService.list(new QueryWrapper<Area>().
                            eq("pid",province.getId()).eq("area_type","city").select("id","area_name"));
                    province.setAreaList(areas);
                });
            });
            return Result.success(regionList);
        } catch (Exception e) {
            log.error("\n=============获取当前品牌所辖大区及省市信息:{}",e);
            return Result.error(BizStatusEnum.REGION_INFO_FAIL.getCode(),BizStatusEnum.REGION_INFO_FAIL.getMsg());
        }
    }

    @PostMapping("/getRegionList")
    @ApiOperation(value="获取当前品牌所辖大区信息", notes="获取当前品牌所辖大区信息", produces="application/json")
    public Result getRegionList() {
        log.info("=============获取当前品牌所辖大区信息=============");
        try {
            List<Region> regionList = this.regionService.list(new QueryWrapper<Region>().
                    eq("brand_id",this.getCurrentUser().getBrandId()).eq("flag",0).
                    select("id","region_name"));
            return Result.success(regionList);
        } catch (Exception e) {
            log.error("\n=============获取当前品牌所辖大区信息:{}",e);
            return Result.error(BizStatusEnum.REGION_FAIL.getCode(),BizStatusEnum.REGION_FAIL.getMsg());
        }
    }
}

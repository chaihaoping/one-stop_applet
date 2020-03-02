package org.atgpcm.oneStopApplet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.RedisKeys;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.Area;
import org.atgpcm.oneStopApplet.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 省市区三级联动控制器
 * </p>
 *
 * @author lixu
 * @since 2020/2/7 11:08
 */
@RestController
@RequestMapping("/area")
@Slf4j
@Api(tags = "省市区相关模块")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    @PostMapping("/getProvinceList")
    @ApiOperation(value="获取省份信息", notes="获取省份信息", produces="application/json")
    @Cacheable(value = RedisKeys.TIME_GROUP_10h, unless = "#result eq null ")
    public Result getProvinceList() {
        log.info("=============获取省份信息=============");
        try {
            List<Area> areaList = this.areaService.list(new QueryWrapper<Area>().eq("area_type","province").
                    select("id","area_name"));
            return Result.success(areaList);
        } catch (Exception e) {
            log.error("\n=============获取省份信息:{}",e);
            return Result.error(BizStatusEnum.PROVINCE_FAIL.getCode(),BizStatusEnum.PROVINCE_FAIL.getMsg());
        }
    }

    @PostMapping("/getCityList")
    @ApiOperation(value="获取地级市信息", notes="获取地级市信息", produces="application/json")
    @Cacheable(value = RedisKeys.TIME_GROUP_10h, unless = "#result eq null ")
    public Result getCityList() {
        log.info("=============获取地级市信息=============");
        try {
            List<Area> areaList = this.areaService.list(new QueryWrapper<Area>().eq("area_type","city").
                    select("id","area_name"));
            return Result.success(areaList);
        } catch (Exception e) {
            log.error("\n=============获取地级市信息:{}",e);
            return Result.error(BizStatusEnum.CITY_FAIL.getCode(),BizStatusEnum.CITY_FAIL.getMsg());
        }
    }
}
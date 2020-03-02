package org.atgpcm.oneStopApplet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.CarSeries;
import org.atgpcm.oneStopApplet.service.CarSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 车系表 前端控制器
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@RestController
@RequestMapping("/carSeries")
@Slf4j
@Api(tags = "车系相关模块")
public class CarSeriesController extends BaseController {

    @Autowired
    private CarSeriesService carSeriesService;

    @PostMapping("/getSeriesList")
    @ApiOperation(value="获取当前品牌下所有车系信息", notes="获取当前品牌下所有车系信息", produces="application/json")
    public Result getSeriesList() {
        log.info("=============获取当前品牌下所有车系信息=============");
        try {
            List<CarSeries> carSeriesList = this.carSeriesService.list(new QueryWrapper<CarSeries>().
                    eq("brand_id",this.getCurrentUser().getBrandId()).eq("flag",0).
                    select("id","series_name"));
            return Result.success(carSeriesList);
        } catch (Exception e) {
            log.error("\n=============获取当前品牌下所有车系信息:{}",e);
            return Result.error(BizStatusEnum.CAR_SERIES_FAIL.getCode(),BizStatusEnum.CAR_SERIES_FAIL.getMsg());
        }
    }

    @PostMapping("/getSeriesAndModelList")
    @ApiOperation(value="获取当前品牌下所有车系及车型信息", notes="获取当前品牌下所有车系及车型信息", produces="application/json")
    public Result getSeriesAndModelList() {
        log.info("=============获取当前品牌下所有车系及车型信息=============");
        try {
            List<CarSeries> carSeriesList = this.carSeriesService.selectSeriesAndModelList(this.getCurrentUser().getBrandId());
            return Result.success(carSeriesList);
        } catch (Exception e) {
            log.error("\n=============获取当前品牌下所有车系及车型信息:{}",e);
            return Result.error(BizStatusEnum.CAR_SERIES_AND_MODEL_FAIL.getCode(),BizStatusEnum.CAR_SERIES_AND_MODEL_FAIL.getMsg());
        }
    }

    @PostMapping("/getSeriesAndCompeteList")
    @ApiOperation(value="获取当前车系及竞品车系", notes="获取当前车系及竞品车系", produces="application/json")
    @ApiImplicitParam(name="seriesId",value="车系id",required=true,dataType="int")
    public Result getSeriesAndCompeteList(Integer seriesId) {
        log.info("=============获取当前车系及竞品车系=============");
        try {
            // 竞品
            List<CarSeries> carSeriesList = this.carSeriesService.selectSeriesAndCompeteList(seriesId);
            // 本品
            CarSeries carSeries = this.carSeriesService.getById(seriesId);
            carSeriesList.add(0,carSeries);
            return Result.success(carSeriesList);
        } catch (Exception e) {
            log.error("\n=============获取当前车系及竞品车系:{}",e);
            return Result.error(BizStatusEnum.SERIES_AND_COMPETE_FAIL.getCode(),BizStatusEnum.SERIES_AND_COMPETE_FAIL.getMsg());
        }
    }
}

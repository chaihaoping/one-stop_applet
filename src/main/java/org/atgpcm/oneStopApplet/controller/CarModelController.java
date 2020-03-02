package org.atgpcm.oneStopApplet.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.CarModel;
import org.atgpcm.oneStopApplet.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 车型表 前端控制器
 * </p>
 *
 * @author lixu
 * @since 2020-02-07
 */
@RestController
@RequestMapping("/carModel")
@Slf4j
@Api(tags = "车型信息相关模块")
public class CarModelController extends BaseController {

    @Autowired
    private CarModelService carmodelService;

    @PostMapping("/getModelListBySeriesId")
    @ApiOperation(value="获取当前车系下的所有车型", notes="获取当前车系下的所有车型", produces="application/json")
    @ApiImplicitParam(name="seriesId",value="车系id",required=true,dataType="int")
    public Result getModelListBySeriesId(Integer seriesId) {
        log.info("=============获取当前车系下的所有车型=============");
        try {
            List<CarModel> carModelList = this.carmodelService.list(new QueryWrapper<CarModel>()
                    .eq("series_id",seriesId).eq("flag",0).select("id","MJ","model_name"));
            carModelList.stream().forEach(carModel -> {
                carModel.setName(carModel.getMj()+" "+carModel.getModelName());
            });
            return Result.success(carModelList);
        } catch (Exception e) {
            log.error("\n=============获取当前车系下的所有车型:{}",e);
            return Result.error(BizStatusEnum.CAR_MODEL_FAIL.getCode(),BizStatusEnum.CAR_MODEL_FAIL.getMsg());
        }
    }

}

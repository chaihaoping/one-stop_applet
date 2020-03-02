package org.atgpcm.oneStopApplet.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.atgpcm.oneStopApplet.bizEnum.BizStatusEnum;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.domain.entity.Brand;
import org.atgpcm.oneStopApplet.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author chaihaoping
 * @since 2020-02-10
 */
@RestController
@RequestMapping("/brand")
@Slf4j
@Api(tags = "品牌相关模块")
public class BrandController extends BaseController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/getBrandAndCompeteList")
    @ApiOperation(value="获取本品及竞品", notes="获取本品及竞品", produces="application/json")
    public Result getBrandAndCompeteList() {
        log.info("=============获取本品及竞品=============");
        try {
            // 竞品
            List<Brand> brandList = this.brandService.selectBrandAndCompeteList(this.getCurrentUser().getBrandId());
            // 本品
            Brand brand = this.brandService.getById(this.getCurrentUser().getBrandId());
            brandList.add(0,brand);
            return Result.success(brandList);
        } catch (Exception e) {
            log.error("\n=============获取本品及竞品:{}",e);
            return Result.error(BizStatusEnum.BRAND_AND_COMPETE_FAIL.getCode(),BizStatusEnum.BRAND_AND_COMPETE_FAIL.getMsg());
        }
    }

}

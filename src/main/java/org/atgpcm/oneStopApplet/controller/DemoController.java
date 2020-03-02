package org.atgpcm.oneStopApplet.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.atgpcm.oneStopApplet.common.BaseController;
import org.atgpcm.oneStopApplet.common.Result;
import org.atgpcm.oneStopApplet.dao.DemoDao_1;
import org.atgpcm.oneStopApplet.dao.DemoDao_2;
import org.atgpcm.oneStopApplet.dao.SalesRecordDao;
import org.atgpcm.oneStopApplet.domain.entity.Area;
import org.atgpcm.oneStopApplet.domain.entity.SalesRecord;
import org.atgpcm.oneStopApplet.domain.entity.User;
import org.atgpcm.oneStopApplet.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chaihaoping
 * @title demo
 * @date 2020/2/4 15:46
 * @description demo
 */
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {

    @Autowired
    private DemoDao_1 demoDao_1;
    @Autowired
    private DemoDao_2 demoDao_2;
    @Autowired
    private SalesRecordDao salesRecordDao;
    @Autowired
    private AreaService areaService;

    @GetMapping("/test")
    public Result test() {
        List<Map<String,String>> mapList1 = this.demoDao_1.selectAll();
        List<Map<String,String>> mapList2 = this.demoDao_2.selectAll();
        List<Map<String,String>> mapList3 = this.demoDao_1.selectUser();
        return Result.success();
    }

    @GetMapping("/excel")
    public Result excel() {
        // 读取本地excel
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\ATG\\Desktop\\一站式数据\\上汽通用别克-昂科威.xlsx");
        List<Map<String,Object>> readList = reader.readAll();
        // 品牌id
        Integer brandId = 3;
        // 车系id
        Integer seriesId = 9;
        // 车系名称
        String seriesName = "昂科威";
        // 车型集合
        List<JSONObject> modelList = Lists.newArrayList();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name","2019款 20T 两驱精英型 国VI");
        jsonObject1.put("id",25);
        modelList.add(jsonObject1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name","2019款 28T 四驱豪华型 国V");
        jsonObject2.put("id",26);
        modelList.add(jsonObject2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("name","2019款 28T 四驱全能运动旗舰型 国VI");
        jsonObject3.put("id",27);
        modelList.add(jsonObject3);
        // 大区id
        Integer regionId = 3;
        // 地级市价格浮动范围
        List<String> cityRandom = Stream.of("-0.1","0","0.1").collect(Collectors.toList());
        // 省级价格浮动范围
        List<String> provinceRandom = Stream.of("-0.5","-0.4","-0.3","-0.2","-0.1","0","0.1","0.2","0.3","0.4","0.5").collect(Collectors.toList());
        List<SalesRecord> salesRecordList = Lists.newArrayList();
        // 循环插入数据
        readList.stream().forEach(map -> {
            // 省级名称
            String provinceName = map.get("省").toString().trim();
            // 省级id
            Integer provinceId = this.areaService.getOne(new QueryWrapper<Area>().eq("area_name",provinceName).
                    eq("area_type","province").select("id")).getId();
            // 地级市名称
            String cityName = map.get("市").toString().trim();
            if (cityName.contains("市")) {
                cityName = cityName.replace("市","");
            }
            // 地级市id
            Integer cityId = this.areaService.getOne(new QueryWrapper<Area>().eq("area_name",cityName).
                    eq("area_type","city").select("id")).getId();
            for (int i = 0;i < Integer.parseInt(map.get("销量").toString());i++) {
                // 随机生成购买日期，注意当月的天数
                // 生成时分秒，防止2020-01-01 00:00:00这种类似的时间存入数据库时会自动转换为2019-12-31
                String buyTime = "2019-11-"+String.format("%02d",RandomUtil.randomInt(1,31))+DateUtil.now().substring(DateUtil.now().length() - 9);
                // 随机生成车型
                JSONObject jsonObject = RandomUtil.randomEle(modelList);
                // 车型名称
                String modelName = jsonObject.getString("name");
                // 车型id
                Integer modelId = jsonObject.getInteger("id");
                BigDecimal cityPrice = this.demoDao_1.selectCityPrice(cityName,seriesName,modelName);
                // 按地级市处理
                if (cityPrice != null) {
                    // 按浮动范围随机生成价格
                    String priceRandom = RandomUtil.randomEle(cityRandom);
                    BigDecimal price = cityPrice.add(new BigDecimal(priceRandom));
                    SalesRecord salesRecord = new SalesRecord();
                    salesRecord.setBrandId(brandId);
                    salesRecord.setSeriesId(seriesId);
                    salesRecord.setModelId(modelId);
                    salesRecord.setRegionId(regionId);
                    salesRecord.setProvinceId(provinceId);
                    salesRecord.setCityId(cityId);
                    salesRecord.setPrice(price);
                    salesRecord.setBuyTime(DateUtil.parse(buyTime));
                    salesRecord.setCreateTime(new Date());
                    salesRecordList.add(salesRecord);
                    this.salesRecordDao.insert(salesRecord);
                } else { // 按省级处理
                    // 按浮动范围随机生成价格
                    BigDecimal provincePrice = this.demoDao_1.selectProvincePrice(provinceName,seriesName,modelName);
                    SalesRecord salesRecord = new SalesRecord();
                    if (provincePrice == null) {
                        salesRecord.setPrice(new BigDecimal(0));
                    } else {
                        String priceRandom = RandomUtil.randomEle(provinceRandom);
                        BigDecimal price = provincePrice.add(new BigDecimal(priceRandom));
                        salesRecord.setPrice(price);
                    }
                    salesRecord.setBrandId(brandId);
                    salesRecord.setSeriesId(seriesId);
                    salesRecord.setModelId(modelId);
                    salesRecord.setRegionId(regionId);
                    salesRecord.setProvinceId(provinceId);
                    salesRecord.setCityId(cityId);
                    salesRecord.setBuyTime(DateUtil.parse(buyTime));
                    salesRecord.setCreateTime(new Date());
                    this.salesRecordDao.insert(salesRecord);
                    salesRecordList.add(salesRecord);
                }
            }
        });
        ExcelWriter writer = ExcelUtil.getWriter("E:/昂科威_2019-11.xlsx");
        writer.write(salesRecordList);
        writer.close();
        return Result.success();
    }

    @GetMapping("/getUser")
    public User getUser() {
        return this.getCurrentUser();
    }

    @GetMapping("/update")
    public void update() {
        List<SalesRecord> list = this.salesRecordDao.selectList(new QueryWrapper<SalesRecord>()
                .eq("model_id",16).in("province_id",2,51,66).select());
        BigDecimal avg_price = new BigDecimal(19.65);
        List<String> provinceRandom = Stream.of("-0.5","-0.4","-0.3","-0.2","-0.1","0","0.1","0.2","0.3","0.4","0.5").collect(Collectors.toList());
        list.stream().forEach(salesRecord -> {
            String priceRandom = RandomUtil.randomEle(provinceRandom);
            BigDecimal price = avg_price.add(new BigDecimal(priceRandom));
            SalesRecord sr = new SalesRecord();
            sr.setId(salesRecord.getId());
            sr.setPrice(price);
            this.salesRecordDao.updateById(sr);
        });
    }



    public static void main(String[] args) {
        System.out.println(DateUtil.now().substring(DateUtil.now().length() - 9));
    }

}


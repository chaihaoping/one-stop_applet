package org.atgpcm.oneStopApplet.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@DS("master")
@Repository
public interface DemoDao_1 {

    @Select("Select brand_name FROM brand")
    List<Map<String,String>>  selectAll();

    @DS("slave")
    @Select("Select age FROM user")
    List<Map<String,String>> selectUser();

    @DS("slave")
    @Select("SELECT ROUND(AVG(luochejia),2) AS price FROM auto_car_all_price WHERE " +
            "city = #{cityName} AND seriesname = #{seriesName} AND specname = #{modelName}")
    BigDecimal selectCityPrice(@Param("cityName") String cityName,
                           @Param("seriesName") String seriesName,
                           @Param("modelName") String modelName);

    @DS("slave")
    @Select("SELECT ROUND(AVG(luochejia),2) AS price FROM auto_car_all_price WHERE " +
            "LEFT(place,2) = #{provinceName} AND seriesname = #{seriesName} AND specname = #{modelName}")
    BigDecimal selectProvincePrice(@Param("provinceName") String provinceName,
                               @Param("seriesName") String seriesName,
                               @Param("modelName") String modelName);

    @Select("SELECT id FROM area WHERE area_name = #{cityName}")
    Integer selectIdByName(@Param("cityName") String cityName);
}

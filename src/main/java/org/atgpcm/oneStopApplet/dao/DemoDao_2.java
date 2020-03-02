package org.atgpcm.oneStopApplet.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@DS("slave")
@Repository
public interface DemoDao_2 {

    @Select("Select age FROM user")
    List<Map<String,String>> selectAll();
}

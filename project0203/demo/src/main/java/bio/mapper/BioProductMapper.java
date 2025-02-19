package bio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface BioProductMapper {

    // 효능군별 개수를 조회하는 메서드
    List<Map<String, Object>> countByEfficacyGroup();


}
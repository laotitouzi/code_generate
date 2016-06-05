package ${bussPackage}.dao#if($!entityPackage).${entityPackage}#end;

import org.springframework.stereotype.Service;
import org.apache.ibatis.annotations.Param;
import ${bussPackage}.entity.${className};
import com.tshop.page.*;
import java.util.List;
import com.tshop.page.Criteria;

/**
 * author: ${author}
 * createDate: ${createDate}
 */

@Service("${lowerName}Mapper")
public interface ${className}Mapper  {

    public void add${className}(${className} ${lowerName});

    public int update${className}(${className} ${lowerName});

    public int update${className}BySelective(${className} ${lowerName});

    public int delete${className}ById(@Param("id") Object id);

    public int delete${className}ByIds(@Param("ids") Object ids);

    public int query${className}ByCount(Criteria criteria);

    public List query${className}ForList(Criteria criteria);

    public ${className} query${className}ById(@Param("id") Object id);

}

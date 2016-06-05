package ${bussPackage}.service#if($!entityPackage).${entityPackage}#end;
import ${bussPackage}.entity.${className};
import com.tshop.page.*;
import java.util.List;
/**
* author: ${author}
* createDate: ${createDate}
*/

public interface ${className}Service {

    public void add${className}(${className} ${lowerName});

    public int update${className}(${className} ${lowerName});

    public int update${className}BySelective(${className} ${lowerName});

    public int delete${className}ById(Object id);

    public int delete${className}ByIds(Object ids);

    public int query${className}ByCount(Criteria criteria);

    public List query${className}ForList(Criteria criteria);

    public ${className} query${className}ById(Object id);

    public Page query${className}ForPage(Criteria criteria) ;

}

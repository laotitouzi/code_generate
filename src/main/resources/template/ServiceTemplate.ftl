package ${bussPackage}.service#if($!entityPackage).${entityPackage}#end;
import com.base.service.BaseService;
import ${bussPackage}.entity.${className};

/**
* author: ${author}
* createDate: ${createDate}
*/

public interface ${className}Service {

    public int add${className}(${className} ${lowerName});

    public int update${className}(${className} ${lowerName});

    public int update${className}BySelective(${className} ${lowerName});

    public int delete${className}ById(Object id);

    public int delete${className}ByIds(Object ids);

    public int query${className}ByCount(Criteria criteria);

    public List query${className}ByList(Criteria criteria);

    public ${className} query${className}ById(Object id);

    public Page query${className}ForPage(Criteria criteria) ;

}

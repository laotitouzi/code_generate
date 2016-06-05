package ${bussPackage}.service.impl#if($!entityPackage).${entityPackage}#end;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${bussPackage}.dao#if($!entityPackage).${entityPackage}#end.${className}Mapper;
import ${bussPackage}.service#if($!entityPackage).${entityPackage}#end.${className}Service;
import ${bussPackage}.entity.${className};
import com.tshop.page.*;

/**
* author: ${author}
* createDate: ${createDate}
*/

@Service("${lowerName}Service")
public class  ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Mapper ${lowerName}Mapper;

    public void add${className}(${className} ${lowerName}){
        ${lowerName}Mapper.add${className}(${lowerName});
    }

    public int update${className}(${className} ${lowerName}){
        return ${lowerName}Mapper.update${className}(${lowerName});
    }

    public int update${className}BySelective(${className} ${lowerName}){
        return ${lowerName}Mapper.update${className}BySelective(${lowerName});
    }

    public int delete${className}ById(Object id){
        return  ${lowerName}Mapper.delete${className}ById(id);
    }

    public int delete${className}ByIds(Object ids){
        return ${lowerName}Mapper.delete${className}ByIds(ids);
    }

    public Page query${className}ForPage(Criteria criteria) {
        PageHelper.newPage(criteria.getCurrentPage(), criteria.getPageSize());
        UserMapper.query${className}ByList(criteria);
        return PageHelper.endPage();
    }

    public int query${className}ByCount(Criteria criteria){
        return ${lowerName}Mapper.query${className}ByCount(criteria);
    }

    public List query${className}ByList(Criteria criteria){
        return ${lowerName}Mapper.query${className}ByList(criteria);
    }

    public ${className} query${className}ById(Object id){
        return ${lowerName}Mapper.query${className}ById(id);
    }

}

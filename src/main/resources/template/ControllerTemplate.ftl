package ${bussPackage}.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StringUtils;

import com.tshop.controller.BaseController;
import com.tshop.page.Criteria;
import com.tshop.page.Page;

import ${bussPackage}.entity#if($!entityPackage).${entityPackage}#end.${className};
import ${bussPackage}.service#if($!entityPackage).${entityPackage}#end.${className}Service;

/**
* author: ${author}
* createDate: ${createDate}
*/

@Controller
@Scope("prototype")
@RequestMapping("/${lowerName}")
public class ${className}Controller extends BaseController{
    
    @Autowired(required=false)
    private ${className}Service ${lowerName}Service; 
    
    private Page<${className}> page;

    private List<${className}> list;


    /**
     *  非分页查询，根据条件显示所有记录
     */
    @RequestMapping("/show")
    public String show(HttpServletRequest request, HttpServletResponse response,Criteria criteria) throws Exception{
        //TODO
        list  =  ${lowerName}Service.query${className}ForList(criteria);
        return "/${className}/show";
    }


    /**
    *  分页查询
    */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpServletResponse response,Criteria criteria) throws Exception{
        //TODO
        page  =  ${lowerName}Service.query${className}ForPage(criteria);
        return "/${className}/list";
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public JSONObject save(@Valid ${className}  ${lowerName}, BindingResult result){
        if (${lowerName} == null) {
        return resonseError(BASIC_MESSAGE_MUST_INPUT);
        }

        if (result.hasErrors()) {
            return resonseError(result.getAllErrors());
        }

        ${lowerName}Service.add${className}(${lowerName});

        return resonseOk();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET )
    public String edit(@PathVariable String id){
        if (StringUtils.isEmpty(id)) {
            return resonseError(BASIC_MESSAGE_MUST_INPUT);
        }

        ${className} ${lowerName} = ${lowerName}Service.query${className}ById(id);

        if(${lowerName}==null){
            return  ERROR_PAGE;
        }

        return "${className}/edit";
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public JSONObject update(@Valid ${className} ${lowerName},BindingResult result){
        if (${lowerName} == null) {
            return resonseError(BASIC_MESSAGE_MUST_INPUT);
        }

        if (result.hasErrors()) {
            return resonseError(result.getAllErrors());
        }

        ${lowerName}Service.update${className}BySelective(${lowerName});

        return resonseOk();
    }

    @RequestMapping(value = "/delete/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteById(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
            return resonseError(BASIC_MUST_INPUT_DELETE_ID_OR_IDS);
        }

        int record = ${lowerName}Service.delete${className}ById(id);

        if(record > 0){
            return resonseOk();
        }

        return responseError();
    }

    @RequestMapping(value = "/deleteIds/{ids}}" ,method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deleteByIds(@PathVariable String ids){
        if(StringUtils.isEmpty(ids)){
            return resonseError(BASIC_MUST_INPUT_DELETE_ID_OR_IDS);
        }

        int record = ${lowerName}Service.delete${className}ByIds(ids);

        if(record > 0){
            return resonseOk();
        }

        return responseError();
    }

    public  List<${className}> getList(){
        return list;
    }
    
    public void setList(List<${className}> list){
        this.list = list;
    }

    public  Page<${className}> getPage(){
        return page;
    }

    public void setPage(Page<${className}> page){
        this.page = page;
    }
}

package ${bussPackage}.controller#if($!controllerEntityPackage).${controllerEntityPackage}#end;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String show(HttpServletRequest request, HttpServletResponse response,,Criteria criteria) throws Exception{
        //TODO
        list  =  ${lowerName}Service.query${className}(criteria);
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


    @RequestMapping(value = "/doSave")
    @ResponseBody
    public JSONObject doSave(@Valid ${className}  ${lowerName}, BindingResult result)){
        if (${lowerName} == null) {
        return resonseError(BADIC_MESSAGE_MUST_INPUT);
        }

        if (result.hasErrors()) {
            return resonseError(result.getAllErrors());
        }

        ${lowerName}Service.add${className}(${lowerName});

        return resonseOk();
    }

    @RequestMapping(value = "/doUpdate")
    @ResponseBody
    public JSONObject doUpdate(@Valid ${className} ${lowerName},BindingResult result)){
        if (${lowerName} == null) {
            return resonseError(BADIC_MESSAGE_MUST_INPUT);
        }

        if (result.hasErrors()) {
            return resonseError(result.getAllErrors());
        }

        ${lowerName}Service.update${className}BySelective(${lowerName});

        return resonseOk();
    }

    @RequestMapping("/doDeleteById")
    @ResponseBody
    public JSONObject doDeleteById(String id){
        if(.StringUtils.isEmpty(id)){
            return resonseError(BASIC_MUST_INPUT_DELETE_ID_OR_IDS);
        }

        int record = ${lowerName}Service.delete${className}ById(id);

        if(record > 0){
            return resonseOk();
        }

        return responseError();
    }

    @RequestMapping("/doDeleteByIds")
    @ResponseBody
    public JSONObject doDeleteByIds(String ids){
        if(.StringUtils.isEmpty(ids)){
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

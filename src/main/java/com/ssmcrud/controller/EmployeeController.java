package com.ssmcrud.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.ssmcrud.bean.Employee;
import com.ssmcrud.bean.Msg;
import com.ssmcrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
/**
 * 处理员工CRUD请求
 *
 * @author lfy
 *
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 导入jackson包
     * @return
     */
    @RequestMapping(value="/emp",method =RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid  Employee employee,BindingResult result){
        if(result.hasErrors()){
            //校验失败，在模态框中显示失败信息
            Map<String,Object> map=new HashMap<>();
             List<FieldError> errors=  result.getFieldErrors();
             for(FieldError fieldError:errors){
                 System.out.println("错误字段名："+fieldError.getField());
                 System.out.println("错误信息"+fieldError.getDefaultMessage());
                 map.put(fieldError.getField(),fieldError.getDefaultMessage());
             }
            return  Msg.fail().add("errorFields",map);
        }else{
            employeeService.saveEmp(employee);
            return  Msg.success();
        }

    }
    /**
     * 员工更新方法
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee){
        System.out.println("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return  Msg.success();
    }

    /**
     * 单个删除
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}")
    public Msg deleteEmpById(@PathVariable("ids")String ids){
        if(ids.contains("-")){
            List<Integer> del_ids=new ArrayList<>();

            String [] str_ids= ids.split("-");

            for(String string:str_ids){
                del_ids.add(Integer.parseInt(string));
            }
               employeeService.deleteBatch(del_ids);
        }else{
            Integer id= Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return  Msg.success();

    }



    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}",method =RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id")Integer id){
        Employee employee=employeeService.getEmp(id);
        return  Msg.success().add("emp",employee);
    }

    /**
     * 检查用户名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuser(String empName){
        //先判断用户名是否合法的表达式
        String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须是6-16位数字和字母的组合");
        }

        //数据库用户名重复校验
        boolean b=employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }
        else{
            return Msg.fail().add("val_msg","用户名不可用");
        }

    }

    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,Model model){
        //这不是一个分页查询
        //引入PageHelper分页插件
        //在查询之前只需要调用,插入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的这个查询就是分页查询

        List<Employee> emps= employeeService.getAll();
        //使用PageInfo查询包装查询后的结果，只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包裹有我们查询出来的数据,连续显示的页数
        PageInfo page=new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }


    //@RequestMapping("/emps")
//    public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,Model model){
//        //这不是一个分页查询
//        //引入PageHelper分页插件
//        //在查询之前只需要调用,插入页码，以及每页的大小
//        PageHelper.startPage(pn,5);
//        //startPage后面紧跟的这个查询就是分页查询
//
//        List<Employee> emps= employeeService.getAll();
//        //使用PageInfo查询包装查询后的结果，只需要将pageInfo交给页面就行了
//        //封装了详细的分页信息，包裹有我们查询出来的数据,连续显示的页数
//        PageInfo page=new PageInfo(emps,5);
//        model.addAttribute("pageInfo",page);
//
//        return "list";
//    }

}

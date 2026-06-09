package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bill;
import com.elderly.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 缴费账单相关
 */
@RestController
@RequestMapping(value = "/bill")
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * 缴费账单分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = billService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增缴费账单
     * @param bill
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Bill bill){
        return billService.save(bill);
    }


    /**
     * 更新
     * @param bill
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Bill bill){
        return billService.update(bill);
    }



    /**
     * 缴费
     * @param bill
     * @return
     */
    @PutMapping(value = "/pay")
    public JSONReturn pay(@RequestBody Bill bill){
        return billService.pay(bill.getId());
    }


    /**
     * 删除缴费账单
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = billService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }



}

package org.sadness.transaction.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.sadness.transaction.service.IConfirmLogService;
import org.sadness.transaction.entity.ConfirmLog;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-06
 */
@Controller
@RequestMapping("/confirm-log")
public class ConfirmLogController {


    @Autowired
    private IConfirmLogService iConfirmLogService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<ConfirmLog>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<ConfirmLog> aPage = iConfirmLogService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConfirmLog> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iConfirmLogService.getById(id), HttpStatus.OK);
    }



}

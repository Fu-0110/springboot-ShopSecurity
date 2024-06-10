package com.louis.shopsecurity.controller;

import com.louis.shopsecurity.controller.result.BaseResult;
import com.louis.shopsecurity.controller.result.DataResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @GetMapping
    public ResponseEntity<BaseResult> resource () {

        DataResult<String> result = new DataResult<>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("成功");
        result.setData("受保護資源");

        return ResponseEntity.ok(result);
    }
}

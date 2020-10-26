package com.example.phh.fo.controller;

import java.util.List;

import com.example.phh.fo.model.Todo;
import com.example.phh.fo.service.DemoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "PHH TEST FO API")
@RestController
@RequestMapping("/fo")
public class DemoController {
    private final DemoService demoService;

    public DemoController(final DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/ccbo/{param1}/{param2}")
    @ApiOperation(value = "Check Connect BO", notes = "성공 시, 파라미터 값을 리턴으로 받습니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "파라미터1", required = true, dataType = "string", paramType = "path", defaultValue = "param1"),
            @ApiImplicitParam(name = "param2", value = "파라미터2", required = true, dataType = "string", paramType = "path", defaultValue = "param2") })
    public ResponseEntity<String> ccbo_api(@PathVariable final String param1, @PathVariable final String param2) {
        final String result = demoService.ccbo_api(param1, param2);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "TODO 전체 조회")
    @GetMapping("/findAll")
    public List<Todo> findAll() {
        final List<Todo> result = demoService.findAll();
        return result;
    }

    @ApiOperation(value = "TODO 조회")
    @GetMapping("/find/{id}")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "string", paramType = "path")
    public ResponseEntity<Todo> find(@PathVariable final String id) {
        final Todo result = demoService.findById(id);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "TODO 등록")
    @PostMapping("/add")
    public Todo add(@RequestBody final Todo todo) {
        final Todo result = demoService.add(todo);
        return result;
    }

    @ApiOperation(value = "TODO 수정")
    @PutMapping("/modify")
    public Todo modify(@RequestBody final Todo todo) {
        final Todo result = demoService.modify(todo);
        return result;
    }

    @ApiOperation(value = "TODO 삭제")
    @DeleteMapping("remove/{id}")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "string", paramType = "path")
    public int remove(@PathVariable final String id) throws Exception {
        final int result = demoService.remove(id);
        return result;
    }

    @GetMapping("/error/{module}/{code}")
    @ApiOperation(value = "Error API", notes = "Error를 발생시키는 API입니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "Select fo or bo", required = true, dataType = "string", paramType = "path", defaultValue = "fo"),
            @ApiImplicitParam(name = "code", value = "Error Code", required = true, dataType = "string", paramType = "path", defaultValue = "400") })
    public ResponseEntity<Object> error_api(@PathVariable final String module, @PathVariable final String code)
            throws InterruptedException {
        return demoService.error_api(module, code);
    }
} 
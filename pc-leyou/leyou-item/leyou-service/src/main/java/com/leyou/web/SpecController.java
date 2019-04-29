package com.leyou.web;

import com.leyou.entity.SpecGroup;
import com.leyou.entity.SpecParam;
import com.leyou.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroup(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(specService.querySpecGroup(cid));
    }
    @RequestMapping("group")
    public ResponseEntity<Void> saveSpecGroup(SpecGroup specGroup){
        specService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping("group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id") Long id){
        specService.deleteSpecGroup(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParam(@RequestParam("gid") Long gid){
        return ResponseEntity.ok(specService.querySpecParam(gid));
    }

    @RequestMapping("param")
    public ResponseEntity<Void> saveSpecParam(SpecParam specParam){
        specService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @RequestMapping("param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id") Long id){
        specService.deleteSpecParam(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
 }

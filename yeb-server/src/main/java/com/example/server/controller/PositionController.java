package com.example.server.controller;


import com.example.common.pojo.Position;
import com.example.server.service.IPositionService;
import com.example.server.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @GetMapping("/")
    public List<Position> listAll() {

        return positionService.list();
    }

    @PostMapping("/")
    public RespBean savePosition(@RequestBody Position position) {

        position.setCreateDate(LocalDateTime.now());
        position.setEnabled(true);

        positionService.save(position);

        return RespBean.success();
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {

        boolean b = positionService.updateById(position);

        return RespBean.success();
    }

    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {

        boolean b = positionService.removeById(id);
        return RespBean.success();
    }

    @DeleteMapping("/")
    public RespBean deleteByIds(Integer[] ids) {

        /**
         * @Tr
         * for() {
         *
         *     mapper.deleteById()
         * }
         */
        boolean b = positionService.removeByIds(Arrays.asList(ids));

        return RespBean.success();
    }
}

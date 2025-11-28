package com.ruoyi.web.controller.lovetime;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
// Removed PreAuthorize import
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.Anniversary;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lovetime.service.IAnniversaryService;

/**
 * 纪念日Controller
 * 
 * @author ruoyi
 * @date 2025-11-28
 */
@RestController
@RequestMapping("/api/anniversary")
public class AnniversaryController extends BaseController {
    
    @Autowired
    private IAnniversaryService anniversaryService;
    
    /**
     * 获取用户的纪念日列表
     */
    @GetMapping("/list")
    public AjaxResult list() {
        try {
            Long userId = getUserId();
            List<Anniversary> anniversaryList = anniversaryService.selectAnniversaryListByUserId(userId);
            return success("获取纪念日列表成功").put("anniversaryList", anniversaryList);
        } catch (Exception e) {
            return error("获取纪念日列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建新的纪念日
     */
    @Log(title = "纪念日", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public AjaxResult create(@RequestBody Anniversary anniversary) {
        try {
            Long userId = getUserId();
            anniversary.setUserId(userId);
            anniversary.setCreateTime(new Date());
            anniversary.setUpdateTime(new Date());
            
            int result = anniversaryService.insertAnniversary(anniversary);
            if (result > 0) {
                return success("添加纪念日成功").put("data", anniversary);
            } else {
                return error("添加纪念日失败");
            }
        } catch (Exception e) {
            return error("添加纪念日失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新指定ID的纪念日
     */
    @Log(title = "纪念日", businessType = BusinessType.UPDATE)
    @PutMapping("/update/{id}")
    public AjaxResult update(@PathVariable Long id, @RequestBody Anniversary anniversary) {
        try {
            Anniversary existingAnniversary = anniversaryService.selectAnniversaryById(id);
            if (existingAnniversary == null) {
                return error("纪念日不存在");
            }
            
            // 确保用户只能更新自己的纪念日
            if (!existingAnniversary.getUserId().equals(getUserId())) {
                return error("无权限更新该纪念日");
            }
            
            // 更新字段
            existingAnniversary.setTitle(anniversary.getTitle());
            existingAnniversary.setDate(anniversary.getDate());
            existingAnniversary.setIcon(anniversary.getIcon());
            existingAnniversary.setColor(anniversary.getColor());
            existingAnniversary.setRemind(anniversary.getRemind());
            existingAnniversary.setUpdateTime(new Date());
            
            int result = anniversaryService.updateAnniversary(existingAnniversary);
            if (result > 0) {
                return success("更新纪念日成功").put("data", existingAnniversary);
            } else {
                return error("更新纪念日失败");
            }
        } catch (Exception e) {
            return error("更新纪念日失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除指定ID的纪念日
     */
    @Log(title = "纪念日", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        try {
            Anniversary existingAnniversary = anniversaryService.selectAnniversaryById(id);
            if (existingAnniversary == null) {
                return error("纪念日不存在");
            }
            
            // 确保用户只能删除自己的纪念日
            if (!existingAnniversary.getUserId().equals(getUserId())) {
                return error("无权限删除该纪念日");
            }
            
            int result = anniversaryService.deleteAnniversaryById(id);
            if (result > 0) {
                return success("删除纪念日成功");
            } else {
                return error("删除纪念日失败");
            }
        } catch (Exception e) {
            return error("删除纪念日失败: " + e.getMessage());
        }
    }
    
    /**
     * 切换指定ID纪念日的提醒状态
     */
    @Log(title = "纪念日", businessType = BusinessType.UPDATE)
    @PutMapping("/remind/{id}")
    public AjaxResult toggleRemind(@PathVariable Long id, @RequestBody Anniversary anniversary) {
        try {
            Anniversary existingAnniversary = anniversaryService.selectAnniversaryById(id);
            if (existingAnniversary == null) {
                return error("纪念日不存在");
            }
            
            // 确保用户只能更新自己的纪念日
            if (!existingAnniversary.getUserId().equals(getUserId())) {
                return error("无权限更新该纪念日");
            }
            
            // 更新提醒状态
            existingAnniversary.setRemind(anniversary.getRemind());
            existingAnniversary.setUpdateTime(new Date());
            
            int result = anniversaryService.updateAnniversary(existingAnniversary);
            if (result > 0) {
                return success("更新提醒状态成功");
            } else {
                return error("更新提醒状态失败");
            }
        } catch (Exception e) {
            return error("更新提醒状态失败: " + e.getMessage());
        }
    }
}
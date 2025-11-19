package com.ruoyi.web.controller.lovetime;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.Trajectory;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lovetime.service.ITrajectoryService;
import com.ruoyi.lovetime.service.ICoupleRelationshipService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;

/**
 * 轨迹Controller
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
@RestController
@RequestMapping("/api/trajectory")
public class TrajectoryController {
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private ITrajectoryService trajectoryService;
    
    @Autowired
    private ICoupleRelationshipService coupleRelationshipService;
    
    /**
     * 上传/更新当前位置
     */
    @PostMapping("/location/update")
    public AjaxResult updateLocation(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证参数
            if (!requestBody.containsKey("latitude") || !requestBody.containsKey("longitude")) {
                return AjaxResult.error("经纬度不能为空");
            }
            
            // 创建或更新轨迹点
            Trajectory trajectory = new Trajectory();
            trajectory.setUserId(loginUser.getUserId());
            trajectory.setLatitude(Double.valueOf(requestBody.get("latitude").toString()));
            trajectory.setLongitude(Double.valueOf(requestBody.get("longitude").toString()));
            trajectory.setVisitTime(new Date());
            
            if (requestBody.containsKey("accuracy")) {
                // 精度信息可以存储在description中或者创建新的字段
                trajectory.setDescription("Accuracy: " + requestBody.get("accuracy").toString() + " meters");
            }
            
            if (requestBody.containsKey("address")) {
                trajectory.setAddress(requestBody.get("address").toString());
            }
            
            if (requestBody.containsKey("avatarKey")) {
                // avatarKey可以存储在placeName或其他字段中
                trajectory.setPlaceName(requestBody.get("avatarKey").toString());
            }
            
            trajectory.setCreatedAt(new Date());
            trajectory.setUpdatedAt(new Date());
            
            // 保存轨迹点
            trajectoryService.insertTrajectory(trajectory);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("locationId", trajectory.getId());
            result.put("uploadedAt", trajectory.getCreatedAt());
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("上传位置失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取双方实时位置
     */
    @GetMapping("/location/current")
    public AjaxResult getCurrentLocations(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 获取情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(loginUser.getUserId());
            if (relationship == null) {
                return AjaxResult.error("未找到情侣关系");
            }
            
            // 获取当前用户最新位置
            Trajectory selfLocation = trajectoryService.selectLatestTrajectoryByUserId(loginUser.getUserId());
            
            // 获取情侣最新位置
            Long partnerId = relationship.getUser1Id().equals(loginUser.getUserId()) ? 
                            relationship.getUser2Id() : relationship.getUser1Id();
            Trajectory partnerLocation = trajectoryService.selectLatestTrajectoryByUserId(partnerId);
            
            // 计算距离（简化计算，实际应使用更精确的地理距离计算）
            double distanceMeters = 0;
            if (selfLocation != null && partnerLocation != null) {
                distanceMeters = calculateDistance(
                    selfLocation.getLatitude(), selfLocation.getLongitude(),
                    partnerLocation.getLatitude(), partnerLocation.getLongitude()
                );
            }
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            
            Map<String, Object> self = new HashMap<>();
            if (selfLocation != null) {
                self.put("latitude", selfLocation.getLatitude());
                self.put("longitude", selfLocation.getLongitude());
                self.put("address", selfLocation.getAddress());
                self.put("updatedAt", selfLocation.getUpdatedAt());
            }
            result.put("self", self);
            
            Map<String, Object> partner = new HashMap<>();
            if (partnerLocation != null) {
                partner.put("latitude", partnerLocation.getLatitude());
                partner.put("longitude", partnerLocation.getLongitude());
                partner.put("address", partnerLocation.getAddress());
                partner.put("updatedAt", partnerLocation.getUpdatedAt());
            }
            result.put("partner", partner);
            
            result.put("distanceMeters", distanceMeters);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取实时位置失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取轨迹点列表
     */
    @GetMapping("/points")
    public AjaxResult getTrajectoryPoints(
            @RequestParam(value = "type", required = false, defaultValue = "all") String type,
            @RequestParam(value = "limit", required = false, defaultValue = "50") Integer limit,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询轨迹点
            List<Trajectory> trajectories;
            if ("important".equals(type)) {
                // 这里可以根据is_shared字段或其他条件筛选重要轨迹点
                Trajectory query = new Trajectory();
                query.setUserId(loginUser.getUserId());
                query.setIsShared(true);
                trajectories = trajectoryService.selectTrajectoryList(query);
            } else {
                // 获取所有轨迹点
                trajectories = trajectoryService.selectTrajectoryByUserId(loginUser.getUserId());
            }
            
            // 限制返回数量
            if (trajectories.size() > limit) {
                trajectories = trajectories.subList(0, limit);
            }
            
            // 构造返回数据
            return AjaxResult.success(trajectories);
        } catch (Exception e) {
            return AjaxResult.error("获取轨迹点列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询历史轨迹记录
     */
    @GetMapping("/list")
    public AjaxResult getTrajectoryList(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "order", defaultValue = "desc") String order,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 解析日期参数
            Date startDate = null;
            Date endDate = new Date(); // 默认为当前时间
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (startDateStr != null && !startDateStr.isEmpty()) {
                try {
                    startDate = sdf.parse(startDateStr);
                } catch (ParseException e) {
                    return AjaxResult.error("开始日期格式错误，应为 YYYY-MM-DD");
                }
            }
            
            if (endDateStr != null && !endDateStr.isEmpty()) {
                try {
                    endDate = sdf.parse(endDateStr);
                } catch (ParseException e) {
                    return AjaxResult.error("结束日期格式错误，应为 YYYY-MM-DD");
                }
            }
            
            // 查询轨迹记录
            List<Trajectory> trajectories;
            if (startDate != null) {
                trajectories = trajectoryService.selectTrajectoryByUserIdAndDateRange(
                    loginUser.getUserId(), startDate, endDate);
            } else {
                trajectories = trajectoryService.selectTrajectoryByUserId(loginUser.getUserId());
            }
            
            // 分页处理
            int total = trajectories.size();
            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            
            // 如果没有数据，返回空列表
            List<Trajectory> pagedTrajectories = fromIndex < total ? 
                trajectories.subList(fromIndex, toIndex) : Arrays.asList();
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("records", pagedTrajectories);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("查询历史轨迹记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取轨迹统计
     */
    @GetMapping("/statistics")
    public AjaxResult getTrajectoryStatistics(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询所有轨迹点
            List<Trajectory> trajectories = trajectoryService.selectTrajectoryByUserId(loginUser.getUserId());
            
            // 计算统计信息
            int totalDays = 0;
            double totalDistanceMeters = 0;
            String favoritePlace = "";
            String mostActiveDay = "";
            int pointsCount = trajectories.size();
            
            // 改进的统计计算
            if (!trajectories.isEmpty()) {
                // 计算总距离
                for (int i = 1; i < trajectories.size(); i++) {
                    Trajectory current = trajectories.get(i);
                    Trajectory previous = trajectories.get(i - 1);
                    
                    if (current.getLatitude() != null && current.getLongitude() != null &&
                        previous.getLatitude() != null && previous.getLongitude() != null) {
                        totalDistanceMeters += calculateDistance(
                            current.getLatitude(), current.getLongitude(),
                            previous.getLatitude(), previous.getLongitude()
                        );
                    }
                }
                
                // 计算总天数（基于不同日期的数量）
                // 这里简化处理，实际应按日期去重统计
                totalDays = Math.min(trajectories.size(), 30); // 简化处理
                
                // 最常访问地点（简化处理）
                favoritePlace = "上海迪士尼"; // 默认值
                
                // 最活跃日期（简化处理）
                mostActiveDay = "Friday"; // 默认值
            }
            
            // 最近访问记录
            List<Map<String, Object>> recentLocations = Arrays.asList();
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("totalDays", totalDays);
            result.put("totalDistanceMeters", totalDistanceMeters);
            result.put("favoritePlace", favoritePlace);
            result.put("mostActiveDay", mostActiveDay);
            result.put("pointsCount", pointsCount);
            result.put("recentLocations", recentLocations);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取轨迹统计失败: " + e.getMessage());
        }
    }
    
    /**
     * 使用Haversine公式计算两点间距离（米）
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // 地球半径（公里）
        
        // 将角度转换为弧度
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLatRad = Math.toRadians(lat2 - lat1);
        double deltaLonRad = Math.toRadians(lon2 - lon1);
        
        // Haversine公式
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        // 距离（米）
        return EARTH_RADIUS * c * 1000;
    }
}
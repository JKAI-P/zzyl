package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.CheckIn;
import com.zzyl.nursing.vo.CheckInDetailVo;

/**
 * 入住Mapper接口
 *
 * @author liuyp
 * @date 2026-05-13
 */
@Mapper
public interface CheckInMapper extends BaseMapper<CheckIn> {
    /**
     * 查询入住
     *
     * @param id 入住主键
     * @return 入住
     */
    CheckIn selectCheckInById(Long id);

    /**
     * 查询入住列表（含楼层、房间、合同状态）
     *
     * @param checkIn 入住
     * @return 入住集合
     */
    List<CheckInDetailVo> selectCheckInList(CheckIn checkIn);

    /**
     * 查询入住详情（单次JOIN查询）
     *
     * @param id 入住ID
     * @return 入住详情
     */
    CheckInDetailVo selectCheckInDetail(Long id);

    /**
     * 新增入住
     * 
     * @param checkIn 入住
     * @return 结果
     */
    int insertCheckIn(CheckIn checkIn);

    /**
     * 修改入住
     * 
     * @param checkIn 入住
     * @return 结果
     */
    int updateCheckIn(CheckIn checkIn);

    /**
     * 删除入住
     * 
     * @param id 入住主键
     * @return 结果
     */
    int deleteCheckInById(Long id);

    /**
     * 批量删除入住
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCheckInByIds(Long[] ids);
}

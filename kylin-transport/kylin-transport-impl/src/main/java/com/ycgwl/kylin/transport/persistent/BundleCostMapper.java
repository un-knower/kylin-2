/**
 * kylin-transport-impl
 * com.ycgwl.kylin.transport.persistent
 */
package com.ycgwl.kylin.transport.persistent;

import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.transport.entity.BundleCostDetail;
import com.ycgwl.kylin.transport.entity.BundleCostTopic;
import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.List;

/**
 * 成本持久层
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-27 09:18:24
 */
public interface BundleCostMapper {

	/**
	 * 更新成本信息
	 * @param costTopic
	 */
	public void updateCostTopicByID(BundleCostTopic costTopic);
	/**
	 * 更新成本明细
	 * @param costDetail
	 */
	public void updateCostDetailByID(BundleCostDetail costDetail);
	/**
	 * 插入成本基础信息
	 * @param costTopic
	 */
	public void insertCostTopic(BundleCostTopic costTopic);
	/**
	 * 插入成本明细
	 * @param costDetail
	 */
	public void insertCostDetail(BundleCostDetail costDetail);
	/**
	 * 批量插入成本明细
	 * @param costDetails
	 */
	public void batchInsertCostDetail(List<BundleCostDetail> costDetails);

	/**
	 * 根据成本编号查询成本
	 * @param insNo
	 */
	public BundleCostTopic getBundleCostTopic(String insNo);
	/**
	 * 查询成本
	 * @param costTopic
	 */
	public Collection<BundleCostTopic> listBundleCostTopic(BundleCostTopic costTopic);
	/**
	 * 分页查询成本
	 * @param costTopic
	 * @param bounds
	 * @return
	 */
	public Page<BundleCostTopic> listBundleCostTopic(BundleCostTopic costTopic, RowBounds bounds);
	/**
	 * 根据ID查询成本明细
	 * @param id
	 */
	public BundleCostDetail getBundleCostDetail(Integer id);
	
	/**
	 * 根据成本编号查询成本明细信息
	 * @param insNo
	 */
	public Collection<BundleCostDetail> listBundleCostDetail(String insNo);
	/**
	 * 分页查询成本明细
	 * @param insNo
	 * @param bounds
	 * @return
	 */
	public Page<BundleCostDetail> listBundleCostDetail(String insNo, RowBounds bounds);
}

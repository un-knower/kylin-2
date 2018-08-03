/**
 * kylin-transport-api
 * com.ycgwl.kylin.transport.service.api
 */
package com.ycgwl.kylin.transport.service.api;

import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleCostDetail;
import com.ycgwl.kylin.transport.entity.BundleCostTopic;

import java.util.Collection;
import java.util.List;

/**
 * 运输成本
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-26 17:09:16
 */
public interface BundleCostService {

	/**
	 * 分页查询运输成本
	 * @param costTopic 查询条件
	 * @param pageNum 当前页
	 * @param pageSize 每页数量
	 * @return
	 * @throws ParameterException  参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public PageInfo<BundleCostTopic> searchBundleCostTopic(BundleCostTopic costTopic, int pageNum, int pageSize) throws ParameterException, BusinessException;
	
	/**
	 * 查询运输成本
	 * @param costTopic 查询条件
	 * @return
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public Collection<BundleCostTopic> searchBundleCostTopic(BundleCostTopic costTopic) throws ParameterException, BusinessException;
	
	/**
	 * 根据指定的运输成本序号查询运输成本信息
	 * @param insNo 运输成本序号
	 * @return
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public BundleCostTopic getBundleCostTopicByInsNo(String insNo) throws ParameterException, BusinessException;
	
	/**
	 *  修改运输成本信息
	 * @param costTopic 运输成本信息  序号不能为空
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public void modifyBundleCostTopic(BundleCostTopic costTopic) throws ParameterException, BusinessException;
	
	/**
	 * 新增运输成本信息
	 * @param costTopic 运输成本信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public void saveBundleCostTopic(BundleCostTopic costTopic) throws ParameterException, BusinessException;
	
	/**
	 * 批量新增运输成本信息
	 * @param costDetails 运输成本信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public void savebatchInsertCostDetail(List<BundleCostDetail> costDetails) throws ParameterException, BusinessException;
	
	/**
	 * 根据id修改成本明细信息
	 * @param costDetail 成本明细信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public void modifyCostDetailByID(BundleCostDetail costDetail) throws ParameterException, BusinessException;
	
	/**
	 * 新增运输成本明细信息
	 * @param costDetail 运输成本明细信息
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public void saveBundleCostDetail(BundleCostDetail costDetail) throws ParameterException, BusinessException;
	
	/**
	 * 根据成本编号查询成本明细信息
	 * @param insNo 成本编号
	 * @return
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public Collection<BundleCostDetail> getBundleCostDetailByInsNo(String insNo) throws ParameterException, BusinessException;
	
	/**
	 * 分页查询成本明细
	 * @param insNo 成本编号
	 * @param pageNum 当前页
	 * @param pageSize 每页数量
	 * @return
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public PageInfo<BundleCostDetail> listBundleCostDetail(String insNo, int pageNum, int pageSize) throws ParameterException, BusinessException;
	
	/**
	 * 根据ID查询成本明细
	 * @param id ID
	 * @return
	 * @throws ParameterException 参数有误时
	 * @throws BusinessException 业务逻辑处理有误时
	 */
	public BundleCostDetail getBundleCostDetailById(Integer id) throws ParameterException, BusinessException;
}

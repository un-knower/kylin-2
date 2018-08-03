/**
 * kylin-transport-impl
 * com.ycgwl.kylin.transport.service.impl
 */
package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.BundleCostDetail;
import com.ycgwl.kylin.transport.entity.BundleCostTopic;
import com.ycgwl.kylin.transport.persistent.BundleCostMapper;
import com.ycgwl.kylin.transport.service.api.BundleCostService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-27 09:12:32
 */
@Service("kylin.transport.dubbo.local.bundleCostService")
public class BundleCostServiceImpl implements BundleCostService {

	@Resource
	private BundleCostMapper bundleCostMapper;
	
	@Override
	public PageInfo<BundleCostTopic> searchBundleCostTopic(BundleCostTopic costTopic, int pageNum, int pageSize)
			throws ParameterException, BusinessException {
		return new PageInfo<BundleCostTopic>(bundleCostMapper.listBundleCostTopic(costTopic, new RowBounds(pageNum, pageSize)));
	}

	@Override
	public Collection<BundleCostTopic> searchBundleCostTopic(BundleCostTopic costTopic)
			throws ParameterException, BusinessException {
		return bundleCostMapper.listBundleCostTopic(costTopic);
	}

	@Override
	public BundleCostTopic getBundleCostTopicByInsNo(String insNo) throws ParameterException, BusinessException {
		if(StringUtils.isBlank(insNo)){
			throw new ParameterException("insNo", insNo, "成本编号不能为空");
		}
		return bundleCostMapper.getBundleCostTopic(insNo);
	}

	@Override
	public void modifyBundleCostTopic(BundleCostTopic costTopic) throws ParameterException, BusinessException {
		if(costTopic == null){
			throw new ParameterException("costTopic", costTopic, "成本信息不能为空");
		}
		if(StringUtils.isBlank(costTopic.getInsNo())){
			throw new ParameterException("costTopic.insNo", costTopic.getInsNo(), "更新成本信息时成本编号不能为空");
		}
		BundleCostTopic existCostTopic = bundleCostMapper.getBundleCostTopic(costTopic.getInsNo());
		if(existCostTopic == null){
			throw new ParameterException("costTopic.insNo", costTopic.getInsNo(), "指定编号的成本信息不存在");
		}
		
		bundleCostMapper.updateCostTopicByID(costTopic);
	}

	@Override
	public void saveBundleCostTopic(BundleCostTopic costTopic) throws ParameterException, BusinessException {
		
		if(costTopic == null){
			throw new ParameterException("costTopic", costTopic, "成本信息不能为空");
		}
		
		bundleCostMapper.insertCostTopic(costTopic);
	}

	@Override
	public void savebatchInsertCostDetail(List<BundleCostDetail> costDetails) throws ParameterException, BusinessException {
		
		if(costDetails == null){
			throw new ParameterException("costDetails", costDetails, "成本明细信息不能为空");
		}
		
		bundleCostMapper.batchInsertCostDetail(costDetails);
	}

	@Override
	public void modifyCostDetailByID(BundleCostDetail costDetail) throws ParameterException, BusinessException {
		
		if(costDetail == null){
			throw new ParameterException("costDetail", costDetail, "成本明细信息不能为空");
		}
		BundleCostDetail bundleCostDetail = bundleCostMapper.getBundleCostDetail(costDetail.getId());
		if(bundleCostDetail == null){
			throw new ParameterException("bundleCostDetail.id", costDetail.getId(), "指定编号的成本明细信息不存在");
		}
		bundleCostMapper.updateCostDetailByID(costDetail);
	}

	@Override
	public void saveBundleCostDetail(BundleCostDetail costDetail) throws ParameterException, BusinessException {
	
		if(costDetail == null){
			throw new ParameterException("costDetail", costDetail, "成本明细信息不能为空");
		}
		
		bundleCostMapper.insertCostDetail(costDetail);
	}

	@Override
	public Collection<BundleCostDetail> getBundleCostDetailByInsNo(String insNo)
			throws ParameterException, BusinessException {
		if(StringUtils.isBlank(insNo)){
			throw new ParameterException("insNo", insNo, "查询成本信息时成本编号不能为空");
		}
		return bundleCostMapper.listBundleCostDetail(insNo);
	}

	@Override
	public PageInfo<BundleCostDetail> listBundleCostDetail(String insNo, int pageNum, int pageSize)
			throws ParameterException, BusinessException {
		if(StringUtils.isBlank(insNo)){
			throw new ParameterException("insNo", insNo, "查询成本信息时成本编号不能为空");
		}
		return new PageInfo<BundleCostDetail>(bundleCostMapper.listBundleCostDetail(insNo, new RowBounds(pageNum, pageSize)));
	}

	@Override
	public BundleCostDetail getBundleCostDetailById(Integer id) throws ParameterException, BusinessException {
		
		return bundleCostMapper.getBundleCostDetail(id);
	}

}

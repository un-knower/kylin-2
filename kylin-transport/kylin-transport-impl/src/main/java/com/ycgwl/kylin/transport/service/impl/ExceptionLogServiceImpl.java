package com.ycgwl.kylin.transport.service.impl;

import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.ExceptionLog;
import com.ycgwl.kylin.transport.entity.ExceptionLogResult;
import com.ycgwl.kylin.transport.entity.ExceptionLogSearch;
import com.ycgwl.kylin.transport.entity.UndoSignLog;
import com.ycgwl.kylin.transport.persistent.ExceptionLogMapper;
import com.ycgwl.kylin.transport.service.api.IExceptionLogService;
import com.ycgwl.kylin.transport.service.api.ITransportSignRecordService;
import com.ycgwl.kylin.util.DateUtils;
import com.ycgwl.kylin.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *  异常日志服务实现
 * <p>
 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月4日
 */
@Service("kylin.transport.dubbo.local.exceptionLogService")
public class ExceptionLogServiceImpl implements IExceptionLogService{

	@Autowired
	private ExceptionLogMapper exceptionLogMapper;
	/** 调用一下签收的mapper */
	@Resource
	private ITransportSignRecordService signRecordService;
	
	@Override
	public void addExceptionLog(ExceptionLog exceptionLog) throws ParameterException, BusinessException {
		if(exceptionLog == null){
			throw new ParameterException("exceptionLog", exceptionLog, "异常日志信息不能为空");
		}
		exceptionLogMapper.insertExceptionLog(exceptionLog);	
	}

	@Override
	public PageInfo<ExceptionLogResult> pageTransportOrder(ExceptionLogSearch exceptionLogSearch, int pageNum, int pageSize)
			throws ParameterException, BusinessException {
		exceptionLogSearch.setPageNums(pageNum);
		exceptionLogSearch.setPageSizes(pageSize);

		//结束时间补到23:59:59,开始时间为零点
		exceptionLogSearch.setOperatingTimeBegin(exceptionLogSearch.getOperatingTimeBegin() + " 00:00:00");
		exceptionLogSearch.setOperatingTimeEnd(exceptionLogSearch.getOperatingTimeEnd() + " 23:59:59");
	
		
		//查询分页总条数
		Long total =  exceptionLogMapper.queryExceptionLogPagesCount(exceptionLogSearch);

		Collection<ExceptionLog> result = new ArrayList<ExceptionLog>();
		if(total != 0){
			//查询分页返回数据
			result = exceptionLogMapper.queryExceptionLogPages(exceptionLogSearch);
		}

		Collection<ExceptionLogResult> exceptionLogResultList = new ArrayList<ExceptionLogResult>();
		
		for (ExceptionLog exceptionLog : result) {
			ExceptionLogResult exceptionLogResult = new ExceptionLogResult();
			exceptionLogResult.setId(exceptionLog.getId());
			exceptionLogResult.setOperatorName(exceptionLog.getOperatorName());
			exceptionLogResult.setOperatorAccount(exceptionLog.getOperatorAccount());
			exceptionLogResult.setOperatorCompany(exceptionLog.getOperatorCompany());
			exceptionLogResult.setIpAddress(exceptionLog.getIpAddress());
			exceptionLogResult.setYdbhid(exceptionLog.getYdbhid());
			exceptionLogResult.setCwpzhbh(exceptionLog.getCwpzhbh());
			exceptionLogResult.setOperatingMenu(exceptionLog.getOperatingMenu());
			exceptionLogResult.setOperatingContent(exceptionLog.getOperatingContent());
			exceptionLogResult.setOperatingTime(DateUtils.getCurrentTimeStr(exceptionLog.getOperatingTime()));
			exceptionLogResult.setCreateName(exceptionLog.getCreateName());
			exceptionLogResult.setCreateTime(DateUtils.getCurrentTimeStr(exceptionLog.getCreateTime()));
			exceptionLogResult.setUpdateName(exceptionLog.getUpdateName());
			exceptionLogResult.setUpdateTime(DateUtils.getCurrentTimeStr(exceptionLog.getUpdateTime()));
			exceptionLogResultList.add(exceptionLogResult);
		}
		
		//设置分页返回信息
		int startRow = 0;
		int endRow = 0;
		int pages = 0;
		if(pageNum > 0 && pageSize >0){
			startRow = (pageNum - 1) * pageSize;
			pages = (int) Math.ceil((double)total / pageSize);
		}
		endRow = startRow + pageSize;	

		Page<ExceptionLogResult> page = new Page<ExceptionLogResult>(pageNum,pageSize,startRow,endRow,total,pages,exceptionLogResultList);
		return new PageInfo<ExceptionLogResult>(page);
	}
	
	/**
	 * 	ydbhid: 运单编号
	 * 	username:登陆用户
	 * 	account:登陆账号
	 * 	company:登陆公司
	 * 	logtype:异常类型
	 * 	menu:	异常所属
	 * 	content:异常信息
	 * @see com.ycgwl.kylin.transport.service.api.IExceptionLogService#CancelLogInsert(com.ycgwl.kylin.json.util.RequestJsonEntity)
	 * <p>
	 * @developer Create by <a href="mailto:108252@ycgwl.com">wyj</a> at 2018-01-26 09:18:11
	 * @param entity
	 * @throws Exception
	 */
	@Transactional
	@Override
	public void CancelLogInsert(RequestJsonEntity entity) throws Exception {
		String ydbhid = entity.getString("ydbhid");
		String grid = entity.getString("username");
		String account = entity.getString("account");
		String company = entity.getString("company");
		UndoSignLog undoSignLog = new UndoSignLog();
		undoSignLog.setYdbhid(ydbhid);
		undoSignLog.setOpeXm(grid);
		undoSignLog.setOpeGrid(account);
		undoSignLog.setOpeType(entity.getString("logtype"));
		Date now = new Date();
		undoSignLog.setOpeDate(now);
		undoSignLog.setRecgenDate(now);
		signRecordService.addUndoSignLog(undoSignLog);
		
		//新增异常日志信息
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setOperatorName(grid);
		exceptionLog.setOperatorAccount(account);
		exceptionLog.setOperatorCompany(company);
		exceptionLog.setIpAddress(IPUtil.getLocalIP());
		exceptionLog.setYdbhid(ydbhid);
		exceptionLog.setOperatingMenu(entity.getString("menu"));
		exceptionLog.setOperatingContent(entity.getString("content"));
		exceptionLog.setOperatingTime(now);
		exceptionLog.setCreateName(grid);
		exceptionLog.setCreateTime(now);
		exceptionLog.setUpdateName(grid);
		exceptionLog.setUpdateTime(now);
		exceptionLogMapper.insertExceptionLog(exceptionLog);	
	}
	
	
}

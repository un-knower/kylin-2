/**
 * kylin-transport-impl
 * com.ycgwl.kylin.transport.service.impl
 */
package com.ycgwl.kylin.transport.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycgwl.kylin.AuthorticationConstant;
import com.ycgwl.kylin.CommonConstants;
import com.ycgwl.kylin.entity.JsonResult;
import com.ycgwl.kylin.entity.Page;
import com.ycgwl.kylin.entity.PageInfo;
import com.ycgwl.kylin.entity.RequestJsonEntity;
import com.ycgwl.kylin.exception.BusinessException;
import com.ycgwl.kylin.exception.ParameterException;
import com.ycgwl.kylin.transport.entity.*;
import com.ycgwl.kylin.transport.entity.adjunct.Employee;
import com.ycgwl.kylin.transport.persistent.*;
import com.ycgwl.kylin.transport.service.api.*;
import com.ycgwl.kylin.transport.util.MobileRegex;
import com.ycgwl.kylin.util.Assert;
import com.ycgwl.kylin.util.CommonDateUtil;
import com.ycgwl.kylin.util.IPUtil;
import com.ycgwl.kylin.util.json.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:110686@ycgwl.com">ding xuefeng</a>
 * @createDate 2017-04-27 09:12:07
 */
@Service("kylin.transport.dubbo.local.bundleReceiptService")
public class BundleReceiptServiceImpl implements BundleReceiptService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public BundleReceiptMapper bundleReceiptMapper;
    @Resource
    public TransportOrderMapper transportOrderMapper;

    @Resource
    public ITransportOrderService transportOrderService;

    @Resource
    private ArriveStationMapper arriveStationMapper;

    @Resource
    public TransportOrderDetailService detailService;
    @Resource
    public AdjunctSomethingService adjunctSomethingService;

    @Resource
    public AdjunctSomethingMapper adjunctSomethingMapper;

    @Resource
    public ITransportSignRecordService signRecordService;

    @Resource
    public TransportSignRecordMapper transportSignRecordMapper;

    @Resource
    public IExceptionLogService exceptionLogService;

    @Resource
    public TransportRightMapper rightMapper;

    @Autowired
    ISendMessageLogService sendMessageLogService;

    @Resource
    public BundleReceiptServiceImpl receiptservice;

    @Resource
    public ExceptionLogMapper exceptionLogMapper;


    public static Function<TransportOrder, Integer> GETZITI = (order) -> (order.getFwfs() == 0 || order.getFwfs() == 3) ? 0 : 1;

    /**
     * 根据运单号将所有运单细则查询出来
     *
     * @param ydbhid
     * @return
     */
    @Override
    public Collection<TransportOrderDetail> queryTransportOrderDetailByYdbhid(String ydbhid) {
        return bundleReceiptMapper.queryTransportOrderDetailByYdbhid(ydbhid);
    }

    @Override
    public List<BundleReceipt> queryLastBundleReceiptByYdbhid(String ydbhid) {
        return bundleReceiptMapper.queryLastBundleReceiptByYdbhid(ydbhid);
    }


    @Override
    public BundleReceipt getBundleReceipt(String chxh, Date fchrq) throws Exception {
        logger.info("查询整车装载信息 车牌号 {} 发车日期 {}", chxh, fchrq);
        Assert.notNull("chxh", chxh, "车牌号不能为空");
        Assert.notNull("fchrq", fchrq, "发车日期不能为空");

        String before = CommonDateUtil.DateToString(fchrq, "yyyy-MM-dd HH:mm:ss");
        String after = CommonDateUtil.DateToString(new Date(fchrq.getTime() + 1000), "yyyy-MM-dd HH:mm:ss");

        List<BundleReceipt> list = bundleReceiptMapper.getBundleReceiptByChxhAndFchrq(chxh, before, after);
        if (CollectionUtils.isEmpty(list))
            throw new ParameterException("查询参数有误");

        list.forEach(receipt -> {
            TransportOrder order = transportOrderService.getTransportOrderByYdbhid(receipt.getYdbhid());
            receipt.setFhdwmch(order.getFhdwmch());        //不想新建属性,在应该不用的几个字段保存下下面字段
            receipt.setKhdh(order.getFhdwdzh());
            receipt.setDhgrid(GETZITI.apply(order) == 0 ? "自提" : "送货上门");
            receipt.setYdendplace(String.valueOf(createResultMap(order).get("endPlacename")));
            receipt.setClOwner(receipt.getClOwner().trim());
        });

        BundleReceipt bundleReceipt = new BundleReceipt();
        BeanUtils.copyProperties(list.get(0), bundleReceipt);
        bundleReceipt.setBundReceiptList(list);
        return bundleReceipt;
    }


    @Override
    public List<BundleReceipt> queryBundleReceiptByYdbhidDesc(String ydbhid) {
        return bundleReceiptMapper.queryBundleReceiptByYdbhidDesc(ydbhid);
    }

    @Override
    public List<BundleReceipt> getLastBundleReceiptByYdbhid(String ydbhid) {
        List<BundleReceipt> breceiptList = queryBundleReceiptByYdbhidDesc(ydbhid);
        List<BundleReceipt> receiptList = new ArrayList<>();
        if (breceiptList != null && breceiptList.size() != 0) {
            Date date = breceiptList.get(0).getFchrq();
            for (BundleReceipt bundleReceipt : breceiptList) {
                Date fchrq = bundleReceipt.getFchrq();
                if (date.getTime() <= fchrq.getTime()) {
                    date = fchrq;
                }
            }
            for (BundleReceipt bundleReceipt : breceiptList) {
                if (bundleReceipt.getFchrq().getTime() == date.getTime()) {
                    receiptList.add(bundleReceipt);
                }
            }
        }
        return receiptList;
    }

    @Override
    public boolean isExistChxhAndZhchrq(String chxh, String zhchrq) {
        int count = bundleReceiptMapper.countBundleByChxhAndZhchrq(chxh, zhchrq);
        return count > 0;
    }

    @Transactional
    @Override
    public JsonResult undoCargoStorage(String company, String userName, String account, String ydbhid) {
        Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.TMSWEIHUMEN_1, account);
        if (rightNum == null || rightNum < 1) {
            return JsonResult.getConveyResult("400", "你没有权限使用这个功能，请与系统管理员联系！");
        }
        // 1.查看是否存在签收状态
        TransportSignRecord signRecord = transportSignRecordMapper.selectTransportSignRecordByYdbhid(ydbhid);
        if (signRecord.getQszt() > 0) {
            return JsonResult.getConveyResult("400", "存在签收状态请先撤销签收再执行撤销,撤消运单号" + ydbhid + "货物入库操作失败");
        }
        // 2.检查是否存在派车单或提货单存在需要先撤销才能撤销到货物入库（调用一个存储过程）
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ydbhid", ydbhid);
        bundleReceiptMapper.callProcedureOfUndoCargoStorage(map);
        int result = (Integer) map.get("result");
        if (result == 1) {
            return JsonResult.getConveyResult("400", "存在提货单或送货派车单，请先撤销提货单或送货派车单,撤消运单号" + ydbhid + "货物入库操作失败");
        }
        // 3.插入日志
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setOperatorName(userName);
        exceptionLog.setOperatorAccount(account);
        exceptionLog.setOperatorCompany(company);
        exceptionLog.setIpAddress(IPUtil.getLocalIP());
        exceptionLog.setYdbhid(ydbhid);
        exceptionLog.setOperatingMenu(CommonConstants.TMS_UNLOADING);
        exceptionLog.setOperatingContent("运单号为:" + ydbhid + ",撤销到货物入库操作");
        exceptionLog.setOperatingTime(new Date());
        exceptionLog.setCreateName(userName);
        exceptionLog.setCreateTime(new Date());
        exceptionLog.setUpdateName(userName);
        exceptionLog.setUpdateTime(new Date());
        exceptionLogMapper.insertExceptionLog(exceptionLog);
        // 4.删除分离库存
        adjunctSomethingMapper.deleteFenLiKucunByYdbhid(ydbhid);
        // 5.删除装载清单
        bundleReceiptMapper.deleteHczzqdSourceByYdbhid(ydbhid);
        // 6.删除装载清单备注
        bundleReceiptMapper.deleteHczzqdSourceBeiZhuByYdbhid(ydbhid);
        // 7.通过运单编号和操作类型为货物入库  删除货运记录
        adjunctSomethingMapper.deleteFreightRecordByYdbhid(ydbhid, "货物入库");
        // 8.通过运单号更新库存
        adjunctSomethingMapper.updateKucunByYdbhid(ydbhid);
        // 9.跟新签收信息
        transportSignRecordMapper.updateSignRecordByYdbhid(ydbhid);
        // 10.删除成本明细
        adjunctSomethingMapper.deleteIncomeCost(ydbhid);
        return JsonResult.getConveyResult("200", "操作成功！");
    }


    @Override
    @Transactional
    public JsonResult saveBundle(RequestJsonEntity map) throws ParameterException, BusinessException, ParseException {
        //校验基本的信息
        map.put("sysName", "麒麟");
        map.put("newbill", 1);
        map.put("autoArrive", 0);
        if (StringUtils.isEmpty(map.getString("daozhan")))
            map.put("daozhan", map.get("arriveStation"));
        if (StringUtils.isEmpty(map.getString("dzshhd")))
            map.put("dzshhd", map.get("freightStation"));
        if (StringUtils.isEmpty(map.getString("fazhan")))
            map.put("fazhan", map.getString("company"));
        map.remove("arriveStation");                        //到站
        map.remove("freightStation");                        //到站网点
        Assert.hasLength("daozhan", map.getString("daozhan"), "目的地不能为空");

        Integer iType = map.getInteger("iType");
        String clOwner = map.getString("clOwner");
        Assert.hasLength("conveys", map.getString("conveys"), "请填写要装在的运单!");
        Assert.hasLength("fchrq", map.getString("fchrq"), "发车时间不能为空");
        Assert.hasLength("yjddshj", map.getString("yjddshj"), "预计到达时间不能为空");
        Assert.hasLength("qdCost", map.getString("qdCost"), "成本不能为空");
        if (!(1 == iType || 2 == iType || (0 == iType && !"中转".equals(map.getString("istransfer"))))) {    //除干线中转以外，外线信息必填
            Assert.hasLength("wxName", map.getString("wxName"), "外线名称不能为空");
            if ("外线".equals(clOwner)) {
                Assert.hasLength("wxConName", map.getString("wxConName"), "外线联系人不能为空");
                Assert.hasLength("wxTel", map.getString("wxTel"), "外线电话不能为空");
            }
        }

        //if ("外线".equals(clOwner)) {
        //	Assert.hasLength("wxName", map.getString("wxName"), "外线名称不能为空");
        //	Assert.hasLength("wxConName", map.getString("wxConName"), "外线联系人不能为空");
        //	Assert.hasLength("wxTel", map.getString("wxTel"), "外线电话不能为空");
        //}else {
        //	map.remove("wxName");
        //	map.remove("wxConName");
        //	map.remove("wxTel");
        //	map.remove("wxItem");
        //}
        //整车和零担都修改为：车牌号必填
        Assert.hasLength("chxh", map.getString("chxh"), "车牌号不能为空");
        if (StringUtils.isNotBlank(map.getString("driverTel"))) {
            if (!MobileRegex.regexMobile(map.getString("driverTel"))) {
                throw new ParameterException("请输入正确的司机手机号");
            }
        }
//		String tosame = map.getString("toSame");
//		if("否".equals(tosame)) {
//			boolean isExist = isExistChxhAndZhchrq(map.getString("chxh"), map.getString("fchrq"));
//			if(isExist) {
//				throw new ParameterException("根据车牌号“"+map.getString("chxh")+"”和发车时间“"+map.getString("fchrq")+"”，查询到已存在相同的装载清单！");
//			}
//		}
        map.put("elseCost", map.getBigDecimal("elseCost"));
        map.put("fchrq", CommonDateUtil.StringToDate(map.getString("fchrq")));
        map.put("zhchrq", map.getDate("fchrq"));

        //校验装载时间不能小于以前装载的时间
        String currFchrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(map.getDate("fchrq"));
        String convey = map.getString("conveys");
        map.remove("istransfer");
        List<JSONObject> conList = JsonUtils.converList(convey);
        String ydbhids[] = new String[conList.size()];
        for (int i = 0; i < conList.size(); i++) {
            JSONObject conver = conList.get(i);
            String ydbhid = conver.getString("ydbhid");
            ydbhids[i] = ydbhid;
        }
        //检查所有运单装载时间有比当前装载时间大的情况
        String[] currBundleTimeSmallerYdbhid = bundleReceiptMapper.getBiggerBundleReceiptTimeYdbhid("'" + StringUtils.join(ydbhids, "','") + "'", currFchrq);
        if (currBundleTimeSmallerYdbhid.length > 0) {
            throw new ParameterException("当前装载发车时间不能小于历史装载时间，请查看如下运单的装载清单：" + StringUtils.join(currBundleTimeSmallerYdbhid, ","));
        }

        //校验装车日期是不是小于当前系统时间
        if (map.getDate("fchrq").getTime() > new Date().getTime() + 1800 * 1000) {
            throw new ParameterException("发车时间要小于等于当前系统时间");
        }

        map.put("lrsj", new Date());
        map.put("yjddshj", CommonDateUtil.StringToDate(map.getString("yjddshj")));
        List<TransportOrder> sichuanOrder = new ArrayList<>();        //四川的运单是要发短信的
        List<String> xuhaos = new ArrayList<>();
        switch (iType) {
            case 2:        //提
                String conveys = map.getString("conveys");
                map.remove("istransfer");
                List<JSONObject> converList = JsonUtils.converList(conveys);
                for (JSONObject conver : converList) {
                    String ydbhid = conver.getString("ydbhid");
                    TransportOrder transportOrder = transportOrderMapper.getTransportOrderByYdbhid(ydbhid);
                    List<TransportOrderDetail> detailList = transportOrderMapper.getTransportOrderDetail(ydbhid);
                    transportOrder.setDetailList(detailList);
                    sichuanOrder.add(transportOrder);
                    map.put("ziti", GETZITI.apply(transportOrder));        //送货上门
                    map.put("shhrlxdh", transportOrder.getShhrlxdh());
                    map.put("shhrmch", transportOrder.getShhrmch());
                    map.put("fhdwmch", transportOrder.getFhdwmch());
                    map.put("fhshj", transportOrder.getFhshj());
                    map.put("yshm", transportOrder.getYshhm());
                    JSONArray detail = JSONArray.parseArray(String.valueOf(conver.get("details")));
                    map.putAll(conver);
                    map.put("fhshj", transportOrder.getFhshj());

                    prepBundleReceipt(transportOrder, map);

                    for (Object orderdetails : detail) {
                        //一个运单的明细
                        JSONObject ordertail = (JSONObject) orderdetails;
                        BigDecimal entruckingzhl = new BigDecimal(ordertail.getString("entruckingzhl"));
                        BigDecimal entruckingtiji = new BigDecimal(ordertail.getString("entruckingtiji"));
                        BigDecimal entruckingjianshu = new BigDecimal(ordertail.getString("entruckingjianshu"));
                        if (entruckingzhl.compareTo(BigDecimal.ZERO) <= 0 && entruckingtiji.compareTo(BigDecimal.ZERO) <= 0 && entruckingjianshu.compareTo(BigDecimal.ZERO) <= 0)
                            throw new ParameterException("请输入装载的数量");
                        Integer ydxzh = ordertail.getInteger("ydxzh");
                        TransportOrderDetail orderDetailByYdbhidAndYdxzh = detailService.getOrderDetailByYdbhidAndYdxzh(ydbhid, ydxzh);
                        map.put("xh", orderDetailByYdbhidAndYdxzh.getXh());
                        //查询仓库
                        KucunEntity queryKucun = adjunctSomethingService.queryKucun(ydbhid, ydxzh);
                        if (!checkKucun(new BigDecimal(queryKucun.getZhl()),
                                Integer.parseInt(queryKucun.getJianshu()),
                                new BigDecimal(queryKucun.getTiji()), ordertail)) {
                            throw new ParameterException(ydbhid + "库存不足,请核实库存是否匹配本次装载!");
                        }

                        //经核实可以保存,开始进行保存操作
                        ordertail.put("zhl", new BigDecimal(queryKucun.getZhl()).subtract(entruckingzhl));
                        ordertail.put("tiji", new BigDecimal(queryKucun.getTiji()).subtract(entruckingtiji));
                        ordertail.put("jianshu", new BigDecimal(queryKucun.getJianshu()).subtract(entruckingjianshu));
                        ordertail.put("ydbhid", ydbhid);
                        adjunctSomethingService.updateKucun(ordertail);

                        map.putAll(ordertail);
                        map.put("beizhu", getBeizhu(ydbhid));
                        map.put("parentXuhao", "1");//第一批装载
                        bundleReceiptMapper.insertBundleReceipt(map);
                        bundleReceiptMapper.insertHczzqd_beizhu(map);
                        Date compareDate = map.getDate("fchrq");
                        List<BundleReceipt> list = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[]{ydbhid})
                                .stream()
                                .filter(receipt -> ydxzh == receipt.getYdxzh().intValue()
                                        && receipt.getFchrq().compareTo(compareDate) == 0)
                                .collect(Collectors.toList());
                        xuhaos.add(String.valueOf(list.get(0).getXuhao()));
                    }
                }
                map.put("hyy", map.getString("grid"));
                map.put("xuhaos", xuhaos);
                try {
                    this.createIncomeWhenSaveBundle(map);
                } catch (ParameterException e) {
                    throw new ParameterException(e.getTipMessage());
                }
                break;
            case 0:        //干
                Assert.hasLength("ysfs", map.getString("ysfs"), "运输方式不能为空");
                if (!"中转".equals(map.getString("istransfer"))) {
                    map.put("fazhan", map.get("company"));
                }
                String conveys2 = map.getString("conveys");
                List<JSONObject> converList2 = JsonUtils.converList(conveys2);
                for (JSONObject conver : converList2) {
                    String ydbhid = String.valueOf(conver.get("ydbhid"));
                    TransportOrder transportOrder = transportOrderService.getTransportOrderByYdbhid(ydbhid);
                    map.put("ziti", GETZITI.apply(transportOrder));        //送货上门
                    map.put("shhrlxdh", transportOrder.getShhrlxdh());
                    map.put("shhrmch", transportOrder.getShhrmch());
                    map.put("fhdwmch", transportOrder.getFhdwmch());
                    map.put("fhshj", transportOrder.getFhshj());
                    map.put("yshm", transportOrder.getYshhm());
                    JSONArray detail = JSONArray.parseArray((String.valueOf(conver.get("details"))));
                    map.putAll(conver);
                    map.put("fhshj", transportOrder.getFhshj());
                    prepBundleReceipt(transportOrder, map);
                    boolean isallow = false;
                    //如果细则号一样就合并
                    HashMap<String, HashMap<String, String>> details = mergeSameYdxzh(detail);
                    for (String key : details.keySet()) {
                        HashMap<String, String> dt = details.get(key);
                        dt.put("company", map.getString("company"));
                        BigDecimal entruckingzhl = new BigDecimal(dt.get("zhl"));
                        BigDecimal entruckingtiji = new BigDecimal(dt.get("tiji"));
                        Integer entruckingjianshu = Integer.parseInt(dt.get("jianshu"));
                        String pinming = dt.get("pinming");
                        String parentXuhao = dt.get("xuhao");
                        if (entruckingzhl.compareTo(BigDecimal.ZERO) <= 0 && entruckingtiji.compareTo(BigDecimal.ZERO) <= 0 && entruckingjianshu <= 0) {
                            continue;
                        } else {
                            isallow = true;
                        }
                        if (!isallow) {
                            throw new ParameterException("请输入装载的数量");
                        }
                        Integer ydxzh = Integer.parseInt(key);
                        TransportOrderDetail orderDetailByYdbhidAndYdxzh = detailService.getOrderDetailByYdbhidAndYdxzh(ydbhid, ydxzh);
                        map.put("xh", orderDetailByYdbhidAndYdxzh.getXh());

                        this.updateFenliOrkucun(map.get("company").toString(), ydbhid, ydxzh, dt, parentXuhao);

                        //保存装载信息
                        //更新上一次装载的提货状态
                        bundleReceiptMapper.updateBundleReceiptyiti(ydbhid, ydxzh, map.getString("company"));
                        //新增装载清单
                        map.put("ydxzh", ydxzh);
                        map.put("pinming", pinming);
                        map.put("entruckingzhl", new BigDecimal(dt.get("zhl")));
                        map.put("entruckingtiji", new BigDecimal(dt.get("tiji")));
                        map.put("entruckingjianshu", Integer.parseInt(dt.get("jianshu")));
                        map.put("beizhu", getBeizhu(ydbhid));
                        map.put("parentXuhao", parentXuhao);
                        bundleReceiptMapper.insertBundleReceipt(map);
                        bundleReceiptMapper.insertHczzqd_beizhu(map);

                        Date compareDate = map.getDate("fchrq");
                        List<BundleReceipt> list = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[]{ydbhid})
                                .stream()
                                .filter(receipt -> ydxzh == receipt.getYdxzh().intValue()
                                        && receipt.getFchrq().compareTo(compareDate) == 0)
                                .collect(Collectors.toList());
                        xuhaos.add(String.valueOf(list.get(0).getXuhao()));
                    }
                    transportOrderMapper.updateYsfs(map.getString("ysfs"), ydbhid);
                }
                map.put("hyy", map.getString("grid"));
                map.put("xuhaos", xuhaos);
                try {
                    this.createIncomeWhenSaveBundle(map);
                } catch (ParameterException e) {
                    throw new ParameterException(e.getTipMessage());
                }
                break;
            case 1:        //配
                Assert.hasLength("ysfs", map.getString("ysfs"), "运输方式不能为空");
                String conveys1 = map.getString("conveys");
                map.remove("istransfer");
                List<JSONObject> converList1 = JsonUtils.converList(conveys1);
                for (JSONObject conver : converList1) {
                    String ydbhid = String.valueOf(conver.get("ydbhid"));
                    TransportOrder transportOrder = transportOrderMapper.getTransportOrderByYdbhid(ydbhid);
                    map.put("ziti", GETZITI.apply(transportOrder));
                    map.put("shhrlxdh", transportOrder.getShhrlxdh());
                    map.put("shhrmch", transportOrder.getShhrmch());
                    map.put("fhdwmch", transportOrder.getFhdwmch());
                    map.put("yshm", transportOrder.getYshhm());
                    JSONArray detail = JSONArray.parseArray(String.valueOf(conver.get("details")));
                    map.putAll(conver);
                    map.put("fhshj", transportOrder.getFhshj());
                    prepBundleReceipt(transportOrder, map);
                    //如果细则号一样就合并
                    HashMap<String, HashMap<String, String>> details = mergeSameYdxzh(detail);
                    for (String key : details.keySet()) {
                        HashMap<String, String> dt = details.get(key);
                        dt.put("company", map.getString("company"));
                        BigDecimal entruckingzhl = new BigDecimal(dt.get("zhl"));
                        BigDecimal entruckingtiji = new BigDecimal(dt.get("tiji"));
                        Integer entruckingjianshu = Integer.parseInt(dt.get("jianshu"));
                        String pinming = dt.get("pinming");
                        String parentXuhao = dt.get("xuhao");

                        if (entruckingzhl.compareTo(BigDecimal.ZERO) <= 0 && entruckingtiji.compareTo(BigDecimal.ZERO) <= 0
                                && (entruckingjianshu <= 0)) {
                            throw new ParameterException("请输入装载的数量");
                        }
                        Integer ydxzh = Integer.parseInt(key);

                        TransportOrderDetail orderDetailByYdbhidAndYdxzh = detailService.getOrderDetailByYdbhidAndYdxzh(ydbhid, ydxzh);
                        map.put("xh", orderDetailByYdbhidAndYdxzh.getXh());

                        //更新库存或分理库存
                        this.updateFenliOrkucun(map.getString("company").toString(), ydbhid, ydxzh, dt, parentXuhao);
                        //map.putAll(orderDetail);
                        //更新上一次装载的提货状态
                        bundleReceiptMapper.updateBundleReceiptyiti(ydbhid, ydxzh, map.getString("company"));
                        //新增装载清单
                        map.put("beizhu", getBeizhu(ydbhid));
                        map.put("ydxzh", ydxzh.shortValue());
                        map.put("pinming", pinming);
                        map.put("entruckingzhl", new BigDecimal(dt.get("zhl")));
                        map.put("entruckingtiji", new BigDecimal(dt.get("tiji")));
                        map.put("entruckingjianshu", Integer.parseInt(dt.get("jianshu")));
                        map.put("parentXuhao", parentXuhao);
                        bundleReceiptMapper.insertBundleReceipt(map);
                        bundleReceiptMapper.insertHczzqd_beizhu(map);
//					xuhaos.add(String.valueOf(adjunctSomethingMapper.getXuhaoByTransportCode(map)));
                        Date compareDate = map.getDate("fchrq");

                        List<BundleReceipt> list = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[]{ydbhid});
                        list = list.stream()
                                .filter(receipt -> ydxzh == receipt.getYdxzh().intValue()
                                        && receipt.getFchrq().compareTo(compareDate) == 0)
                                .collect(Collectors.toList());
                        xuhaos.add(String.valueOf(list.get(0).getXuhao()));
                    }
                    transportOrderMapper.updateYsfs(map.getString("ysfs"), ydbhid);
                }
                //成本
                map.put("xuhaos", xuhaos);
                try {
                    this.createIncomeWhenSaveBundle(map);
                } catch (ParameterException e) {
                    throw new ParameterException(e.getTipMessage());
                }
                break;
            default:
                return JsonResult.getConveyResult("400", "操作失败");
        }


        return JsonResult.getConveyResult("200", "操作成功");
    }


    //预处理订单
    private void prepBundleReceipt(TransportOrder transportOrder, RequestJsonEntity entity) throws ParseException {


        //快运运单，成本不能根据车牌号和时间来合并。
        //合同物流运单是根据车牌号和装载时间来确定的是否合并成本的。
        List<BundleReceiptHomePageEntity> bundleReceiptHomePageEntityList = Collections.emptyList();
        Date fchrq = entity.getDate("fchrq");
        List<String> xuhaos = (List<String>) entity.get("xuhaos");
        String chxh = entity.getString("chxh");
        if (transportOrder.getIsKaiyun() != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(fchrq.getTime());
            do {
                 bundleReceiptHomePageEntityList =bundleReceiptMapper.getBundleReceiptHomePageEntityByMap(chxh, calendar.getTime());
                if (!bundleReceiptHomePageEntityList.isEmpty()) {
                    calendar.add(Calendar.SECOND, 1);
                }
            } while (!bundleReceiptHomePageEntityList.isEmpty());
            entity.put("fchrq", calendar.getTime());
           // entity.put("zhchrq", calendar.getTime());
        }
    }

    /**
     * 若是之前分装的有全装了，就合并
     *
     * @param detail
     * @return
     */
    private HashMap<String, HashMap<String, String>> mergeSameYdxzh(JSONArray detail) {
        HashMap<String, HashMap<String, String>> details = new HashMap<String, HashMap<String, String>>();
        for (Object orderDetails : detail) {
            JSONObject orderDetail = (JSONObject) orderDetails;
            Integer ydxzh = Integer.parseInt(orderDetail.getString("ydxzh"));
            BigDecimal entruckingzhl = new BigDecimal(orderDetail.getString("entruckingzhl"));
            BigDecimal entruckingtiji = new BigDecimal(orderDetail.getString("entruckingtiji"));
            Integer entruckingjianshu = Integer.parseInt(orderDetail.getString("entruckingjianshu"));
            if (entruckingzhl.compareTo(BigDecimal.ZERO) == 1 || entruckingtiji.compareTo(BigDecimal.ZERO) == 1 || entruckingjianshu > 0) {
                if (!details.containsKey(ydxzh.toString())) {
                    HashMap<String, String> dd = details.get(ydxzh.toString());
                    if (dd == null) {
                        dd = new HashMap<String, String>();
                    }
                    dd.put("zhl", entruckingzhl.toString());
                    dd.put("tiji", entruckingtiji.toString());
                    dd.put("jianshu", entruckingjianshu.toString());
                    dd.put("xuhao", orderDetail.getString("xuhao"));
                    dd.put("ydxzh", orderDetail.getString("ydxzh"));
                    dd.put("pinming", orderDetail.getString("pinming"));
                    details.put(ydxzh.toString(), dd);
                } else {
                    HashMap<String, String> dd = details.get(ydxzh.toString());
                    if (dd == null) {
                        dd = new HashMap<String, String>();
                    }
                    String zhl = new BigDecimal(dd.get("zhl")).add(new BigDecimal(orderDetail.getString("entruckingzhl"))).toString();
                    String tiji = new BigDecimal(dd.get("tiji")).add(new BigDecimal(orderDetail.getString("entruckingtiji"))).toString();
                    String jianshu = (Integer.parseInt(dd.get("jianshu")) + Integer.parseInt(orderDetail.getString("entruckingjianshu"))) + "";
                    String xuhao = dd.get("xuhao") + "," + orderDetail.getString("xuhao");
                    dd.put("zhl", zhl);
                    dd.put("tiji", tiji);
                    dd.put("jianshu", jianshu);
                    dd.put("xuhao", xuhao);
                    details.put(ydxzh.toString(), dd);
                }
            }
        }
        return details;
    }

    private void updateFenliOrkucun(String company, String ydbhid, Integer ydxzh, HashMap<String, String> dt, String parentXuhao) throws ParameterException {
        //使用库存
        BigDecimal entruckingzhl = new BigDecimal(dt.get("zhl"));
        BigDecimal entruckingtiji = new BigDecimal(dt.get("tiji"));
        Integer entruckingjianshu = Integer.parseInt(dt.get("jianshu"));

        String xh[] = parentXuhao.split(",");
        Integer xuhaoArray[] = new Integer[xh.length];
        for (int i = 0; i < xh.length; i++) {
            xuhaoArray[i] = Integer.parseInt(xh[i]);
        }
        if ("1".equals(parentXuhao)) {//从库存拿的话就是1
            KucunEntity queryKucun = adjunctSomethingService.queryKucun(ydbhid, ydxzh);
            if (entruckingjianshu > Integer.parseInt(queryKucun.getJianshu())
                    || entruckingtiji.compareTo(new BigDecimal(queryKucun.getTiji())) == 1
                    || entruckingzhl.compareTo(new BigDecimal(queryKucun.getZhl())) == 1) {
                throw new ParameterException(ydbhid + "库存不足");
            }
            //减库存
            HashMap<String, Object> orderDetail = new HashMap<String, Object>();
            orderDetail.put("zhl", new BigDecimal(queryKucun.getZhl()).subtract(entruckingzhl));
            orderDetail.put("tiji", new BigDecimal(queryKucun.getTiji()).subtract(entruckingtiji));
            orderDetail.put("jianshu", Integer.parseInt(queryKucun.getJianshu()) - entruckingjianshu);
            orderDetail.put("ydxzh", Integer.parseInt(dt.get("ydxzh")));
            orderDetail.put("ydbhid", ydbhid);
            adjunctSomethingService.updateKucun(orderDetail);
        } else {        //已装载过的运单的库存是从分离库存表中获取数据的
            List<FenliKucunEntity> fenliKucunList = new ArrayList<FenliKucunEntity>();
            if (parentXuhao == null || "0".equals(parentXuhao)) {//老数据
                fenliKucunList = adjunctSomethingService.queryFenliKucunEntity(ydbhid, ydxzh, company);
            } else {
                fenliKucunList = arriveStationMapper.queryFenlikucunByXuhaos(xuhaoArray);
            }
            Integer fenliJianshu = 0;
            BigDecimal fenliTiji = new BigDecimal(0);
            BigDecimal fenliZhl = new BigDecimal(0);
            for (FenliKucunEntity entity : fenliKucunList) {
                fenliJianshu += Integer.parseInt(entity.getJianshu());
                fenliTiji = fenliTiji.add(new BigDecimal(entity.getTiji()));
                fenliZhl = fenliZhl.add(new BigDecimal(entity.getZhl()));
            }
            if (entruckingjianshu > fenliJianshu || entruckingtiji.compareTo(fenliTiji) == 1 || entruckingzhl.compareTo(fenliZhl) == 1) {
                throw new ParameterException(ydbhid + "分理库存不足,请核实库存是否匹配本次装载!");
            }

            HashMap<String, Object> orderDetail = new HashMap<String, Object>();
            for (FenliKucunEntity entity : fenliKucunList) {
                if (new BigDecimal(entity.getTiji()).compareTo(entruckingtiji) == -1) {//分理库存比要装载的小
                    entruckingtiji = entruckingtiji.subtract(new BigDecimal(entity.getTiji()));//剩余未装载完的
                    orderDetail.put("tiji", BigDecimal.ZERO);
                    entity.setTiji("0");
                } else {
                    orderDetail.put("tiji", new BigDecimal(entity.getTiji()).subtract(entruckingtiji));
                    entruckingtiji = BigDecimal.ZERO;
                }
                if (new BigDecimal(entity.getZhl()).compareTo(entruckingzhl) == -1) {//分理库存比要装载的小
                    entruckingzhl = entruckingzhl.subtract(new BigDecimal(entity.getZhl()));//剩余未装载完的
                    orderDetail.put("zhl", BigDecimal.ZERO);
                    entity.setZhl("0");
                } else {
                    orderDetail.put("zhl", new BigDecimal(entity.getZhl()).subtract(entruckingzhl));
                    entruckingzhl = BigDecimal.ZERO;
                }
                if (Integer.parseInt(entity.getJianshu()) < entruckingjianshu) {//分理库存比要装载的小
                    entruckingjianshu = entruckingjianshu - Integer.parseInt(entity.getJianshu());//剩余未装载完的
                    orderDetail.put("jianshu", 0);
                    entity.setJianshu("0");
                } else {
                    orderDetail.put("jianshu", Integer.parseInt(entity.getJianshu()) - entruckingjianshu);
                    entruckingjianshu = 0;
                }
                orderDetail.put("company", dt.get("company"));
                orderDetail.put("xuhao", entity.getXuhao());
                orderDetail.put("ydxzh", Integer.parseInt(dt.get("ydxzh")));
                orderDetail.put("ydbhid", ydbhid);
                if (entity.getXuhao() != null && entity.getXuhao() != 0) {
                    arriveStationMapper.updateBundleFenLiKuCunByXuhao(orderDetail);
                } else {
                    adjunctSomethingService.updateFenLiKucun(orderDetail);
                }
            }
        }
    }


    /**
     * @param tranportOrderOwner 运单归属，0 TMS录入运单，1，快运运单，2 千里马运单
     * @param entity
     * @throws ParseException
     * @throws ParameterException
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public void createIncomeWhenSaveBundle(RequestJsonEntity entity) throws ParseException, ParameterException {

        //是否录入成本
        boolean recordCost = entity.getInteger("buildIncome") == 1;
        if (!recordCost) {
            return;
        }

        List<String> insNoList = Collections.emptyList();
        Date fchrq = entity.getDate("fchrq");
        List<String> xuhaos = (List<String>) entity.get("xuhaos");
        String chxh = entity.getString("chxh");

        String before = CommonDateUtil.DateToString(fchrq, "yyyy-MM-dd HH:mm:ss");
        insNoList = bundleReceiptMapper.getInsNoFromIncome(before, chxh);

        Assert.trueIsWrong("insNo length not equals one", insNoList.size() > 1, "相同车牌号和装车时间已存在2个及以上总成本");
        //整车成本表头ID
        String insNo = null;
        if (!insNoList.isEmpty()) {
            insNo = insNoList.get(0);
        }
        //	1.判断是否有表头
        boolean flag = StringUtils.isNotEmpty(insNo);
        entity.put("insNo", insNo);
        //已生成或者需要生成
        Integer insXh = 0;
        if (flag) {
            //已生成的成本详情的最大ID
            insXh = bundleReceiptMapper.getInsXhFromIncome(insNo);
        } else {
            //未生成，需要生成成本头
            insNo = getIncomecostNo();
            entity.put("insNo", insNo);
            entity.put("tCost", entity.getBigDecimal("qdCost").add(entity.getBigDecimal("elseCost")));
            adjunctSomethingMapper.insertIncome_HCostv2(entity);
        }
        insXh = insXh == null ? 0 : insXh;
        for (String xuhao : xuhaos) {
            entity.put("insXh", String.valueOf(++insXh));
            BundleReceipt receipt = bundleReceiptMapper.getBundleReceiptByXuhao(xuhao);
            entity.put("yshm", transportOrderMapper.get(receipt.getYdbhid()).get(0).getYshhm());
            entity.put("receipt", receipt);
            //添加成本详情
            adjunctSomethingMapper.insertIncome_DCostv2(entity);
        }
    }

    public boolean checkKucun(BigDecimal kucunzhl, Integer kucunjianshu, BigDecimal kucuntiji, JSONObject orderDetail) {
        BigDecimal entruckingzhl = orderDetail.getBigDecimal("entruckingzhl");
        BigDecimal entruckingtiji = orderDetail.getBigDecimal("entruckingtiji");
        Integer entruckingjianshu = orderDetail.getInteger("entruckingjianshu");
        if (entruckingjianshu > kucunjianshu || entruckingtiji.compareTo(kucuntiji) == 1
                || entruckingzhl.compareTo(kucunzhl) == 1)
            return false;
        return true;
    }

    public String getIncomecostNo() {
        Map<String, String> incomeMap = new HashMap<String, String>();
        incomeMap.put("insNo", "");
        adjunctSomethingMapper.genincomecostno(incomeMap);
        String insNo = incomeMap.get("insNo");
        if (insNo == null || insNo.length() < 1)
            throw new BusinessException("成本号生成失败");
        return insNo;
    }

    boolean checkKucun(Integer jianshu, BigDecimal zhl, BigDecimal tiji) {
        if (jianshu <= 0 && zhl.compareTo(new BigDecimal(0)) != 1 && tiji.compareTo(new BigDecimal(0)) != 1)
            return false;
        return true;
    }

    public String getBeizhu(String ydbhid) {
        FiwtResult xianlu = signRecordService.getXianluByYdbhid(ydbhid);
        if (xianlu != null) {
            FkfshResult fkfshResult = signRecordService.getFkfshResult(xianlu);
            if (fkfshResult.getXianjin_b() || fkfshResult.getYhshr_b() == 1)
                return "款清";
            if (fkfshResult.getYshk_b() == 1 && fkfshResult.getHdfk_b() == 0)
                return "月结";
            if (fkfshResult.getHdfk_b() == 1)
                return "货到付款";
            if (fkfshResult.getYshzhk_b())
                return "款未付,等通知";
        }
        return null;
    }

    @Override
    public JsonResult queryBundleConvey(String ydbhid, String iType, String companyName) {
        //1.查有没有该运单号
        List<TransportOrder> list = transportOrderMapper.get(ydbhid);
        if (CollectionUtils.isEmpty(list))
            return JsonResult.getConveyResult("400", "该运单不存在");
        Integer qszt = adjunctSomethingService.isReceivedByYdbhid(ydbhid);
        // select * from T_QSZT_BASE 签收状态
        if (qszt != null && qszt > 0)  // 已经签收过的运单
            return JsonResult.getConveyResult("400", "已签收运单不可重复装载");

        //运单在运单表里是唯一的
        TransportOrder order = list.get(0);
        Map<String, Object> BaseMap = createResultMap(order);

        //如果是提货检查库存
        boolean flag = false; //库存是否有数据的标志 ,默认是没有库存的

        if (iType.equals("2") && !companyName.equals(order.getFazhan()))
            return JsonResult.getConveyResult("400", "只有该运单的发站公司可以做提货装载");

        //先从库存看是否有库存
        List<KucunEntity> kucunList = adjunctSomethingService.queryKucunByYdbhid(ydbhid);
        Iterator<KucunEntity> iterator = kucunList.iterator();
        while (iterator.hasNext()) {
            KucunEntity kucun = iterator.next();
            if (checkKucun(Integer.parseInt(kucun.getJianshu()), new BigDecimal(kucun.getZhl()), new BigDecimal(kucun.getTiji())))
                flag = true; //但凡有一条库存是足够的,那么就代表可以进行装载
            else
                iterator.remove();
        }
        //提货装载如果库存没有了就不能再装载了
        if ("2".equals(iType)) {
            if (!flag) {
                return JsonResult.getConveyResult("400", "库存不足或无权限操作该运单");
            } else {
                BaseMap.put("detail", kucunList);
                JsonResult result = JsonResult.getConveyResult("200", "查询成功");
                result.put("convey", BaseMap);
                return result;
            }
        } else {    //不是提货
            if (flag) { //有库存
                BaseMap.put("detail", kucunList);
                JsonResult result = JsonResult.getConveyResult("200", "查询成功");
                result.put("convey", BaseMap);
                return result;
            } else {//没有库存
                //如果当前登录公司和运单信息中的发站公司相同
                if (companyName.equals(order.getFazhan())) {
                    List<FenliKucunEntity> fenLiKucunList = adjunctSomethingService.queryFenliKucunByYdbhid(ydbhid, companyName);
                    Iterator<FenliKucunEntity> fenliiterator = fenLiKucunList.iterator();
                    while (fenliiterator.hasNext()) {
                        FenliKucunEntity next = fenliiterator.next();
                        if (checkKucun(Integer.parseInt(next.getJianshu()), new BigDecimal(next.getZhl()), new BigDecimal(next.getTiji())))
                            flag = true; //但凡有一条库存是足够的,那么就代表可以进行装载
                        else
                            fenliiterator.remove();
                    }
                    if (!flag) {
                        return JsonResult.getConveyResult("400", "库存不足或无权限操作该运单");
                    } else {
                        BaseMap.put("detail", fenLiKucunList);
                        JsonResult result = JsonResult.getConveyResult("200", "查询成功");
                        result.put("convey", BaseMap);
                        return result;
                    }
                } else {
                    List<FenliKucunEntity> fenLiKucunList = adjunctSomethingService.queryFenliKucunByYdbhid(ydbhid, companyName);
                    Iterator<FenliKucunEntity> fenliiterator = fenLiKucunList.iterator();
                    while (fenliiterator.hasNext()) {
                        FenliKucunEntity kucun = fenliiterator.next();
                        if (checkKucun(Integer.parseInt(kucun.getJianshu()), new BigDecimal(kucun.getZhl()), new BigDecimal(kucun.getTiji())))
                            flag = true; //但凡有一条库存是足够的,那么就代表可以进行装载
                        else
                            fenliiterator.remove();
                    }
                    if (!flag)
                        return JsonResult.getConveyResult("400", "库存不足或无权限操作该运单");
                    //如果不是发站公司,肯定是被中转到了该公司的库存表中
                    List<BundleReceipt> bundleReceipt = bundleReceiptMapper.getBundleReceiptByYDBHID(new String[]{ydbhid});
                    for (BundleReceipt receipt : bundleReceipt) {
                        if (companyName.equals(receipt.getDaozhan())) {    //就是本次装载的货物到达该公司的
                            if (receipt.getiType() == 1 && fenLiKucunList.size() == 0)//如果还有分理库存就给配送
                                return JsonResult.getConveyResult("400", "该运单在" + companyName + "已配送,请核实该运单的物流状态");
                            if (receipt.getiType() == 0) {    //上一次是干线,那么这一次是干或者配送
                                String transferFlag = receipt.getTransferFlag();
                                if (StringUtils.isNotEmpty(transferFlag)) {    //干线的中转标志
                                    if (transferFlag.trim().equals("不中转") && !iType.equals("1"))    //上一次不中转,这次一定是配送
                                        return JsonResult.getConveyResult("400", "该运单已经做过干线不中转，当前只能进行配送装载");
                                }
                            }
                        }
                    }
                    BaseMap.put("detail", fenLiKucunList);
                    JsonResult result = JsonResult.getConveyResult("200", "查询成功");
                    result.put("convey", BaseMap);
                    return result;
                }
            }
        }
    }


    private Map<String, Object> createResultMap(TransportOrder order) {
        Map<String, Object> map = new HashMap<>();
        map.put("ydbhid", order.getYdbhid());
        map.put("fhdwmch", order.getFhdwmch());
        map.put("fhdwdzh", order.getFhdwdzh());
        map.put("fhshj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getFhshj()));
        StringBuilder shrProvinces = new StringBuilder("");
        if (StringUtils.isNotBlank(order.getDhShengfen())) {
            shrProvinces.append(order.getDhShengfen());
        }
        if (StringUtils.isNotBlank(order.getDhChengsi())) {
            if (shrProvinces.length() > 0) {
                shrProvinces.append("-");
            }
            shrProvinces.append(order.getDhChengsi());
        }
        if (StringUtils.isNotBlank(order.getDhAddr())) {
            if (shrProvinces.length() > 0) {
                shrProvinces.append("-");
            }
            shrProvinces.append(order.getDhAddr());
        }
        map.put("endPlacename", shrProvinces.toString());
        map.put("ziti", GETZITI.apply(order) == 0 ? "自提" : "送货上门");
        return map;
    }

    @Override
    public BundleReceipt getBundleReceiptByXuhao(String xuhao) {
        return bundleReceiptMapper.getBundleReceiptByXuhao(xuhao);
    }

    @Override
    public BundleReceipt findBundleReceiptByXuhao(Integer xuhao) {
        return bundleReceiptMapper.findBundleReceiptByXuhao(xuhao);
    }

    @Override
    public PageInfo<BundleReceiptHomePageEntity> searchBundleReceiptV2(BundleReceiptSearch receiptSearch, int pageNum, int pageSize)
            throws ParameterException, BusinessException {
        receiptSearch.setPageNums(pageNum);
        receiptSearch.setPageSizes(pageSize);
        //查询分页总条数
        Integer total = bundleReceiptMapper.listBundleReceiptCounts(receiptSearch);
        int startRow = 0, endRow = 0, pages = 0;
        if (pageNum > 0 && pageSize > 0) {
            startRow = (pageNum - 1) * pageSize;
            pages = (int) Math.ceil((double) total / pageSize);
        }
        endRow = startRow + pageSize;
        Collection<BundleReceiptHomePageEntity> list = bundleReceiptMapper.searchTop10BundleReceiptList(receiptSearch);

        Collection<BundleReceiptHomePageEntity> resultCollection = new ArrayList<>();
        for (BundleReceiptHomePageEntity entity : list) {
            List<BundleReceiptHomePageEntity> pageEntityList = bundleReceiptMapper.getBundleReceiptHomePageEntityByMap(entity.getChxh(), entity.getFchrq());
            pageEntityList.forEach(subentity -> {
                String fhdwmch;
                if (StringUtils.isEmpty((fhdwmch = subentity.getFhdwmch())))
                    fhdwmch = transportOrderMapper.getTransportOrderByYdbhid(subentity.getYdbhid()).getFhdwmch();
                subentity.setFhdwmch(fhdwmch);
            });
            entity = pageEntityList.get(0);
            //本来只是为了适应tms自动为空,后来又因分理装载的成本组合问题再加一个判断
            if (entity.getBuildIncome() == null || entity.getBuildIncome() == 0)
                entity.setBuildIncome(bundleReceiptMapper.countIncome(pageEntityList.get(0)) <= 0 ? 0 : 1);
            //--end
            entity.setDetailData(pageEntityList);
            resultCollection.add(entity);
        }
        Page<BundleReceiptHomePageEntity> result = new Page<>(pageNum, pageSize, startRow, endRow, total, pages, resultCollection);

        return new PageInfo<>(result);
    }


    @Override
    @Transactional
    public JsonResult modifyIncome(RequestJsonEntity entity) throws Exception {
        //查看是否有权限操作该功能
        //适应tms装车日期是随时生成的问题
        Date fchrq = entity.getDate("fchrq");
        entity.put("fchrq", CommonDateUtil.DateToString(fchrq, "yyyy-MM-dd HH:mm:ss"));
        entity.put("end", CommonDateUtil.DateToString(new Date(fchrq.getTime() + 1000), "yyyy-MM-dd HH:mm:ss"));

        String company = entity.getString("company");
        String grid = entity.getString("grid");
        List<BundleReceipt> list = bundleReceiptMapper.getBundleReceiptByChxhAndFchrq
                (entity.getString("chxh"), entity.getString("fchrq"), entity.getString("end"));

        if (CollectionUtils.isEmpty(list))
            return JsonResult.getConveyResult("400", "该装载有异常,请联系管理员");
        else {
            BundleReceipt receipt = list.get(0);
            TransportOrder order = transportOrderMapper.getTransportOrderByYdbhid(receipt.getYdbhid());
            Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.EDIT_COST, entity.getString("account"));
            if (receipt.getiType() == 2 && !company.equals(order.getFazhan())) {    //提货,并且是当前登陆公司可以装载
                return JsonResult.getConveyResult("400", "提货装载请在运单的始发站做成本");
            }
            rightNum = rightNum == null ? 0 : rightNum;
            if (rightNum < 3) {
                if (!company.equals(receipt.getFazhan()))
                    return JsonResult.getConveyResult("400", "不能操作其他公司的装载成本");
//				if(receipt.getiType() != 2 && !company.equals(receipt.getFazhan())) 
//					return JsonResult.getConveyResult("400", "非本公司的装载清单,无法修改");
                Date lrsjafterhalfhour = new Date(receipt.getLrsj().getTime() + 30 * 60 * 1000);
                //jirabug:923

                rightNum = rightMapper.getRightNum(AuthorticationConstant.EDIT_COST, entity.getString("account"));
                if (rightNum == null || rightNum < 1) {        //无财凭冲红权限
                    if (new Date().compareTo(lrsjafterhalfhour) == 1)    //三十分钟之内不用判断权限
                        return JsonResult.getConveyResult("400", "无权限,超过半小时如有需要修改成本,请联系管理员申请权限");
                }
            }
        }
        //记日志
        Date now = new Date();
        //新增异常日志信息
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setOperatorName(grid);
        exceptionLog.setOperatorAccount(entity.getString("account"));
        exceptionLog.setOperatorCompany(company);
        exceptionLog.setIpAddress(IPUtil.getLocalIP());
        exceptionLog.setOperatingTime(now);
        exceptionLog.setCreateName(grid);
        exceptionLog.setCreateTime(now);
        exceptionLog.setUpdateName(grid);
        exceptionLog.setUpdateTime(now);
        exceptionLog.setOperatingMenu("修改装载成本");
        list.stream().forEach(receipt -> {
            try {
                String ydbhid = receipt.getYdbhid();
                exceptionLog.setYdbhid(ydbhid);
                StringBuilder sb = new StringBuilder("")
                        .append("运单编号:" + ydbhid + ",细则号:" + receipt.getYdxzh())
                        .append("修改成本信息,原始成本信息为:运输成本:" + receipt.getQdCost() + ",其他成本为" + receipt.getElseCost())
                        .append(",修改后的数据为:运输成本为" + entity.getString("qdCost") + ",其他成本为" + entity.getString("elseCost"));
                exceptionLog.setOperatingContent(sb.toString());
                exceptionLogService.addExceptionLog(exceptionLog);
            } catch (Exception e) {
                throw new BusinessException("新增异常日志信息失败", e);
            }
        });
        //修改成本的逻辑: 1.修改装载表中的成本字段; 2.修改成本_H表的成本字段,成本_D表会根据作业自动更新;
        //1.更新装载清单表的成本字段
        //为了适应tms保存的格式,加一个结束时间
        entity.put("num", 1);
        bundleReceiptMapper.modifyHczzqd_sourceIncome(entity);
        //2.获取该车的成本号
        String insNo = bundleReceiptMapper.getInsNoByFchrqAndChxh(entity);
        //3.根据成本号来对_H
        entity.put("insNo", insNo);
        entity.put("tCost", entity.getBigDecimal("qdCost").add(entity.getBigDecimal("elseCost")));
        adjunctSomethingService.updateIncome_HByInsNo(entity);

        return JsonResult.getConveyResult("200", "成本修改成功");
    }

    @Override
    public JsonResult createIncome(RequestJsonEntity entity) throws ParseException {
        Integer rightNum = rightMapper.getRightNum(AuthorticationConstant.HCZZQD, entity.getString("account"));
        if (rightNum == null || rightNum < 1)
            return JsonResult.getConveyResult("400", AuthorticationConstant.MESSAGE);
        String company = entity.getString("company");

        Date fchrq = entity.getDate("fchrq");
        entity.put("fchrq", CommonDateUtil.DateToString(fchrq, "yyyy-MM-dd HH:mm:ss"));
        entity.put("end", CommonDateUtil.DateToString(new Date(fchrq.getTime() + 1000), "yyyy-MM-dd HH:mm:ss"));

        List<BundleReceipt> list = bundleReceiptMapper.getBundleReceiptByChxhAndFchrq
                (entity.getString("chxh"), entity.getString("fchrq"), entity.getString("end"));

        if (CollectionUtils.isEmpty(list))
            return JsonResult.getConveyResult("400", "该装载有异常,请联系管理员");
        BundleReceipt bundlereceipt = list.get(0);
        TransportOrder order = transportOrderMapper.getTransportOrderByYdbhid(bundlereceipt.getYdbhid());
        //提货的随便进行
        if (bundlereceipt.getiType() == 2 && !company.equals(order.getFazhan())) {    //提货,并且是当前登陆公司可以装载
            return JsonResult.getConveyResult("400", "提货装载请在运单的始发站做成本");
        }
        if (bundlereceipt.getiType() != 2 && !company.equals(bundlereceipt.getFazhan()))
            return JsonResult.getConveyResult("400", "不能操作其他公司的装载成本");

        entity.put("num", 1);
        bundleReceiptMapper.modifyHczzqd_sourceIncome(entity);
        entity.put("tCost", entity.getBigDecimal("qdCost").add(entity.getBigDecimal("elseCost")));

        String insNo = getIncomecostNo();
        Integer insXh = 0;
        entity.put("insNo", insNo);
        for (BundleReceipt receipt : list) {
            //jirabug:924
            receipt.setQdCost(entity.getBigDecimal("qdCost"));
            receipt.setElseCost(entity.getBigDecimal("elseCost"));
            entity.put("insXh", ++insXh);
            entity.put("receipt", receipt);
            receipt.setWxItem(entity.getString("wxItem"));
            entity.put("yshhm", transportOrderService.getTransportOrderByYdbhid(receipt.getYdbhid()).getYshhm());
            bundleReceiptMapper.insertIntoIncome_D(entity);
        }
        bundleReceiptMapper.insertIntoIncome_H(entity);
        return JsonResult.getConveyResult("200", "成本保存成功");
    }

    @Override
    public List<BundleReceipt> queryBundleReceiptByYdbhidTime(String ydbhid, String zhchrq, String chxh) {
        return bundleReceiptMapper.queryBundleReceiptByYdbhidTime(ydbhid, zhchrq, chxh);
    }

    @Override
    public List<HashMap<String, Object>> searchBundleReceiptForPrint(BundleReceiptSearch receiptsearch) {
        List<HashMap<String, Object>> list = bundleReceiptMapper.searchBundleReceiptForPrint(receiptsearch);
        Map<String, String> empMap = new HashMap<>();    //创建一个临时的缓存,减少数据库查询 工号:姓名
        empMap.put("", "");
        empMap.put(null, "");        //没有工号就没有姓名
        for (HashMap<String, Object> submap : list) {
            String grid =String.valueOf(Optional.ofNullable(submap.get("grid")).orElse(""));
            String dhgrid = String.valueOf(Optional.ofNullable(submap.get("dhgrid")).orElse(""));
            if (!empMap.containsKey(grid)) {
                Employee emp = adjunctSomethingService.getEmployeeByNumber(grid);
                empMap.put(grid, emp == null ? "" : emp.getEmplyeeName());
            }
            if (!empMap.containsKey(dhgrid)) {
                Employee emp = adjunctSomethingService.getEmployeeByNumber(dhgrid);
                empMap.put(dhgrid, emp == null ? "" : emp.getEmplyeeName());
            }
            submap.put("grid", empMap.get(grid));
            submap.put("dhgrid", empMap.get(dhgrid));
        }
        return list;
    }

}




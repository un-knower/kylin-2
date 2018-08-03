var totalCostForFinance = {
//	zhjxzyf	: 0,	//叉车费单价
//	qchl : 0,		//轻货叉车量
//	zchl : 0,		//重货叉车量
//	jshhj : 0,      //是否取整
//	ydly : 0,       //?
//	baolu : 0,      //保率
//	tbje  : 0,      //投保金额
//	zhzhxfdj : 0,   //重货装卸单价
//	zzxl : 0,       //重货装卸量
//	qhzhxfdj : 0,   //轻货装载单价
//	qzxl : 0,   	//轻货装卸量
//	jffs : 0,       //计费方式
//	zhl : 0,        //重量
//	jianshu : 0,    //件数
//	ajyj : 0,      //按件运价
//	tiji : 0,      //体积
//	zhhyj : 0,     //重货运价
//	qhyj : 0,      //轻货运价
//	dsk : 0,       //代收款
//	bdf : 0,       //办单费
//	bxf : 0,
// loadingfee      装卸费
// totalTransport  运费合计
	calcForkliftCharge : function(jshhj,zhjxzyf,zchl,qchl) {	//叉车费计算公式
		var sum = 0.0;
		if(jshhj < 1){
			sum += Math.round(zhjxzyf * (zchl + qchl));
		}else{
			sum += zhjxzyf * (zchl + qchl);
		}
		return sum;
	},
	calcInsuranceExpense : function(ydly,baolu,tbje,jshhj) {	//保险费计算公式
		if (ydly==1) {
			if(baolu*tbje<1){
				return Math.ceil(baolu*tbje);
			}else{
				return Math.round(baolu*tbje*100)/100.00;
			}
		}else{
			if(jshhj<1){
				return Math.round(baolu * tbje)*1.00;
			}else{
				return Math.round(baolu * tbje*100)/100.00;
			}
		}
	},
	calcHeavyLoadingFee : function(jshhj,zhzhxfdj,zzxl) {	//装卸费（重）计算公式
		var sum = 0.0;
		if(jshhj < 1){
			sum += Math.round(zhzhxfdj  * zzxl);
		}else{
			sum += zhzhxfdj * zzxl;
		}
		return sum;
	},
	calcLightLoadingFee : function(jshhj,qhzhxfdj,qzxl) {	//装卸费（轻）计算公式
		var sum = 0.0;
		if(jshhj < 1){
			sum += Math.round(qhzhxfdj  * qzxl);
		}else{
			sum += qhzhxfdj * qzxl;
		}
		return sum;
	},
	calcTotalTransport : function(jshhj,jffs,zhl,zhhyj,tiji,qhyj,ajyj,jianshu) {	//运费合计
		var result = 0.0;
		if(jshhj < 1){
			if(jffs==0){
				result = zhl*1000 * zhhyj;
			}else if(jffs==1){
				result = tiji*1000 * qhyj;
			}else {
				result = ajyj*1000 * jianshu;
			}
			result =  Math.round(result/1000);
		}else{
			if(jffs==0){
				result = zhl * 1000 * zhhyj;
			}else if(jffs==1){
				result = tiji * 1000 * qhyj;
			}else {
				result = ajyj * 1000 * jianshu;
			}
			result =  Math.round(result/10)/100.00;
		}
		return result;//-plus
	},
	calcSumTransportFees : function(jshhj,forklift,loadingfee,bxf,shshmf,shmshhf,qtfy,totalTransport,bdf) {	//运杂费合计
		var sum = forklift +  bxf + loadingfee;
		if (jshhj < 1) {
			sum += Math.floor(shshmf + shmshhf + qtfy + 0.5);
		} else {
			sum += shshmf + shmshhf + qtfy;
		}
		sum += bdf + totalTransport;
		return sum;
	},
	calcTransportationAcceptanceSheet :function(calcSumTransportFees,dsk){//运杂费合计 +dsk =合计费用(总金额大写)
		return Math.round((calcSumTransportFees+dsk)*100)/100;
	}
}
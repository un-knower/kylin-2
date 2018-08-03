$(".quanxuan").click(function(){//给全选按钮加上点击事件
        var xz = $(this).prop("checked");//判断全选按钮的选中状态
        var ck = $(".qx").prop("checked",xz);  //让class名为qx的选项的选中状态和全选按钮的选中状态一致。  
        })
//分页
window.onload=function(){
  var oUl=document.getElementById('list');
  var aLi=oUl.getElementsByTagName('span');
  for(var i=0;i<aLi.length;i++){
   fn1(aLi[i]);
  }
  function fn1(aLi){
   var aBtn=aLi.getElementsByTagName('input');
   var aStrong=aLi.getElementsByTagName('strong')[0];
   var aEm=aLi.getElementsByTagName('em')[0];
   var aSpan=aLi.getElementsByTagName('span')[0];
   var num=Number(aStrong.innerHTML); //aStrong.innerHTML='0'
// var price=parseFloat(aEm.innerHTML);//aEm.innerHTML='12.9元'
   aBtn[0].onclick=function(){
   if(num>1)
   {
   num--;
   aStrong.innerHTML=num;
// aSpan.innerHTML=(num*price).toFixed(1)+'元'
   }
   }
   aBtn[1].onclick=function(){
   num++;
   aStrong.innerHTML=num;
// aSpan.innerHTML=(num*price).toFixed(1)+'元'
   }
  }
  }
$(function(){
	//列表
	pageControl();
	wrapWidth();
	$(window).resize(function(){
		pageControl();
		wrapWidth();
	});
	toggleMenu();
  
})

function pageControl(){
	    var Wdocument=$(window).width();
	    var Hdocument=$(window).height();
	    $('#navigation').height(Hdocument);
}

//控制页面布局，不要删；
function wrapWidth(){
	var col25 = $('.col-25').outerWidth()-92;
	var col50 = $('.col-50').outerWidth()-92;
	var col100 = $('.col-100').outerWidth()-92;
	var spanAddress = $('.col-50').outerWidth()-98;
	$('.col-25>.txt').css('width',col25);
	$('.col-50>.txt').css('width',col50);
	$('.col-100>.txt').css('width',col100);
	$('.col-50 .city-picker-span').css('width',spanAddress);
}

function toggleMenu(){
	$('.parent-li .parent-menu').click(function(){
		var $li = $(this).parent();
		var $selected = $li.hasClass('selected');
		if($selected){
			$li.removeClass('selected');
			$li.find('.sub-menu').stop().slideUp();
		}else{
			$li.addClass('selected').siblings().removeClass('selected');
			$li.find('.sub-menu').stop().slideDown();
			$li.siblings().find('.sub-menu').stop().slideUp();
		}

    })
}

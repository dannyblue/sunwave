function fullscreen(dataType){
            var $overlayPanel = $("#overlayPanel"),
            str = $overlayPanel.html();
            $("[data-info]").off();
            $("[data-info]").on("click",function(){
            $("#overlayPanel").remove();
            $("body").prepend("<div id='overlayPanel'>"+str+"</div>");
            if(dataType==1){  
                        $("#overl ayPanel").attr('data-panelType',1); 
                        $('[data-func="cancel"]').attr('onclick','cancelAlerm(1)');
                        $('[data-panel="name"]').html('非法手机');
                        $('[data-form]').attr('data-form',$(this).data('did'));
                        $('[data-panel="icon"]').attr('class','iconsp-mobile');
                        $('[data-panel="phoneNo"]').html($(this).data('loginimsi'));
                        $('[data-panel="cardNo"]').parent().hasClass('disno')?$('[data-panel="cardNo"]').parent().removeClass('disno'):'';
                        $('[data-panel="cardNo"]').html($(this).data('loginimei'));
                        $('[data-panel="area"]').html($(this).data('posinfo'));
                        $('[data-panel="date"]').html($(this).data('logindate'));
                        $('[data-panel="operator"]').html(operatorType($(this).data('operator')));
                        $('[data-panel="callPhone"]').parent().hasClass('disno')?'':$('[data-panel="callPhone"]').parent().addClass('disno');
                        $('[data-panel="content"]').parents(".row-fluid").hasClass('disno')?'':$('[data-panel="content"]').parents(".row-fluid").addClass('disno');
            }else if (dataType ==2) {
                        $("#overlayPanel").attr('data-panelType',2); 
                        $('[data-func="cancel"]').attr('onclick','cancelAlerm(2)');
                        $('[data-form]').attr('data-form',$(this).data('did'));
                        $('[data-panel="icon"]').attr('class','icon-envelope');
                        $('[data-panel="name"]').html('非法短信');
                        $('[data-panel="phoneNo"]').html($(this).data('smsimsi'));
                        $('[data-panel="cardNo"]').parent().hasClass('disno')?'':$('[data-panel="cardNo"]').parent().addClass('disno');
                        $('[data-panel="area"]').html($(this).data('posinfo'));
                        $('[data-panel="date"]').html($(this).data('smsdate'));
                        $('[data-panel="operator"]').html(operatorType($(this).data('operator')));
                        if($('[data-panel="callPhone"]').parent().hasClass('disno')){
                                $('[data-panel="callPhone"]').parent().removeClass('disno');
                        }
                        $('[data-panel="callPhone"]').html($(this).data('smsnum'));
                        $('[data-panel="content"]').parents(".row-fluid").hasClass('disno')?$('[data-panel="content"]').parents(".row-fluid").removeClass('disno'):'';
                        $('[data-panel="content"]').html($(this).data('msgcontent'));
            }else if(dataType ==3){
                        $("#overlayPanel").attr('data-panelType',3); 
                        $('[data-func="cancel"]').attr('onclick','cancelAlerm(3)');
                        $('[data-form]').attr('data-form',$(this).data('did'));
                        $('[data-panel="icon"]').attr('class','iconsp-call');
                        $('[data-panel="name"]').html('非法通话');
                        $('[data-panel="phoneNo"]').html($(this).data('callimsi'));
                        $('[data-panel="cardNo"]').parent().hasClass('disno')?'':$('[data-panel="cardNo"]').parent().addClass('disno');
                        $('[data-panel="area"]').html($(this).data('posinfo'));
                        $('[data-panel="date"]').html($(this).data('calldate'));
                        $('[data-panel="operator"]').html(operatorType($(this).data('operator')));
                        $('[data-panel="callPhone"]').parent().hasClass('disno')?$('[data-panel="callPhone"]').parent().removeClass('disno'):'';
                        $('[data-panel="callPhone"]').html($(this).data('callnum'));
                        $('[data-panel="content"]').parents(".row-fluid").hasClass('disno')?'':$('[data-panel="content"]').parents(".row-fluid").addClass('disno');
            }else{
                alert('无内容');
            };


        //$(".caption").text(title);
        $("#overlayPanel").fadeIn(100,function(){
            $(this).addClass("expand");
        });
        $("#mainPanel").addClass("narrow");
        setTimeout(function(){
            $("#panelBox").addClass("expand");
        },200)
        $(".delete").on("click",function(){
            $("#overlayPanel").removeClass("expand");
            setTimeout(function(){
                $("#overlayPanel").addClass("mini");
            },500);
            $("#mainPanel").removeClass("narrow").addClass("expand");
        })
       
    });
}

function cancelAlerm(dataType){
            var count= 0,str ='',dataId=0,count1=0;
            if(dataType==1){
                        str='WARN_LOGIN';
            }else if(dataType==2){
                        str='WARN_SMS'
            }else if(dataType==3){
                        str='WARN_CALL';
            }else{
                        alert('无内容');
            }
            count = $("[data-count="+ str+"]").html();
            count--;
            count1 = $('[data-value="未处理告警"]').html();
            count1--;
            $("[data-count="+ str+"]").html(count);
             $('[data-value="未处理告警"]').html(count1);
            dataId = $("[data-form]").data('form');
            var $li = $("span[data-count="+ str+"]").parent().parent().find("li[data-did="+dataId+"]");
            if( $li.siblings('.disno').length!=0){
                $li.siblings('.disno')[0].className='';
            }
            $li.remove();
            //删除行内的数据
            if(count ==0){
            //1.删除圆圈 2.删除样式
                        $("span[data-count="+ str+"]").empty().parent().removeClass('redBreath').parent().removeClass('redBling').find("ul").removeClass('dropdown-menu').empty();
            }

}

/*$("[data-name]").on("click",function(){
    var title = $(this).data("name");
    $("#unitsPanel").remove();
    $("body").prepend("<div id='unitsPanel'>"+str2+"</div>");
    $(".caption").text(title);
    $("#unitsPanel").fadeIn(100,function(){
        $(this).addClass("expand");
    });
    $("#mainPanel").addClass("narrow");
    setTimeout(function(){
        $("#unitsBox").addClass("expand");
    },200);
    $(".delete").on("click",function(){
        $("#unitsPanel").removeClass("expand");
        setTimeout(function(){
            $("#unitsPanel").addClass("mini");
        },500);
        $("#mainPanel").removeClass("narrow").addClass("expand");
    })
});*/
var dataAttr={};
$("[data-optype]").on("click",function(){
		var val = $(this).data('optype');
		var li = $(this).parent('li');
		dataAttr.otype = val;
		dataAttr.info=li.data('info');
		dataAttr.did=li.data('did');
		dataAttr.imsi=li.data('imsi');
		dataAttr.date=li.data('date');
		dataAttr.posinfo=li.data('posinfo');
		dataAttr.operator=li.data('operator');
		dataAttr.name=li.data('name');
		dataAttr.loginimei=li.data('loginimei');
		dataAttr.targnum=li.data('targnum');
		dataAttr.content=li.data('content');
		$("[data-display='true']").hasClass("disno")?$("[data-display='true']").removeClass("disno"):"";
});

$('#myModal').on('show', function () {
		var val = dataAttr.otype;
		$('[data-panel="phoneNo"]').html(dataAttr.imsi);  
		$('[data-panel="area"]').html(dataAttr.posinfo);
		$('[data-panel="date"]').html(dataAttr.date);
		$('[data-panel="operator"]').html(operatorType(dataAttr.operator));
		$("#relieveReason").val("");
		$("#relievePassword").val("");
		if(val==1){
			$('#myModalLabel').html('非法手机');
            $('[data-panel="cardNo"]').parent().hasClass('disno')?$('[data-panel="cardNo"]').parent().removeClass('disno'):'';
			$('[data-panel="cardNo"]').html(dataAttr.loginimei);
			$('[data-panel="callPhone"]').parent().hasClass('disno')?'':$('[data-panel="callPhone"]').parent().addClass('disno');
            $('[data-panel="content"]').parent().hasClass('disno')?'':$('[data-panel="content"]').parent().addClass('disno');
		}else if(val ==2){
			$('#myModalLabel').html('非法通话');
			$('[data-panel="cardNo"]').parent().hasClass('disno')?'':$('[data-panel="cardNo"]').parent().addClass('disno');
	        $('[data-panel="callPhone"]').parent().hasClass('disno')?$('[data-panel="callPhone"]').parent().removeClass('disno'):'';
    	    $('[data-panel="callPhone"]').html(dataAttr.targnum);
            $('[data-panel="content"]').parent().hasClass('disno')?'':$('[data-panel="content"]').parent().addClass('disno');    
		}else if(val==3){
			$('#myModalLabel').html('非法短信');
			$('[data-panel="cardNo"]').parent().hasClass('disno')?'':$('[data-panel="cardNo"]').parent().addClass('disno');
			$('[data-panel="callPhone"]').parent().hasClass('disno')?$('[data-panel="callPhone"]').parent().removeClass('disno'):'';
            $('[data-panel="callPhone"]').html(dataAttr.targnum);
            $('[data-panel="content"]').parent().hasClass('disno')?$('[data-panel="content"]').parent().removeClass('disno'):'';
            $('[data-panel="content"]').html(dataAttr.content);
		}else{
           	$("[data-display='true']").hasClass("disno")?"":$("[data-display='true']").addClass("disno");
        };
});
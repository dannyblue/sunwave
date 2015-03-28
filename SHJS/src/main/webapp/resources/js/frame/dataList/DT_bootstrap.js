/* Set the defaults for DataTables initialisation */
/*<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>*/
$.extend( true, $.fn.dataTable.defaults, {
	"sDom": "t<'row-fluid'<'span6'i><'span6'p>>",
	"sPaginationType": "bootstrap",
	"oLanguage": {
		"sLengthMenu": "_MENU_ records per page"
	}
} );


/* Default class modification */
$.extend( $.fn.dataTableExt.oStdClasses, {
	"sWrapper": "dataTables_wrapper form-inline"
} );


/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{

	return {
		"iStart":         oSettings._iDisplayStart,
		"iEnd":           oSettings.fnDisplayEnd(),
		"iLength":        oSettings._iDisplayLength,
		"iTotal":         oSettings.fnRecordsTotal(),
		"iFilteredTotal": oSettings.fnRecordsDisplay(),
		"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
		"iTotalPages":    Math.ceil( $("#"+oSettings.sTableId+"").data("countsize")/ oSettings._iDisplayLength ),
		"resultList":     oSettings.resultList,
		"moble":		  oSettings.moble
	};
};
var ffllaagg = true;
var reBindCheck = function(){
    $(".checker").on("click", function () {
        var $span = $(this).find("span");
        $span.hasClass("checked")?$span.removeClass("checked"):$span.addClass("checked");
    });
};
/* Bootstrap style pagination control */
$.extend( $.fn.dataTableExt.oPagination, {
	"bootstrap": {
		"fnInit": function( oSettings, nPaging, fnDraw ) {
            var oLang = oSettings.oLanguage.oPaginate;
            var startDataNum,endDataNum;
            
            /*初始化的数据源*/
            var oPaging = oSettings.oInstance.fnPagingInfo();
            var myDataList = {};
            var fnClickHandler = function (e) {
                e.preventDefault();
                $("tr.add").remove();
                $(".checked").removeClass("checked");
                $(".row-details-open").removeClass("row-details-open").addClass(" row-details-close");
                var pageNow = parseInt($("#" + oSettings.sTableId + "_paginate .active>a").html());
                var pageTotal = Math.ceil($("#" + oSettings.sTableId).data("countsize") / oPaging.iLength);
                var $search = $("#searchPanel");
                var $th = $("#" + oSettings.sTableId + " th");
                var thName = new Array();
                var rtpye = new Array();
                var pageList = $("#" + oSettings.sTableId + "_paginate");
                var prev = pageList.find(".prev");
                var next = pageList.find(".next");
                var a = $search.find("input[type='hidden']"), str = '';
                for (var i = 1; i < $th.length; i++) {
                    thName.push($th[i].attributes["name"].value);
                    rtpye.push($th[i].dataset.rtype);
                }
                for (var i = 0; i < a.length; i++) {
                    str += "&" + a[i].name + "=" + a[i].value;
                }
                if (e.data.action == 'next') {
                	if(ffllaagg == true && pageNow < 5){
                        oSettings.oApi._fnPageChange(oSettings, e.data.action);
                        fnDraw(oSettings);
                    }else{
                        var dataStart = pageNow*10,dataEnd;
                        ffllaagg = false;
                        var $tr = $("#tbody").find("tr").not(".add");
                        if(oSettings.resultList[dataStart] == null){
                            //如果数据没缓存则开始请求
                           $.ajax({
                                type:"get",
                                url: $("#" + oSettings.sTableId + "").data("url") + "?pagination.start=" + pageNow * 10 + "&pagination.pageSize=10&pagination.jsonPageNo=1&pagination.pageNo=" + (pageNow + 1) + str,
                                success: function (data) {
                                    //请求完成,将数据插入缓存数组
                                    dataEnd = pageNow*10 + data.resultList.length;
                                    for(var i = 0 ; i < data.resultList.length; i++){
                                        oSettings.resultList[dataStart+i] = data.resultList[i];
                                    }
                                    //获得数据后开始初始化界面
                                    for (var i = dataStart,k = 0 ; i < dataEnd,k<data.resultList.length;i++,k++) {
                                        var $td = $tr[k].children;
                                        for (var j = 1; j < $td.length; j++) {
                                        	var tdWidth = $td[j].style.width;
                                        	$td[j].children[0].style.width = tdWidth;
                                            $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("oSettings.resultList[" + i + "]." + thName[j - 1]));
                                            
                                        }
                                    }
                                    if(data.resultList.length < 10){
                                        for(var i = data.resultList.length;i < 10;i++){
                                            $tr[i].className = 'trHide';
                                        }
                                    }
                                    $('#sample_1').off('click', ' tbody td .row-details');
                                    reBindCheck();
                                    $('#sample_1').on('click', ' tbody td .row-details', function () {
                                        if ($(this).hasClass("row-details-open")) {
                                            $(this).addClass("row-details-close").removeClass("row-details-open");
                                            $(this).parents("tr").next("tr").remove();
                                        }else{
                                            $(this).addClass("row-details-open").removeClass("row-details-close");
                                            var num;
                                            num = $("tr").not(".add").index($(this).parents("tr"));
                                            var pageActive = parseInt($("#sample_1_paginate").find(".active").text());
                                            var sOut = '<tr class="details add"><td class="details"colspan="'+ $("#thead").find("th").length+'"><div class="row-fluid">';
                                            var rows = Math.ceil(oSettings.moble.length/3),count = 0;
                                            for(var i = 0 ; i < rows,count<oSettings.moble.length; i++){
                                                /*详细信息展示*/
                                                for(var j = 0;j<3 && count<oSettings.moble.length;j++){
                                                    if( j == 0){
                                                        sOut += '<div class="span12">';
                                                    }
                                                    count++;
                                                    sOut +='<div class="span4"><div class="span3">'+ oSettings.moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(oSettings.moble[3*i+j].rtype,eval("oSettings.resultList["+(num-1+(pageActive-1)*10)+"]."+oSettings.moble[3*i+j].name)) +'</div></div>';
                                                    if(j==2){
                                                        sOut += '</div>';
                                                    }
                                                }

                                            }
                                            sOut += '</div></td></tr>';
                                            $(this).parents("tr").after(sOut);
                                        }
                                    });
                                }
                           });
                        }else{
                            dataEnd = (oSettings.resultList.length - dataStart) >= 10 ? dataEnd = dataStart + 10:dataEnd = oSettings.resultList.length;
                            //读取缓存数据

                            for (var i = dataStart,k = 0 ; i < dataEnd,k<(dataEnd - dataStart);i++,k++) {
                                var $td = $tr[k].children;
                                for (var j = 1; j < $td.length; j++) {
                                	var tdWidth = $td[j].style.width;
                                	$td[j].children[0].style.width = tdWidth;
                                    $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("oSettings.resultList[" + i + "]." + thName[j - 1]));
                                }
                            }
                            if(dataEnd - dataStart <10){
                                for(var i = dataEnd - dataStart;i < 10;i++){
                                    $tr[i].className = 'trHide';
                                }
                            }
                            $('#sample_1').off('click', ' tbody td .row-details');
                            reBindCheck();
                            $('#sample_1').on('click', ' tbody td .row-details', function () {
                                if ($(this).hasClass("row-details-open")) {
                                    $(this).addClass("row-details-close").removeClass("row-details-open");
                                    $(this).parents("tr").next("tr").remove();
                                }else{
                                    $(this).addClass("row-details-open").removeClass("row-details-close");
                                    var num;
                                    num = $("tr").not(".add").index($(this).parents("tr"));
                                    var sOut = '<tr class="details add"><td class="details"colspan="'+ $("#thead").find("th").length+'"><div class="row-fluid">';
                                    var rows = Math.ceil(oSettings.moble.length/3),count = 0;
                                    for(var i = 0 ; i < rows,count<oSettings.moble.length; i++){
                                        /*详细信息展示*/
                                        for(var j = 0;j<3 && count<oSettings.moble.length;j++){
                                            if( j == 0){
                                                sOut += '<div class="span12">';
                                            }
                                            count++;
                                            sOut +='<div class="span4"><div class="span3">'+ oSettings.moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(oSettings.moble[3*i+j].rtype,eval("oSettings.resultList["+(num-1)+"]."+oSettings.moble[3*i+j].name)) +'</div></div>';
                                            if(j==2){
                                                sOut += '</div>';
                                            }
                                        }

                                    }
                                    sOut += '</div></td></tr>';
                                    $(this).parents("tr").after(sOut);
                                }
                            });
                        }
                       //页码渲染
                       
                       //分页逻辑开始
                       if(pageNow < (pageTotal-2) ){
                           pageList.find("li").not($("li.prev")).not($("li.next")).each(function () {
                               if(pageNow <3){
                                   pageList.find(".active").removeClass("active").next("li").addClass("active");
                                   prev.hasClass("disabled") ? prev.removeClass("disabled") :"";
                                   return false;
                               }else{
                                   var _inner = parseInt($(this).find("a").html());
                                   $(this).find("a").html(_inner + 1);
                               }

                           })
                       }else if(pageNow >= (pageTotal-2 ) && pageNow < pageTotal) {
                           pageList.find(".active").removeClass("active").next("li").addClass("active");
                           if(pageNow ==(pageTotal-1)){
                               next.hasClass("disabled") ? "" : next.addClass("disabled");
                           }
                       }
                    }
                }else if(e.data.action == "previous"){
                    var $tr = $("#tbody").find("tr").not(".add");
                    $tr.removeClass("trHide");
                    if(ffllaagg == true && pageNow <= 5){
                        oSettings.oApi._fnPageChange(oSettings, e.data.action);
                        fnDraw(oSettings);
                    }else{
                        var dataStart = (pageNow-2)*10,dataEnd;
                        ffllaagg = false;
                        if(oSettings.resultList[dataStart] == null && pageNow > 5){
                            $.ajax({
                                type: "get",
                                url: $("#" + oSettings.sTableId + "").data("url") + "?pagination.start=" + (pageNow - 2) * 10 + "&pagination.pageSize=10&pagination.jsonPageNo=1&pagination.pageNo=" + (pageNow - 1) + str,
                                success: function (data) {
                                    //请求完成,将数据插入缓存数组
                                    dataEnd = dataStart + data.resultList.length;
                                    for(var i = 0 ; i < data.resultList.length; i++){
                                        oSettings.resultList[dataStart+i] = data.resultList[i];
                                    }
                                    //获得数据后开始初始化界面
                                    for (var i = dataStart,k = 0 ; i < dataEnd,k<data.resultList.length;i++,k++) {
                                        var $td = $tr[k].children;
                                        for (var j = 1; j < $td.length; j++) {
                                        	var tdWidth = $td[j].style.width;
                                        	$td[j].children[0].style.width = tdWidth;
                                            $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("oSettings.resultList[" + i + "]." + thName[j - 1]));
                                        }
                                    }

                                }
                            });
                        }else{
                            dataEnd = dataStart + 10;
                            for (var i = dataStart,k = 0 ; i < dataEnd, k<(dataEnd - dataStart);i++,k++) {
                                var $td = $tr[k].children;
                                for (var j = 1; j < $td.length; j++) {
                                	var tdWidth = $td[j].style.width;
                                	$td[j].children[0].style.width = tdWidth;
                                   $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("oSettings.resultList[" + i + "]." + thName[j - 1]));
                                }
                            }
                        }

                        $('#sample_1').off('click', ' tbody td .row-details');
                        reBindCheck();
                        $('#sample_1').on('click', ' tbody td .row-details', function () {
                            if ($(this).hasClass("row-details-open")) {
                                $(this).addClass("row-details-close").removeClass("row-details-open");
                                $(this).parents("tr").next("tr").remove();
                            }else{
                                $(this).addClass("row-details-open").removeClass("row-details-close");
                                var num;
                                num = $("tr").not(".add").index($(this).parents("tr"));
                                var pageActive = parseInt($("#sample_1_paginate").find(".active").text());
                                var sOut = '<tr class="details add"><td class="details"colspan="'+ $("#thead").find("th").length+'"><div class="row-fluid">';
                                var rows = Math.ceil(oSettings.moble.length/3),count = 0;
                                for(var i = 0 ; i < rows,count<oSettings.moble.length; i++){
                                    /*详细信息展示*/
                                    for(var j = 0;j<3 && count<oSettings.moble.length;j++){
                                        if( j == 0){
                                            sOut += '<div class="span12">';
                                        }
                                        count++;
                                        sOut +='<div class="span4"><div class="span3">'+ oSettings.moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(oSettings.moble[3*i+j].rtype,eval("oSettings.resultList["+(num-1+(pageActive-1)*10)+"]."+oSettings.moble[3*i+j].name)) +'</div></div>';
                                        if(j==2){
                                            sOut += '</div>';
                                        }
                                    }

                                }
                                sOut += '</div></td></tr>';
                                $(this).parents("tr").after(sOut);
                            }
                        });
                        /*分页逻辑开始*/
                        if(pageNow > 3){
                            pageList.find("li").not($("li.prev")).not($("li.next")).each(function () {
                                if(pageNow > (pageTotal-2)){
                                    pageList.find(".active").removeClass("active").prev("li").addClass("active");
                                    next.hasClass("disabled") ? next.removeClass("disabled") :"";
                                    return false;
                                }else{
                                    var _inner = parseInt($(this).find("a").html());
                                    $(this).find("a").html(_inner - 1);
                                }
                            });
                        }else if(pageNow <=3 && pageNow>0) {
                            pageList.find(".active").removeClass("active").prev("li").addClass("active");
                            if(pageNow == 2){
                                prev.hasClass("disabled") ? "" : prev.addClass("disabled");
                            }
                        }
                        /*分页逻辑结束*/

                    }
                }
                $(".checker").on("click", function () {
                    $(this).find("span").toggleClass("checked");
                });
             };
            $(nPaging).parent().css('margin-top','14px');
            $(nPaging).addClass('pagination').append(
            		'<span class="first" id="first"><<</span>'+
                    '<ul>' +
                    '<li class="prev disabled"><a href="#"><span class="hidden-480"><</span></a></li>' +
                    '<li class="next disabled"><a href="#"><span class="hidden-480">></span></a></li>' +
                    '</ul>'+
                    '<span class="last" id="last">>></span>'
            );
            var els = $('a', nPaging);
            $(els[0]).bind('click.DT', { action: "previous" }, fnClickHandler);
            $(els[1]).bind('click.DT', { action: "next" }, fnClickHandler);
		},
		'fnUpdate': function ( oSettings, fnDraw ) {

			var $thead = $("#thead"),
		   	$tbody = $("#tbody"),
		   	arrayWidth = [];
		   	$thead.attr("style","display:block");
		   	$tbody.attr("style","display:block;");
            $thead.find('th').each(function(){
		   		arrayWidth.push($(this).width());
		   	});
		   function perload(){

	   		$tbody.find('tr').each(function(){
		   		var $td = $(this).find("td");
		   		for(var i = 0 ; i < arrayWidth.length; i++){
		   			$td.eq(i).css("width",parseInt(arrayWidth[i])+"px");
		   		}
	   		});
           }
		   perload();
            var iListLength = Math.ceil(oSettings.resultList.length/10);
			var oPaging = oSettings.oInstance.fnPagingInfo();
			var an = oSettings.aanFeatures.p;
			var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

			if ( oPaging.iTotalPages < iListLength) {
				iStart = 1;
			}
			else if ( oPaging.iPage <= iHalf ) {
				iStart = 1;
				iEnd = iListLength;
			} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
				iStart = oPaging.iTotalPages - iListLength + 1;
				iEnd = oPaging.iTotalPages;

			} else {
				iStart = oPaging.iPage - iHalf + 1;
				iEnd = iStart + iListLength - 1;

			}

			for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
				// Remove the middle elements
				$('li:gt(0)', an[i]).filter(':not(:last)').remove();

				// Add the new list items and their event handlers
				for ( j=iStart ; j<=iEnd ; j++ ) {
					sClass = (j==oPaging.iPage+1) ? 'class="active "' : '';
					$('<li '+sClass+'><a href="#">'+j+'</a></li>')
						.insertBefore( $('li:last', an[i])[0] )
						.bind('click', function (e) {
							e.preventDefault();
                            var rightNow = $(this);
                            var pageNow = parseInt(rightNow.text());
                            var dataStart =  (pageNow-1)*10;
                            var dataEnd;
                            var $tr = $("#tbody").find("tr").not(".add");
                            $tr.removeClass("trHide");
                            if(ffllaagg == true && pageNow <= 5){
                                oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                                fnDraw( oSettings );
                                reBindCheck();
                            }else{
                                var pageTotal = Math.ceil($("#"+oSettings.sTableId).data("countsize")/oPaging.iLength);
                                var $search = $("#searchPanel");
                                var $th = $("#"+oSettings.sTableId+" th");
                                var thName = new Array();
                                var rtpye = new Array();
                                var pageList = $("#"+oSettings.sTableId+"_paginate");
                                var a = $search.find("input[type='hidden']"),
                                    str = '';
                                for(var i = 1; i<$th.length ; i++){
                                    thName.push($th[i].attributes["name"].value);
                                    rtpye.push($th[i].dataset.rtype);
                                }
                                for(var i = 0; i< a.length ;i++){
                                    str += "&"+a[i].name +"="+ a[i].value;
                                }
                                $("tr.add").remove();
                                $(".checked").removeClass("checked");
                                $(".row-details-open").removeClass("row-details-open").addClass(" row-details-close");
                                if(oSettings.resultList[dataStart] ==null ){
                                    $.ajax({
                                        type : "get",
                                        url: $("#"+ oSettings.sTableId+ "").data("url")+ "?pagination.start="+ (pageNow-1)* 10+ "&pagination.pageSize=10&pagination.jsonPageNo=1&pagination.pageNo="+ pageNow + str,
                                        success : function(data) {
                                            /*表单内容渲染开始*/
                                            /*1,数据绑定*/
                                            dataEnd = dataStart + data.resultList.length;
                                            for(var i = 0 ; i < data.resultList.length ; i++){
                                                oSettings.resultList[dataStart+i] = data.resultList[i];
                                            }
                                            //获得数据后开始初始化界面
                                            if(data.resultList.length < 10){
                                                for(var i = data.resultList.length;i < 10;i++){
                                                    $tr[i].className = 'trHide';
                                                }
                                            }
                                            for (var i = dataStart,k = 0 ; i < dataEnd,k<data.resultList.length;i++,k++) {
                                                var $td = $tr[k].children;
                                                for (var j = 1; j < $td.length; j++) {
                                                	var tdWidth = $td[j].style.width;
                                                	$td[j].children[0].style.width = tdWidth;
                                                    $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("oSettings.resultList[" + i + "]." + thName[j - 1]));
                                                }
                                            }
                                            ffllaagg=false;
                                            /*内容渲染结束*/

                                            /*11-14新增结束*/
                                        }
                                    });
                                }else{
                                    dataEnd = (oSettings.resultList.length - dataStart) >= 10 ? dataEnd = dataStart + 10:dataEnd = oSettings.resultList.length;
                                    //读取缓存数据
                                    if(dataEnd - dataStart <10){
                                        for(var i = dataEnd - dataStart;i < 10;i++){
                                            $tr[i].className = 'trHide';
                                        }
                                    }
                                    for (var i = dataStart,k = 0 ; i < dataEnd,k<(dataEnd - dataStart);i++,k++) {
                                        var $td = $tr[k].children;
                                        for (var j = 1; j < $td.length; j++) {
                                        	var tdWidth = $td[j].style.width;
                                        	$td[j].children[0].style.width = tdWidth;
                                            $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("oSettings.resultList[" + i + "]." + thName[j - 1]));
                                        }
                                    }

                                }

                                /*分页渲染开始*/
                                $('#sample_1').off('click', ' tbody td .row-details');
                                reBindCheck();
                                $('#sample_1').on('click', ' tbody td .row-details', function () {
                                    if ($(this).hasClass("row-details-open")) {
                                        $(this).addClass("row-details-close").removeClass("row-details-open");
                                        $(this).parents("tr").next("tr").remove();
                                    }else{
                                        $(this).addClass("row-details-open").removeClass("row-details-close");
                                        var num;
                                        num = $("tr").not(".add").index($(this).parents("tr"));
                                        var pageActive = parseInt($("#sample_1_paginate").find(".active").text());
                                        var sOut = '<tr class="details add"><td class="details"colspan="'+ $("#thead").find("th").length+'"><div class="row-fluid">';
                                        var rows = Math.ceil(oSettings.moble.length/3),count = 0;
                                        for(var i = 0 ; i < rows,count<oSettings.moble.length; i++){
                                            /*详细信息展示*/
                                            for(var j = 0;j<3 && count<oSettings.moble.length;j++){
                                                if( j == 0){
                                                    sOut += '<div class="span12">';
                                                }
                                                count++;
                                                sOut +='<div class="span4"><div class="span3">'+ oSettings.moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(oSettings.moble[3*i+j].rtype,eval("oSettings.resultList["+(num-1+(pageActive-1)*10)+"]."+oSettings.moble[3*i+j].name)) +'</div></div>';
                                                if(j==2){
                                                    sOut += '</div>';
                                                }
                                            }

                                        }
                                        sOut += '</div></td></tr>';
                                        $(this).parents("tr").after(sOut);
                                    }
                                });
                                var num_1 = parseInt(pageList.find(".active").text());//被点击时候的页码数
                                var num_2 = parseInt(rightNow.text());//被点击的页码数
                                var lis = pageList.find("li").not($("li.prev")).not($("li.next"));//除去上一页下一页的数组
                                pageList.find(".active").removeClass("active");
                                if(num_2 == 1){
                                    lis[0].className="active";
                                    pageList.find(".prev").hasClass("disabled")?"":pageList.find(".prev").addClass("disabled");
                                    for(var i = 0,j = 0;i < lis.length ;i++){
                                        lis[i].children[0].textContent= num_2 + j;
                                        j++;
                                    }
                                }else if( num_2 ==2 ){
                                    lis[1].className="active";
                                    pageList.find(".prev").removeClass("disabled");
                                    for(var i = 0,j = -1;i < lis.length ;i++){
                                        lis[i].children[0].textContent= num_2 + j;
                                        j++;
                                    }
                                }else if (num_2 == pageTotal-1){
                                    lis[3].className="active";
                                    pageList.find(".next").removeClass("disabled");
                                    for(var i = 0,j = -3;i < lis.length ;i++){
                                        lis[i].children[0].textContent= num_2 + j;
                                        j++;
                                    }
                                }else if( num_2 == pageTotal){
                                    lis[4].className="active";
                                    pageList.find(".next").hasClass("disabled")?"":pageList.find(".next").addClass("disabled")
                                    for(var i = 0,j = -4;i < lis.length ;i++){
                                        lis[i].children[0].textContent= num_2 + j;
                                        j++;
                                    }
                                }else{
                                    lis[2].className= "active";
                                    pageList.find(".prev").removeClass("disabled");
                                    pageList.find(".next").removeClass("disabled");
                                    for(var i = 0,j= -2;i < lis.length ;i++){
                                        lis[i].children[0].textContent= num_2 + j;
                                        j++;
                                    }
                                }
                            }
						} );
				}

				// Add / remove disabled classes from the static elements
				if ( oPaging.iPage === 0 ) {
					$('li:first', an[i]).addClass('disabled');
				} else {
					$('li:first', an[i]).removeClass('disabled');
				}

				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
					$('li:last', an[i]).addClass('disabled');
				} else {
					$('li:last', an[i]).removeClass('disabled');
				}

			}
		}
	}
} );


/*
 * TableTools Bootstrap compatibility
 * Required TableTools 2.1+
 */
if ( $.fn.DataTable.TableTools ) {
	// Set the classes that TableTools uses to something suitable for Bootstrap
	$.extend( true, $.fn.DataTable.TableTools.classes, {
		"container": "DTTT btn-group",
		"buttons": {
			"normal": "btn",
			"disabled": "disabled"
		},
		"collection": {
			"container": "DTTT_dropdown dropdown-menu",
			"buttons": {
				"normal": "",
				"disabled": "disabled"
			}
		},
		"print": {
			"info": "DTTT_print_info modal"
		},
		"select": {
			"row": "active"
		}
	} );

	// Have the collection use a bootstrap compatible dropdown
	$.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
		"collection": {
			"container": "ul",
			"button": "li",
			"liner": "a"
		}
	} );
}
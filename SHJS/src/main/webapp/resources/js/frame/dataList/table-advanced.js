var TableAdvanced = function (resultList,moble) {

    var initTable1 = function(resultList,moble) {

        /* Formating function for row details */
        function fnFormatDetails ( oTable, nTr )
        {
            var aData = oTable.fnGetData( nTr),
            num = $("tr").not(".add").index(nTr);
            var pageActive = parseInt($("#sample_1_paginate").find(".active").text());
            var sOut = '<div class="row-fluid">';
            var rows = Math.ceil(moble.length/3),count = 0;
            for(var i = 0 ; i < rows,count<moble.length; i++){
                /*详细信息展示*/
                for(var j = 0;j<3 && count<moble.length;j++){
                    if( j == 0){
                        sOut += '<div class="span12">';
                    }
                    count++;
                    sOut +='<div class="span4"><div class="span3">'+ moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(moble[3*i+j].rtype,eval("resultList["+(num-1+(pageActive-1)*10)+"]."+moble[3*i+j].name)) +'</div></div>';
                    if(j==2){
                        sOut += '</div>';
                    }
                }

            }
            sOut += '</div>';

            return sOut;
        }

        /*
         * Insert a 'details' column to the table
         */
        var nCloneTh = document.createElement( 'th' );
        var nCloneTd = document.createElement( 'td' );
        // nCloneTd.innerHTML = '<span class="row-details row-details-close" style="float:left"></span><label class="checkbox" style="margin-left: 10px;width: 14px;height: 14px;float: left;"><div class="checker" style="margin-top: -2px!important;"><span><input type="checkbox" value=""></span></div></label>';
        nCloneTd.innerHTML = '<label class="checkbox" style="margin-left: 10px;width: 14px;height: 14px;float: left;"><div class="checker" style="margin-top: -2px!important;"><span><input type="checkbox" value=""></span></div></label>';

        $('#sample_1 thead tr').each( function () {
            this.insertBefore( nCloneTh, this.childNodes[0] );
        } );

        $('#sample_1 tbody tr').each( function () {
            this.insertBefore(  nCloneTd.cloneNode( true ), this.childNodes[0] );
        } );

        /*
         * Initialse DataTables, with no sorting on the 'details' column
         */
        var oTable = $('#sample_1').dataTable( {
            "aoColumnDefs": [
                {"bSortable": false, "aTargets": [ 0 ] }
            ],
             "aLengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            "iDisplayLength": 10,
            "resultList":resultList,
            "moble":moble
        });

        jQuery('#sample_1_wrapper .dataTables_filter input').addClass("m-wrap small"); // modify table search input
        jQuery('#sample_1_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
        jQuery('#sample_1_wrapper .dataTables_length select').select2(); // initialzie select2 dropdown

        /* Add event listener for opening and closing details
         * Note that the indicator for showing which row is open is not controlled by DataTables,
         * rather it is done here
         */
        $('#sample_1').on('click', ' tbody td .row-details', function () {
            var nTr = $(this).parents('tr')[0];
            if ( oTable.fnIsOpen(nTr) )
            {
                /* This row is already open - close it */
                $(this).addClass("row-details-close").removeClass("row-details-open");
                oTable.fnClose( nTr );
            }
            else
            {
                /* Open this row */
                $(this).addClass("row-details-open").removeClass("row-details-close");
                oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
            }
        });
        /*Delete this row*/
        $('#sample_1').parents('.box').on('click', '[data-op]', function () {
            switch($(this).data('op')){
                case 'delete':
                    var nTr = $(this).parents('tr')[0];
                    $.ajax({
                    	url:"surveillance.Invade.remove.do",
                    	data:{
                    		ids:$(this).data('id')
                    	},
                    	success:function(v){
                    		 nTr.remove();
                    		 //维护缓存
                    	}
                    });
                    break;
                case 'empty':
                    var nTr= $('#sample_1').find('span.checked').parents('tr');
                    var ids;
                    for(var i in nTr){
                    	console.log(nTr[i].data('id'));
                    	ids+=nTr[i].data('id')+","
                    }
                    ids=ids.subString(0,ids.length-1);
                      $.ajax({
                    	url:"surveillance.Invade.remove.do",
                    	data:{
                    		ids:ids
                    	},
                    	success:function(v){
                    		 nTr.remove();
                    	}
                    });
                    break;
                case 'refresh':
                    alert("刷新");
                    break;
                default:
                    alert(312);
            }
       });
    };



    return {
        //main function to initiate the module
        init: function (resultList,moble) {
            if (!jQuery().dataTable) {
                return;
            }
            initTable1(resultList,moble);

            var $th = $("#thead").find("th");
            var thName = new Array();
            var rtpye = new Array();
            var pageList = $("#sample_1_paginate");
            for (var i = 1; i < $th.length; i++) {
                thName.push($th[i].attributes["name"].value);
                 rtpye.push($th[i].dataset.rtype);
            }
            $th.off("click");
            $("#first").on("click",function(e){
                e.preventDefault();
                $("tr.add").remove();
                $(".checked").removeClass("checked");
                $(".row-details-open").removeClass("row-details-open").addClass(" row-details-close");
                $(".checker").on("click", function () {
                    $(this).find("span").hasClass("checked")?$(this).find("span").removeClass("checked"):$(this).find("span").addClass("checked");
                });
                /*取resultList中首个对象*/
                var dataStart = 0,dataEnd;
                dataEnd = (resultList.length - dataStart) >= 10 ? dataEnd = dataStart + 10:dataEnd = resultList.length;
                //读取缓存数据
                var $tr = $("#tbody").find("tr").not(".add");
                $tr.removeClass("trHide");
                for (var i = dataStart,k = 0 ; i < dataEnd,k<(dataEnd - dataStart);i++,k++) {
                    var $td = $tr[k].children;
                    for (var j = 1; j < $td.length; j++) {
                        var tdWidth = $td[j].style.width;
                        $td[j].children[0].style.width = tdWidth;
                        $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("resultList[" + i + "]." + thName[j - 1]));
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
                        var rows = Math.ceil(moble.length/3),count = 0;
                        for(var i = 0 ; i < rows,count<moble.length; i++){
                            /*详细信息展示*/
                            for(var j = 0;j<3 && count<moble.length;j++){
                                if( j == 0){
                                    sOut += '<div class="span12">';
                                }
                                count++;
                                sOut +='<div class="span4"><div class="span3">'+ moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(moble[3*i+j].rtype,eval("resultList["+(num-1+(pageActive-1)*10)+"]."+moble[3*i+j].name)) +'</div></div>';
                                if(j==2){
                                    sOut += '</div>';
                                }
                            }
                        }
                        sOut += '</div></td></tr>';
                        $(this).parents("tr").after(sOut);
                    }
                });
                //页码渲染
                var _inner = 1;
                pageList.find("li").not($("li.prev")).not($("li.next")).each(function () {
                    $(this).find("a").html(_inner);
                    _inner++;
                });
                pageList.find(".active").removeClass("active");
                pageList.find("li").not($(".prev")).not($(".next")).first("li").addClass("active");
                pageList.find(".prev").hasClass("disabled")?"": pageList.find(".prev").addClass("disabled");
                pageList.find(".next").hasClass("disabled")?pageList.find(".next").removeClass("disabled"):"";
            });
            $("#last").on("click",function(e){
                e.preventDefault();
                $("tr.add").remove();
                $(".checked").removeClass("checked");
                $(".row-details-open").removeClass("row-details-open").addClass(" row-details-close");
                $(".checker").on("click", function () {
                    $(this).find("span").hasClass("checked")?$(this).find("span").removeClass("checked"):$(this).find("span").addClass("checked");
                });
                /*判断resultList中最后一页的第一个对象是否存在*/
                var dataStart = (parseInt($("#sample_1").data("countno")-1))*10,dataEnd;
                var $search = $("#searchPanel");
                var a = $search.find("input[type='hidden']"),
                    str = '';
                for(var i = 0; i< a.length ;i++){
                    str += "&"+a[i].name +"="+ a[i].value;
                }
                if(resultList[dataStart] == null){
                    $.ajax({
                        type:"get",
                        url: $("#sample_1").data("url")+ "?pagination.start="+ dataStart + "&pagination.pageSize=10&pagination.jsonPageNo=1&pagination.pageNo="+ $("#sample_1").data("countno") + str,
                        success:function(data){
                            dataEnd = dataStart + data.resultList.length;
                            for(var i = 0 ; i < data.resultList.length ; i++){
                                resultList[dataStart+i] = data.resultList[i];
                            }
                            //获得数据后开始初始化界面
                            var $tr = $("#tbody").find("tr").not(".add");
                            for (var i = dataStart,k = 0 ; i < dataEnd,k<data.resultList.length;i++,k++) {
                                var $td = $tr[k].children;
                                for (var j = 1; j < $td.length; j++) {
                                    var tdWidth = $td[j].style.width;
                                    $td[j].children[0].style.width = tdWidth;
                                    $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("resultList[" + i + "]." + thName[j - 1]));
                                }
                            }
                            for(var i = data.resultList.length;i < 10;i++){
                                $tr[i].className = 'trHide';
                            }
                        }
                    })
                }else{
                    dataEnd = resultList.length;
                    var $tr = $("#tbody").find("tr").not(".add");
                    for(var i = dataEnd - dataStart;i < 10;i++){
                        $tr[i].className = 'trHide';
                    }
                    for (var i = dataStart,k = 0 ; i < dataEnd,k<(dataEnd - dataStart);i++,k++) {
                        var $td = $tr[k].children;
                        for (var j = 1; j < $td.length; j++) {
                            var tdWidth = $td[j].style.width;
                            $td[j].children[0].style.width = tdWidth;
                            $td[j].children[0].textContent = dataRender(rtpye[j - 1],eval("resultList[" + i + "]." + thName[j - 1]));
                        }
                    }
                }
                $('#sample_1').off('click', ' tbody td .row-details');
                reBindCheck();
                $('#sample_1').on('click', ' tbody td .row-details', function () {
                    if ($(this).hasClass("row-details-open")) {
                        /* This row is already open - close it */
                        $(this).addClass("row-details-close").removeClass("row-details-open");
                        $(this).parents("tr").next("tr").remove();
                    }
                    else {
                        /* Open this row */
                        $(this).addClass("row-details-open").removeClass("row-details-close");
                        var num;
                        num = $("tr").not(".add").index($(this).parents("tr"));
                        var pageActive = parseInt($("#sample_1_paginate").find(".active").text());
                        var sOut = '<tr class="details add"><td class="details"colspan="'+ $("#thead").find("th").length+'"><div class="row-fluid">';
                        var rows = Math.ceil(moble.length/3),count = 0;
                        for(var i = 0 ; i < rows,count<moble.length; i++){
                            /*详细信息展示*/
                            for(var j = 0;j<3 && count<moble.length;j++){
                                if( j == 0){
                                    sOut += '<div class="span12">';
                                }
                                count++;
                                sOut +='<div class="span4"><div class="span3">'+ moble[(3*i+j)].key +'：</div><div class="span9">'+ dataRender(moble[3*i+j].rtype,eval("resultList["+(num-1+(pageActive-1)*10)+"]."+moble[3*i+j].name)) +'</div></div>';
                                if(j==2){
                                    sOut += '</div>';
                                }
                            }
                        }
                        sOut += '</div></td></tr>';
                        $(this).parents("tr").after(sOut);
                    }
                });

                //页码渲染
                var _inner = parseInt($("#sample_1").data("countno"))-4;
                pageList.find("li").not($("li.prev")).not($("li.next")).each(function () {
                    $(this).find("a").html(_inner);
                    _inner++;
                });
                pageList.find(".active").removeClass("active");
                pageList.find("li").not($(".prev")).not($(".next")).last("li").addClass("active");
                pageList.find(".prev").hasClass("disabled")?pageList.find(".prev").removeClass("disabled"): "";
                pageList.find(".next").hasClass("disabled")?"": pageList.find(".next").addClass("disabled");
            });
            $(".checker").on("click", function () {
               $(this).find("span").hasClass("checked")?$(this).find("span").removeClass("checked"):$(this).find("span").addClass("checked");
            });
            $(".sorting").removeClass("sorting");
            $(".sorting_asc").removeClass("sorting_asc");
        }
    };
}();
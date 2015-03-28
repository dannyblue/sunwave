function showall(menu_list, parent) {
    for (var menu in menu_list) {
        //如果有子节点，则遍历该子节点
        if (menu_list[menu].children.length > 0) {
            //创建一个子节点li
            var li	 = $('<li class="list-group-item hasChild"></li>');

            //将li的文本设置好，并马上添加一个空白的ul子节点，并且将这个li添加到父亲节点中
            $(li).append(menu_list[menu].name).append('<ul class="list-group tree"></ul>')
                    .appendTo(parent);
            //将空白的ul作为下一个递归遍历的父亲节点传入
            showall(menu_list[menu].children, $(li).children().eq(0));
        }
        //如果该节点没有子节点，则直接将该节点li以及文本创建好直接添加到父亲节点中
        else {
            $('<li class="list-group-item"></li>').append('<input type="checkbox" /><label>'+menu_list[menu].name+'</label>').appendTo(parent);
        }
    }

}
var menulist =  [
    {
        "id":"1",
        "name":"Bootstrap",
        "children":
            [
                {
                    "id":"11",
                    "name":"JavaScript",
                    "children":""
                },{
                "id":"12",
                "name":"CSS",
                "children":""
            },{
                "id":"13",
                "name":"Buttons",
                "children":
                    [
                        {
                            "id":"131",
                            "name":"Colors",
                            "children":""
                        },{
                        "id":"132",
                        "name":"Sizes",
                        "children":""
                    },{
                        "id":"133",
                        "name":"Forms",
                        "children":
                            [
                                {
                                    "id":"1331",
                                    "name":"Horizontal",
                                    "children":""
                                },{
                                "id":"1332",
                                "name":"Vertical",
                                "children":""
                            }
                            ]
                    }
                    ]
            }
            ]
    }
];
var showlist = $('<ul class="list-group tree"></ul>');
showall(menulist, showlist);
$("#tree2").append(showlist);
$(".hasChild").each(function(){
    var textContent = $(this).context.childNodes[0].textContent;
    $(this).context.childNodes[0].textContent = '';
    var html  = '<input type="checkbox" /><label class="tree-toggle tree-parent">'+ textContent +'<span class="icon icon-chevron-down"></span></label> ';
    $(this).prepend(html);
})
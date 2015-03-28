//全局字段渲染变量，与 com.sunwave.framework.util.ElTag.dataMap 同步更新
var GlobalData={
	locked:[["0","正常"],["1","锁定"]],////人员状态
	sysRole:[["1","admin"],["2","manager"],["3","normal"]],
	isWhite:[["0","<span class=\"label label-important\">否</span>"],["1","<span class=\"label label-success\">是</span>"]],
	operator:[["0","移动"],["1","联通"],["2","电信"]]
}


//全局字段渲染函数
function dataRender(t,v){
	if(t==null){
		return v;
	}else{
		var d = eval("GlobalData."+t);
		for(var i in d){
			if(d[i][0]==v){
				return d[i][1];
			}
		}
	}
}
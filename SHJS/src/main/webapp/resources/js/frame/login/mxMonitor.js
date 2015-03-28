
/**
 * 地图侦测监控处理函数
 **/
var MxMonitor=function(){
	return {
	init:function(container,mapConfig){
		// Checks if the browser is supported
		if (!mxClient.isBrowserSupported()) {
			// Displays an error message if the browser is not supported.
			mxUtils.error('Browser is not supported!', 200, false);
		} else {
			// Creates the graph inside the given container
			var graph = this.createGraph(container);
			this.readConfig(graph,mapConfig.configType,mapConfig.configUrl,mapConfig.configContent);
			return graph;
		}
	},
	readConfig:function(graph,configType,configUrl,configContent){
		
		var doc;
		if(configType==0){//url
//			configUrl = "resources/others/mapConfig/map_1.xml";
			doc = mxUtils.load(configUrl).getDocumentElement().ownerDocument;
		}else if(configType==1){//读取数据库
			configContent=configContent;
			doc = mxUtils.parseXml(configContent);
		}
			var codec = new mxCodec(doc);
			codec.decode(doc.documentElement, graph.getModel());
			graph.getModel().endUpdate();
			graph.setCellsLocked(true);
	},
	createGraph:function(container){
		var node = mxUtils.load('resources/others/mapConfig/myworkfloweditor.xml').getDocumentElement();
		var editor = new mxEditor(node);
		var graph = editor.graph;
		var style = new Object();
		return graph;
	},
	update:function(graph,data){
		alert('update');
	}
	}
}();


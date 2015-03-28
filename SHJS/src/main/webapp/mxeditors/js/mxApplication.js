/*
 * $Id: mxApplication.js,v 1.21 2010-11-08 20:09:40 gaudenz Exp $
 * Copyright (c) 2006-2010, JGraph Ltd
 *
 * Defines the startup sequence of the application.
 *
 */
{

	/**
	 * Constructs a new application (note that this returns an mxEditor
	 * instance).
	 */
	function mxApplication(config)
	{
		var hideSplash = function()
		{
			// Fades-out the splash screen
			var splash = document.getElementById('splash');
			
			if (splash != null)
			{
				try
				{
					mxEvent.release(splash);
					mxEffects.fadeOut(splash, 100, true);
				}
				catch (e)
				{
					splash.parentNode.removeChild(splash);
				}
			}
		};
		
		try
		{
			if (!mxClient.isBrowserSupported())
			{
				mxUtils.error('Browser is not supported!', 200, false);
			}
			else
			{
				//TODO
				var node = mxUtils.load(config).getDocumentElement();
				var editor = new mxEditor(node);
				
				// Updates the window title after opening new files
				var title = document.title;
				var funct = function(sender)
				{
					document.title = title + ' - ' + sender.getTitle();
				};

				var saveFunct = function(sender)
				{
					var graph=sender.graph;
					var enc = new mxCodec(mxUtils.createXmlDocument());  
					var node = enc.encode(graph.getModel());  
					var xmlContent = mxUtils.getXml(node);
					 $.ajax({
			             type: "POST",
			             url: "mxeditors/saveMapConfig.do",
			             data:{xmlContent:xmlContent},
			             dataType: "json",
			             success: function(data){
			             	console.log("保持配置成功："+data.xmlContent)
			             }
			         });
				};
				
				editor.addListener(mxEvent.OPEN, funct);
				
				// Prints the current root in the window title if the
				// current root of the graph changes (drilling).
				editor.addListener(mxEvent.ROOT, funct);
				
				funct(editor);

				editor.addListener(mxEvent.SAVE, saveFunct);
				
				// Displays version in statusbar
				editor.setStatus('mxGraph '+mxClient.VERSION);

				// Shows the application
				hideSplash();
				
				//初始化对应监区地图
				$.ajax({
				    type: "GET",
				    url: "mxeditors/getConfig4User.do",
				    dataType: "json",
				    success: function(data){
				    	if(data!=null){
					    	var doc = mxUtils.parseXml(data.configContent);
					    	var codec = new mxCodec(doc);
					    	var graph=editor.graph;
							codec.decode(doc.documentElement, graph.getModel());
//							graph.getModel().endUpdate();
				    	}else{
				    		alert("图配置数据加载错误，请联系我们！")
				    	}
				    }
				});
			}
		}
		catch (e)
		{
			hideSplash();

			// Shows an error message if the editor cannot start
			mxUtils.alert('Cannot start application: '+e.message);
			throw e; // for debugging
		}
								
		return editor;
	}

}

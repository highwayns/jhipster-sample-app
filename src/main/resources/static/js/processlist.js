  $(document).ready(function(){
	  $("#upload").submit(function(){
	    if($("#fileupload").val()=="")
	    {
	    	alert("Please select workflow file upload");
	    	return false;
	    }
	  });
	  
  });
  
  $(document).ready(function(){
	    var grid =$("#grid-data").bootgrid({
	    ajax:true,
	    rowCount:10,
	    navigation:2,
	    columnSelection:false,
	    url:"/getprocesslists",
	        formatters: {
	        "commands": function(column, row)
	        {
	            return "<button type=\"button\" class=\"btn btn-xs btn-info command-delete\" data-row-id=\"" + row.deploymentId + "\">Delete</button>"
				+"<button type=\"button\" class=\"btn btn-xs btn-info command-export\" data-row-id=\"" + row.id + "\">Export</button>"
				;
	        },
	        "resname":function(column, row)
	        {
	            return "<a target=\"_blank\" href=\"/showresource?pdid="+row.id+"&resource="+row.resourceName+"\">"  + row.resourceName + "</a>";
	        },
	        "picname":function(column, row)
	        {
	        	return "<a target=\"_blank\" href=\"/showresource?pdid="+row.id+"&resource="+row.diagramresourcename+"\">"  + row.diagramresourcename + "</a>";
	        },
	    	}
	    }).on("loaded.rs.jquery.bootgrid", function()
		{
		    grid.find(".command-delete").on("click", function(e)
			{
				$.post("/deletedeploy",{deployid:$(this).data("row-id")},function(){
					alert("Delete Success");
					$("#grid-data").bootgrid("reload");
				});
			});
		    grid.find(".command-export").on("click", function(e)
			{
				$.post("/export",{processDefinitionId:$(this).data("row-id")},function(resp){
					// コールバック関数でなんか処理
					if (resp.length == 0) {

					} else {
						// レスポンスに入力されていればエラーメッセージとしてダイアログに出力(とする)
						var binaryData = [];
						binaryData.push(resp);
						const url = window.URL.createObjectURL(new Blob(binaryData, {type: "application/xml"}));
						const a = document.createElement("a");
						document.body.appendChild(a);
						a.download = 'bpmn20.xml';
						a.href = url;
						a.click();
						a.remove();
						window.URL.revokeObjectURL(url);
					}
				});
			});
		});
	});

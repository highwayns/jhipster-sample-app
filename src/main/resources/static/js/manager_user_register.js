    
    $(document).ready(function(){
    	$("#manager").hide();
	    var grid=$("#grid-data").bootgrid({
	    	navigation:2,
  			columnSelection:false,
		    ajax:true,
		    url:"/managertasklist",
		    formatters: {
		    "taskcreatetime":function(column, row){
		    	return getLocalTime(row.taskcreatetime);
		    },
		    "commands": function(column, row)
		    {
		            return "<button class=\"btn btn-xs btn-default ajax-link command-run1\" data-row-id=\"" + row.taskid + "\">Process</button>";
		    }
	    	}
	    
	    }).on("loaded.rs.jquery.bootgrid", function()
	    		{
	    	    grid.find(".command-run1").on("click", function(e)
	    	    {
	    	    	var taskid=$(this).data("row-id");
	    	    	$.post("/gettaskbyid",{"taskid":taskid},function(data){
	    	    		var obj = data;
	    	    		$("#username").val(obj.username);
	    	    		$("#password").val(obj.password);
	    	    		$("#birthday").val(obj.birthday);
	    	    		$("#tel").val(obj.tel);
	    	    		$("#mail").val(obj.mail);
	    	    		$("#user_id").val(obj.user_id);
	    	    		$("#applytime").val(obj.apply_time);
	    	    		$("form").attr("action","/task/completebymanager/"+taskid);
	    	    	});
	    	    	$("#manager").show();
	    	    	$("#btn").click(function(){
	    		    	$.post("/task/completebymanager/"+taskid,$("form").serialize(),function(a){
	    		    		alert("Process Success");
	    		    		LoadAjaxContent("manager_user_register");
	    		    	});
	    	    	
	    	    });
	    	    });
	    
	    
	    });
	  });
	  
function getLocalTime(nS) {  
 return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');  
}

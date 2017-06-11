<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>dwr test</title>
	<script type='text/javascript' src='js/jquery-1.9.1.min.js'></script>
	<script type='text/javascript' src='/springdemo/dwr/engine.js'></script>
	<script type='text/javascript' src='/springdemo/dwr/util.js'></script>
  	<script type='text/javascript' src='/springdemo/dwr/interface/DwrController.js'></script>
    <script type="text/javascript">
    	$(function(){
	    	$('#sendMessage').click(function() {
	            var message = $('#message').val();  
	            //直接用dwr.xml中暴露出来的类来调用，第一个是方法test的传入参数，最后一个是回调的方法  
	            DwrController.test(message);  
	        });
    	});

    	//回调方法
    	dwr.engine.setActiveReverseAjax(true);
    	dwr.engine.setNotifyServerOnPageUnload(true);
        function showMessage(data) {
        	$('#returnmessage').text(data);
        }
	</script>
</head>
<body>
	<input id="message" type="text" name="message" />
	<input id="sendMessage" type="button" value="send message" />
	<div id="returnmessage"></div>
</body>
</html>
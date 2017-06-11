<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Spring Demo</title>
<style>
	body {
		font-family: sans-serif;
	}
</style>
</head>
<body>

	<h1>Cookie</h1>

	Page refresh count: <b> ${cookie.refreshCount.value} </b>

	<h1>SessionId</h1>

	SessionId: <b> ${sessionId} </b><br>
	<p>
	redis host: <b>${redisHost }</b><br>
	<p>
	redis Port: <b>${redisPort }</b><br>
	<p>
	helloWorld: <b>${helloWorld }</b>
	

</body>
</html>

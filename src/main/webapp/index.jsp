<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.2.min.js"></script>  
  </head>
  
  <body>
    <form onsubmit="return sumbit();" target="upload" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath }/upload">
        <input type="file" name="file"/>
        <script type="text/javascript">
        	function sumbit(){
        		if($("input[name='file']").val() == ''){
        			alert("请先上传文件！");
        			return false;
        		}
        		
        		return true;
        	}
        </script>
        <input type="submit" value="提交"/>
    </form>
    <iframe name="upload"></iframe>
  </body>
</html>

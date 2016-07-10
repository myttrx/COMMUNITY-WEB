<% 
	String g_contextPath = request.getContextPath();
	String g_basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + g_contextPath + "/";
	request.setAttribute("g_contextPath", g_contextPath);
	request.setAttribute("g_basePath", g_basePath);
%>
<script type='text/javascript' >
//define global constant
var g_contextPath = "<%=g_contextPath%>";
var g_basePath = "<%=g_basePath%>"; 
</script>
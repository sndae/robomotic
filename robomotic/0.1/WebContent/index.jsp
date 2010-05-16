<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<c:set var="loginAction"><s:url action="login" namespace="/" /></c:set>
<%
    response.sendRedirect((String)pageContext.getAttribute("loginAction"));
%>
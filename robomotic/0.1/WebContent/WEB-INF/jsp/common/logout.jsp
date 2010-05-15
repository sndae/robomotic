<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>

<fmt:setLocale value="${pageLocale}"/>

<fmt:bundle basename="com.robomotic.translation">

<div class="logout">
    <a href="<s:url action="logout" />"><fmt:message key="logout.label" /></a>
</div>

</fmt:bundle>
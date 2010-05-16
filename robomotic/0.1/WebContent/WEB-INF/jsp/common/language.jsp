<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ page import="com.robomotic.util.Constants"%>

<c:set var="langParam"><%=Constants.pageLocaleParam%></c:set>
<c:set var="basePath">${queryString}</c:set>
<c:if test="${not empty basePath}">
    <c:set var="basePath">${basePath}&</c:set>
</c:if>

<fmt:setLocale value="${pageLocale}"/>

<fmt:bundle basename="com.robomotic.translation">

<div class="language">
    <ul>
        <c:forEach items="${availableLanguages}" var="lang">
            <li>
                <c:choose>
                    <c:when test="${lang == pageLocale}">
                        <span>${lang}</span>
                    </c:when>
	                <c:otherwise>
	                    <a href="?${basePath}${langParam}=${lang}">${lang}</a>
	                </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
</div>

</fmt:bundle>
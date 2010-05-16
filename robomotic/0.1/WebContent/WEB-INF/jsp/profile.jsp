<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<fmt:setLocale value="${pageLocale}"/>

<fmt:bundle basename="i18n.com.robomotic.translation">

<html>
<c:import url="common/header.jsp" />

<body>
    <c:import url="common/logout.jsp" />
    <c:import url="common/language.jsp" />
    <div class="clear">&nbsp;</div>
    
    <div class="mainWrapper">
        <div class="page">
        
            This is the profile page
        
        </div>
    </div>
    
    <c:import url="common/footer.jsp" />
</body>
</html>

</fmt:bundle>
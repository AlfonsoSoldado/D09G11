<%--
 * register_Explorer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="manager/register_Manager.do" modelAttribute="managerForm">

	<form:hidden path="manager.id"/>
	<form:hidden path="manager.version"/>

	<acme:textbox code="actor.username" path="manager.userAccount.username" />
	<jstl:choose>
		<jstl:when test="${managerForm.manager.id==0}">
			<acme:password code="actor.password" path="manager.userAccount.password" />
			<br />
			<acme:password code="actor.password" path="confirmPassword" />
			<br />
		</jstl:when>
		<jstl:otherwise></jstl:otherwise>
	</jstl:choose>

	<acme:textbox code="actor.name" path="manager.name" />
	<acme:textbox code="actor.surname" path="manager.surname" />
	<acme:textbox code="actor.email" path="manager.email" />
	<acme:textbox code="actor.phoneNumber" path="manager.phoneNumber" />
	<acme:textbox code="actor.postalAddress" path="manager.postalAddress" />

	<jstl:if test="${managerForm.manager.id == 0}">
   		<form:label path="terms">
		<spring:message code="actor.legal.agree"/><a href="misc/legal.do"><spring:message code="actor.legal.info"/></a>
		</form:label>
		<input type="checkbox" id="terms" name="terms" required /> <spring:message code="actor.legal.agree" /><br>
		<form:errors cssClass="error" path="terms"/>
   </jstl:if>
	
	<br />
	
	<acme:submit name="save" code="actor.submit" />
	<acme:cancel url="/" code="answer.cancel" />
	
	<br />
</form:form>

<script type="text/javascript">
$('#form input[type=checkbox]').on('change invalid', function() {
    var campotexto = $(this).get(0);

    campotexto.setCustomValidity('');

    if (!campotexto.validity.valid) {
      campotexto.setCustomValidity('<jstl:out value="${check}"/>');  
    }
});

</script>
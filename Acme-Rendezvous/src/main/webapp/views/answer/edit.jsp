<%--
 * edit.jsp
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

<form:form action="answer/user/edit.do" modelAttribute="answer">
	<security:authorize access="hasRole('USER')">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="question" />

		<acme:textbox code="answer.text" path="text" />

		<acme:submit name="save" code="answer.submit" />
		<acme:cancel url="answer/list.do" code="answer.cancel" />
		<acme:delete confirmationCode="answer.confirmationCode" buttonCode="answer.delete" id="${answer.id }" />
	</security:authorize>
</form:form>



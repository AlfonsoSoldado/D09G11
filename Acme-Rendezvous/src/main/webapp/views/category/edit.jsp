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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">

	<form:form action="category/administrator/edit.do"
		modelAttribute="category">

		<form:hidden path="id" />
		<form:hidden path="version"/>
		<acme:textbox code="category.name" path="name" />
		<acme:textbox code="category.description" path="description" />
		<form:label path="level">
			<spring:message code="category.level" />:
	</form:label>
		<form:select id="level" path="level">
			<form:options items="${levels}" />

		</form:select>
		<form:errors cssClass="error" path="level" />
		<br />
		<acme:submit name="save" code="category.submit" />
		<acme:cancel url="category/administrator/list.do"
			code="category.cancel" />
		<jstl:if test="${id!=0 }">
			<acme:delete confirmationCode="category.confirmationCode"
				buttonCode="category.delete" id="${category.id }" />
		</jstl:if>
	</form:form>
</security:authorize>


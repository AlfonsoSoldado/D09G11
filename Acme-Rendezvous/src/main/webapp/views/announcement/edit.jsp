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
<security:authorize access="hasRole('USER')">
	<form:form action="${requestUri}" modelAttribute="announcement">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="momentMade" />
		<form:hidden path="rendezvous" />

		<acme:textbox code="announcement.title" path="title" />
		<acme:textbox code="announcement.description" path="description" />

		<!-- Buttons -->

		<acme:submit name="save" code="announcement.save" />
		<acme:delete confirmationCode="announcement.confirm.delete"
			buttonCode="announcement.delete" id="${announcement.id }" />
		<acme:cancel url="rendezvous/list.do" code="announcement.cancel" />

	</form:form>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">

	<form:form action="${requestUri}" modelAttribute="announcement">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="momentMade" />
		<form:hidden path="rendezvous" />

		<acme:textbox code="announcement.title" path="title" readonly="true"/>
		<acme:textbox code="announcement.description" path="description" readonly="true"/>

		<!-- Buttons -->

		<acme:delete confirmationCode="announcement.confirm.delete"
			buttonCode="announcement.delete" id="${announcement.id }" />
		<acme:cancel url="rendezvous/list.do" code="announcement.cancel" />
	</form:form>
</security:authorize>
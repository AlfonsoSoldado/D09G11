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

<form:form action="${requestUri}" modelAttribute="actor">

	<form:hidden path="id" />
	<form:hidden path="userAccount" />
	<form:hidden path="answer" />

	<acme:textbox code="actor.name" path="name" />
	<acme:textbox code="actor.surname" path="surname" />
	<acme:textbox code="actor.email" path="email" />
	<acme:textbox code="actor.phoneNumber" path="phoneNumber" />
	<acme:textbox code="actor.postalAddress" path="postalAddress" />

	<security:authorize access="hasRole('USER')">
		
		<form:hidden path="comment"/>
		<form:hidden path="rendezvous"/>
		<form:hidden path="rsvp"/>
		<form:hidden path="question"/>
		<form:hidden path="answer" />
		<acme:date code="actor.dateBorn" path="dateBorn"  placeholder="dd/MM/yyyy"/>

	</security:authorize>

	<acme:submit name="save" code="actor.submit" />
	<acme:cancel url="/" code="answer.cancel" />

</form:form>



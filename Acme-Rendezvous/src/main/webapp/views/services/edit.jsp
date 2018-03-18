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

<form:form action="services/manager/edit.do" modelAttribute="services">
	<security:authorize access="hasRole('MANAGER')">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="manager" />
		<form:hidden path="canceled"/>
		<form:hidden path="rendezvous"/>

		<acme:select items="${categories1 }" itemLabel="name" code="services.category" path="category"/>
		<acme:select items="${categories2 }" itemLabel="name" code="services.category" path="category"/>
		<acme:select items="${categories3 }" itemLabel="name" code="services.category" path="category"/>

		<acme:textbox path="name" code="services.name" />
		<acme:textbox path="description" code="services.description" />
		<acme:textbox path="picture" code="services.picture" />
		
		<acme:submit name="save" code="services.submit" />
		<acme:cancel url="rendezvous/list.do" code="services.cancel" />
		<acme:delete confirmationCode="services.confirmationCode"
			buttonCode="services.delete" id="${services.id }" />
	</security:authorize>
</form:form>



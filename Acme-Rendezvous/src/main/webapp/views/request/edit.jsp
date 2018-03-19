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

<form:form action="request/user/edit.do" modelAttribute="request">
	<security:authorize access="hasRole('USER')">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="services" />
		<form:hidden path="moment" />

		<acme:textbox path="creditCard.holderName" code="request.creditCard.holderName" />
		<acme:textbox path="creditCard.brandName" code="request.creditCard.brandName" />
		<acme:textbox path="creditCard.number" code="request.creditCard.number" />
		<acme:textbox path="creditCard.expirationMonth" code="request.creditCard.expirationMonth" />
		<acme:textbox path="creditCard.expirationYear" code="request.creditCard.expirationYear" />
		<acme:textbox path="creditCard.CVV" code="request.creditCard.CVV" />
		<acme:textbox path="comment" code="request.comment" />
		

		<acme:submit name="save" code="request.submit" />
		<acme:cancel url="services/list.do" code="request.cancel" />
		<acme:delete confirmationCode="request.confirmationCode"
			buttonCode="request.delete" id="${request.id }" />
	</security:authorize>
</form:form>



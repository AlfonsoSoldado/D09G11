<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="services" requestURI="${requestURI}" id="row">

	<spring:message code="services.name" var="name" />
	<display:column property="name" title="${name }"
		class="${row.canceled }" />

	<spring:message code="services.description" var="description" />
	<display:column property="description" title="${description }"
		class="${row.canceled }" />
		
	<spring:message code="services.rendezvous" var="rendezvous" />
	<display:column property="rendezvous.name" title="${rendezvous }"
		class="${row.canceled }" />

	<spring:message code="services.picture" var="picture" />
	<display:column class="${row.canceled }">
		<img class="imagenesComment" src="${row.picture}">
	</display:column>
	
	<spring:message code="services.canceled" var="canceled" />
	<display:column property="canceled" title="${canceled }"
		class="${row.canceled }" />
		
	<spring:message code="services.category" var="category" />
	<display:column property="category" title="${category }"
		class="${row.canceled }" />

	<display:column>
		<jstl:if test="${row.canceled == false}">
			<acme:links url="services/manager/edit.do?servicesId=${row.id }"
				code="services.edit" />
		</jstl:if>
	</display:column>

</display:table>

<acme:links url="services/manager/create.do" code="services.create" />


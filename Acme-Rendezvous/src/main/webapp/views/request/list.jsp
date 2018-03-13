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
	name="request" requestURI="${requestURI}" id="row">

	<acme:column property="comment" code="request.comment" />
	<acme:column property="services.name" code="request.services" />
	
	<security:authorize access="hasRole('MANAGER')">
	<display:column>
		<jstl:if test="${row.services == null}">
		<acme:links url="services/manager/create.do?rendezvousId=${rendezvous}" code="request.create.services" />
		</jstl:if>
	</display:column>
	</security:authorize>
	
</display:table>




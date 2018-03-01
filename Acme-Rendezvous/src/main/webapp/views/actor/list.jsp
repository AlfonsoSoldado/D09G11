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

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="user" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<acme:column code="actor.username" property="userAccount.username" />
	<display:column><acme:links url="rendezvous/listByUser.do?userId=${row.id }" code="actor.rendezvous"/></display:column>
	



	<spring:message var="displayHeader" code="user.display" />
		<display:column title="${displayHeader}">
		<a href="user/display.do?userId=${row.id}"> <spring:message
				code="user.display" />
		</a>
	</display:column>

</display:table>
<br>
<acme:links url="rendezvous/list.do" code="actor.back" />
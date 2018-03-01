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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="announcement" requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<acme:column code="announcement.title" property="title" />
	<acme:column code="announcement.description" property="description" />
	<acme:column code="announcement.momentMade" property="momentMade" sortable="true" />
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column> <acme:links url="announcement/administrator/edit.do?announcementId=${row.id}" code="announcement.delete" /> </display:column>
	</security:authorize>
	
</display:table>

<!-- Action links -->
<br>
<acme:links url="rendezvous/list.do" code="announcement.back" />
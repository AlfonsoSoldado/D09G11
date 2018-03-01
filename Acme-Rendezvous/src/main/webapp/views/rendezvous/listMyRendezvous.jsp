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
	name="rendezvous" requestURI="${requestUri}" id="row">

	<!-- Attributes -->
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${row.finalMode == false}">
				<acme:links url="rendezvous/user/edit.do?rendezvousId=${row.id}"
					code="rendezvous.edit" />
			</jstl:if>
		</display:column>

	</security:authorize>

	<spring:message code="rendezvous.name" var="name" />
	<display:column property="name" title="${name }"
		class="${row.deleted }" />

	<spring:message code="rendezvous.description" var="description" />
	<display:column property="description" title="${description }"
		class="${row.deleted }" />

	<spring:message code="rendezvous.moment" var="moment" />
	<display:column property="moment" title="${moment }"
		class="${row.deleted }" />
		
	<spring:message code="rendezvous.gpsCoordinate.name" var="gpsCoordinates"/>
	<display:column property="gpsCoordinate.namePlace" title="${gpsCoordinates }" class="${row.deleted }"/>

	<spring:message code="rendezvous.finalMode" var="finalMode" />
	<display:column property="finalMode" title="${finalMode }"
		class="${row.deleted }" />

	<spring:message code="rendezvous.deleted" var="deleted" />
	<display:column property="deleted" title="${deleted}" sortable="false"
		class="${row.deleted }" />
		
		<spring:message code="rendezvous.picture" var="picture"/>
		<display:column><img class="imagenesComment" src="${row.picture}"></display:column>
		
		
	<display:column>
		<jstl:if test="${row.deleted == false}">
			<acme:links url="rendezvous/listSimilar.do?rendezvousId=${row.id }"
			code="rendezvous.similar" />
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if test="${row.deleted == false}">
			<acme:links url="announcement/list.do?rendezvousId=${row.id}"
			code="rendezvous.announcement" />
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if test="${row.deleted == false}">
			<acme:links url="announcement/user/create.do?rendezvousId=${row.id}"
			code="rendezvous.announcement.create" />
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if test="${row.deleted == false}">
			<acme:links url="question/listByUser.do?rendezvousId=${row.id}"
			code="rendezvous.question" />
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if test="${row.deleted == false}">
			<acme:links url="question/user/create.do?rendezvousId=${row.id}"
			code="rendezvous.question.create" />
		</jstl:if>
	</display:column>
	<display:column>
			<jstl:if test="${row.deleted == false}">
				<acme:links url="user/listAttendant.do?rendezvousId=${row.id}" code="rendezvous.listAttendant" />
			</jstl:if>
		</display:column>

</display:table>

<!-- Action links -->

<security:authorize access="hasRole('USER')">
	<br>
	<acme:links url="rendezvous/user/create.do" code="rendezvous.create" />
</security:authorize>
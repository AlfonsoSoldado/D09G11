<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<spring:message code="administrator.averageRedezvousUser" />
</h3>
<p>
	<jstl:out value="${averageRedezvousUser}" />
</p>
<h3>
	<spring:message code="administrator.estandardDesviationRedezvousUser" />
</h3>
<p>
	<jstl:out value="${estandardDesviationRedezvousUser}" />
</p>
<h3>
	<spring:message code="administrator.ratioUserConRendezvous" />
</h3>
<p>
	<jstl:out value="${ratioUserConRendezvous}" />
</p>
<h3>
	<spring:message code="administrator.ratioUserSinRendezvous" />
</h3>
<p>
	<jstl:out value="${ratioUserSinRendezvous}" />
</p>
<h3>
	<spring:message code="administrator.averageUsersRendezvous" />
</h3>
<p>
	<jstl:out value="${averageUsersRendezvous}" />
</p>
<h3>
	<spring:message code="administrator.estandardDesviationUsersRendezvous" />
</h3>
<p>
	<jstl:out value="${estandardDesviationUsersRendezvous}" />
</p>
<h3>
	<spring:message code="administrator.averageRendezvousRSVPTruePerUser" />
</h3>
<p>
	<jstl:out value="${averageRendezvousRSVPTruePerUser}" />
</p>
<h3>
	<spring:message
		code="administrator.estandardDesviationRendezvousRSVPTruePerUser" />
</h3>
<p>
	<jstl:out value="${estandardDesviationRendezvousRSVPTruePerUser}" />
</p>
<h3>
	<spring:message code="administrator.averageannouncementsRendezvous" />
</h3>
<p>
	<jstl:out value="${averageannouncementsRendezvous}" />
</p>
<h3>
	<spring:message
		code="administrator.estandardDesviationAnnouncementsUser" />
</h3>

<p>
	<jstl:out value="${estandardDesviationAnnouncementsUser}" />
</p>
<h3>
	<spring:message code="administrator.redezvousSimiliars10" />
</h3>

<h3>
	<jstl:if test="${redezvousSimiliars10==null}">
		<spring:message code="administrator.noHay" />
	</jstl:if>
</h3>
<p>
	<jstl:if test="${redezvousSimiliars10!=null}">

		<jstl:forEach var="row" items="${redezvousSimiliars10}">

			<jstl:out value="${row.name }"></jstl:out>
		</jstl:forEach>
	</jstl:if>
</p>
<h3>
	<spring:message
		code="administrator.averageNumberOfQuestionsPerRendezvous" />
</h3>
<p>
	<jstl:out value="${averageNumberOfQuestionsPerRendezvous}" />
</p>
<h3>
	<spring:message
		code="administrator.estandardDesviationOfQuestionsPerRendezvous" />
</h3>
<p>
	<jstl:out value="${estandardDesviationOfQuestionsPerRendezvous}" />
</p>
<h3>
	<spring:message
		code="administrator.averageOfAnswerPerQuestionsPerRendezvous" />
</h3>
<p>
	<jstl:out value="${averageOfAnswerPerQuestionsPerRendezvous}" />
</p>
<h3>
	<spring:message
		code="administrator.estandardDesviationOfAnswerPerQuestionsPerRendezvous" />
</h3>
<p>
	<jstl:out
		value="${estandardDesviationOfAnswerPerQuestionsPerRendezvous}" />
</p>
<h3>

	<spring:message code="administrator.RendezvousConMas075Announcement" />
</h3>


<p>
	<jstl:forEach var="row" items="${RendezvousConMas075Announcement}">
		<p>
			<jstl:out value="${row.name }"></jstl:out>
		</p>
	</jstl:forEach>
</p>
<h3>
	<spring:message code="administrator.averageRepliesComment" />
</h3>
<p>
	<jstl:out value="${averageRepliesComment}" />
</p>
<h3>
	<spring:message code="administrator.estandardDesviationRepliesComment" />
</h3>

<p>
	<jstl:out value="${estandardDesviationRepliesComment}" />
</p>
<h3>
	<spring:message code="administrator.topRedezvous" />
</h3>

<p>
	<jstl:forEach var="row" items="${topRedezvous}">
		<p>
			<jstl:out value="${row.name }"></jstl:out>
		</p>
	</jstl:forEach>
</p>

<!-- falta lo del top -->
















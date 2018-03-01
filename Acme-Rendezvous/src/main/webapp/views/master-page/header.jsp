<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="/Acme-Rendezvous"><img src="images/logo.png"
		alt="Acme-Rendezvous Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				
				<ul>
					<li class="arrow"></li>
					
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
				
			
			<li><a class="fNiv" href="rendezvous/list.do"><spring:message
						code="master.page.rendezvous" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message
						code="master.page.user.list" /></a></li>
						
						<li><a class="fNiv" href="administrator/display.do"><spring:message
						code="master.page.admin.display" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/user/edit.do"><spring:message
								code="master.page.actorEdit" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
			<li><a class="fNiv" href="rendezvous/list.do"><spring:message
						code="master.page.rendezvous" /></a></li>
			<li><a class="fNiv" href="announcement/user/streamAnnouncement.do"><spring:message
						code="master.page.myAnnouncements" /></a></li>
			<li><a class="fNiv" href="rendezvous/user/listMyRendezvous.do"><spring:message
						code="master.page.myRendezvous" /></a></li>
			<li><a class="fNiv" href="rendezvous/user/listAttendRendezvous.do"><spring:message
						code="master.page.attendedRendezvous" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message
						code="master.page.user.list" /></a></li>
			<li><a class="fNiv" href="RSVP/user/create.do"><spring:message
						code="master.page.rsvp" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv" href="user/register_User.do"><spring:message
						code="master.page.register.user" /></a></li>
			<li><a class="fNiv" href="rendezvous/list.do"><spring:message
						code="master.page.rendezvous" /></a></li>
			<li><a class="fNiv" href="user/list.do"><spring:message
						code="master.page.user.list" /></a></li>
		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>


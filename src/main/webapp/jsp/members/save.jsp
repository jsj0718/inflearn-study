<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%
    // request, response는 그대로 사용 가능 (jsp가 servlet으로 자동으로 변환되는데, service()가 호출된다고 생각하면 된다.)
    MemberRepository memberRepository = MemberRepository.getInstance();

    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    Member savedMember = memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p>성공</p>
    <ul>
        <li>id=<%=member.getId()%></li>
        <li>username=<%=member.getUsername()%></li>
        <li>age=<%=member.getAge()%></li>
    </ul>

    <a href="/index.html">메인</a>
</body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="ClaseObjeto.Libro"%>
<%
    List<Libro> lista=(List<Libro>)request.getAttribute("lista");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>LISTADO DE LIBRO</h1>
        <p><a href ="MainController?op=nuevo">Nuevo</a></p>
        <table border="3">
            <tr>
                <th>Id</th>
                 <th>ISBN</th>
                  <th>Titulo</th>
                   <th>Categoria</th>
                    <th></th>
                    <th></th>
                    
            </tr>
            <c:forEach var="item" items="${lista}">
                <tr>
                    <td>${item.id}</td>
                    <td>${item.isbn}</td>
                    <td>${item.titulo}</td>
                    <td>${item.categoria}</td>
                    <td><a href="MainController?op=modificar&id=${item.id}"
                           onclick="return(confirm('ESTAS SEGURO DE MODIFICAR ???'))">Modificar</a></td>
                    <td><a href="MainController?op=eliminar&id=${item.id}"
                           onclick="return(confirm('ESTAS SEGURO ???'))">Eliminar</a></td>
                </tr> 
            </c:forEach>
        </table>
    </body>
</html>

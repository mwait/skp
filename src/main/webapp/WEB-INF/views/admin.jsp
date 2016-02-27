<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>All Products</h1>
            <p class="lead"> Checkout all the awesome products avalible now!</p>
        </div>

        <h3>Produckty</h3>
    <a href="<c:url value="/admin/productInventory" />">Product Inventory</a>
<p>Tutaj mozesz obejrzec, zmodyfikowac produkty</p>
        <%@include file="/WEB-INF/views/template/footer.jsp"%>

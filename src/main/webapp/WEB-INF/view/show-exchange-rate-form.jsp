<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exchange Rate</title>

    <link type="text/css"
          rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
            integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
            integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
            crossorigin="anonymous"></script>

    <script data-require="jquery@2.0.3" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
</head>
<body>

<div class="container">
    <p/>
    <hr>
    <p/>
    <div class="row justify-content-md-center">
        <h2>Exchange <span class="badge badge-secondary">Rate Monitor</span></h2>
    </div>
    <p/>
    <hr>
    <p/>
    <div class="row border-radius">
        <div class="col">
            <form:form action="showResultFromApi" modelAttribute="formQuery" method="POST" class="form-horizontal">
                <div class="form-group">
                    <label>Currency code</label>
                    <form:input required="required" pattern="[a-zA-Z]{3}" class="form-control" path="currencyCode"
                                placeholder="Enter currency code"
                                oninvalid="this.setCustomValidity('invalid code')"
                                oninput="this.setCustomValidity('')"/>
                    <small class="form-text text-muted">Correct code format: 3 characters, ex. "EUR"</small>
                    <form:errors path="currencyCode" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label>Date From</label></td>
                    <form:input class="form-control" type="date" path="dateFrom" required="required"
                                oninvalid="this.setCustomValidity('invalid date')"
                                oninput="this.setCustomValidity('')"/>
                </div>
                <div class="form-group">
                    <label>Date To</label>
                    <form:input class="form-control" type="date" path="dateTo" required="required"
                                oninvalid="this.setCustomValidity('invalid date')"
                                oninput="this.setCustomValidity('')"/>
                </div>
                <div class="form-group">
                    <input class="btn btn-primary" type="submit" value="Get Results" class="save"/>
                </div>
            </form:form>
        </div>
        <div class="col align-self-center">
            <c:if test="${resultAttr==true}">
                <div class="jumbotron jumbotron-fluid border-radius">
                    <div class="container">
                        <h5 class="display-5">Standard Deviation: ${standardDeviation} </h5>
                        <h5 class="display-5">Bid price Average: ${averageRate} </h5>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <div class="row">
        <c:if test="${resultAttr==true}">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Date</th>
                    <th scope="col">Currency</th>
                    <th scope="col">Currency code</th>
                    <th scope="col">Bid price</th>
                    <th scope="col">Ask price</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="exchangeRate" value="${rateList}"/>
                <c:forEach items="${rateList.rates}" var="list">
                    <tr>
                        <td>${list.effectiveDate}</td>
                        <td>${exchangeRate.currency}</td>
                        <td>${exchangeRate.code}</td>
                        <td>${list.bid}</td>
                        <td>${list.ask}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:if>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/noty/3.1.4/noty.min.css"
          integrity="sha512-0p3K0H3S6Q4bEWZ/WmC94Tgit2ular2/n0ESdfEX8l172YyQj8re1Wu9s/HT9T/T2osUw5Gx/6pAZNk3UKbESw=="
          crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/css/bootstrap-select.min.css"
          integrity="sha512-ARJR74swou2y0Q2V9k0GbzQ/5vJ2RBSoCWokg4zkfM29Fb3vZEQyv0iWBMW/yvKgyHSR/7D64pFMmU8nYmbRkg==" crossorigin="anonymous" />
    <title>TODOList мечты!</title>
</head>
<body>

<div class="row">
    <nav class="col-md-2 d-none d-md-block bg-light sidebar">
        <div class="sidebar-sticky">
            <ul id="categories" class="nav flex-column">
                <li>
                    <a class="nav-link active" href="#">
                        All items
                    </a>
                </li>
                <c:forEach items="${categories}" var="cat">
                    <li>
                        <a class="nav-link" href="#" data-id="${cat.id}">
                            <c:out value="${cat.name}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>

        </div>
    </nav>
    <main class="col-md-9 ml-sm-auto col-lg-10 px-4" role="main">
        <div class="row">
            <div class="col">
                <h1 class="text-center">TODO List for job4j.ru</h1>
            </div>
        </div>
        <hr />
        <div class="row">
            <div class="col">
                <h2 class="text-center">Create new task</h2>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="input-group mb-3">
                    <input class="form-control form-control-lg" type="text" placeholder="add new task"
                           aria-label="add new task" aria-describedby="basic-addon2" id="new_task">
                    <div class="input-group-append">
                        <select id="select_category" multiple class="selectpicker" data-style="bg-white rounded-pill px-4 py-3 shadow-sm ">
                            <c:forEach items="${categories}" var = "cat" >
                                <c:if test="${cat.name == 'INBOX'}">
                                    <option selected data-id="${cat.id}"><c:out value = "${cat.name}"/></option>
                                </c:if>
                                <c:if test="${cat.name != 'INBOX'}">
                                    <option data-id="${cat.id}"><c:out value = "${cat.name}"/></option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" id="add_task">
                            <i class="bi bi-plus-square"></i></button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <h2 class="text-center">Tasks</h2>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div id="tasks"></div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <h2 class="text-center">Completed task </h2>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div id="completed_tasks"></div>
            </div>
        </div>
    </main>

</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.1/umd/popper.min.js" integrity="sha512-ubuT8Z88WxezgSqf3RLuNi5lmjstiJcyezx34yIU2gAHonIi27Na7atqzUZCOoY4CExaoFumzOsFQ2Ch+I/HCw=="
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.18/js/bootstrap-select.min.js" integrity="sha512-yDlE7vpGDP7o2eftkCiPZ+yuUyEcaBwoJoIhdXv71KZWugFqEphIS3PU60lEkFaz8RxaVsMpSvQxMBaKVwA5xg==" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous">
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/noty/3.1.4/noty.min.js"
        integrity="sha512-lOrm9FgT1LKOJRUXF3tp6QaMorJftUjowOWiDcG5GFZ/q7ukof19V0HKx/GWzXCdt9zYju3/KhBNdCLzK8b90Q=="
        crossorigin="anonymous"></script>
<script src="<c:url value="/js/index.js"/>"></script>
</body>
</html>
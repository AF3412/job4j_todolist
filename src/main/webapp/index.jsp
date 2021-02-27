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
    <title>TODOList мечты!</title>
</head>
<body>

<div class="container">
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
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous">
</script>
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
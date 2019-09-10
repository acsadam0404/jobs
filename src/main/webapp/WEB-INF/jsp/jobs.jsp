<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Jobs</title>

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript" charset="utf8"
            src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap4.min.css"/>
    <link href="/css/main.css" rel="stylesheet">
    <link href="/css/jobs.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <h1>Jobs</h1>
    <div class="header-actions">
        <a href="/jobs/create">New job</a>
    </div>
</div>


<div class="content">
    <h2>Running jobs</h2>
    <table id="runningJobstable" class="display table table-striped table-bordered">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Progress</th>
        </tr>
        </thead>
    </table>

    <h2>Available jobs</h2>

    <table id="jobstable" class="display table table-striped table-bordered">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Actions</th>
        </tr>
        </thead>
    </table>

    <h2>Finished jobs</h2>

    <table id="finishedJobstable" class="display table table-striped table-bordered">
        <thead>
        <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Finish time</th>
        </tr>
        </thead>
    </table>
</div>
<script>
    $(document).ready(function () {
        var runningJobsTable = $('#runningJobstable').DataTable({
            "paging": false,
            "ordering": false,
            "info": false,
            "sAjaxSource": "/rest/jobs/running",
            "sAjaxDataProp": "",
            "order": [[0, "asc"]],
            "aoColumns": [
                {"mData": "name"},
                {"mData": "type"},
                {"mData": "progress"}
            ]
        });
        setInterval(function () {
            runningJobsTable.ajax.reload();
        }, 1000);

        var jobsTable = $('#jobstable').DataTable({
            "paging": false,
            "ordering": false,
            "info": false,
            "sAjaxSource": "/rest/jobs",
            "sAjaxDataProp": "",
            "order": [[0, "asc"]],
            "aoColumns": [
                {"mData": "name"},
                {"mData": "type"},
                {"mData": null}
            ],
            "aoColumnDefs": [{
                "aTargets": -1,
                "mData": null,
                "defaultContent": "<button >Execute</button>"
            }]
        });

        $('#jobstable tbody').on('click', 'button', function () {
            var data = JSON.stringify(jobsTable.row($(this).parents('tr')).data());
            $.ajax({
                url: "/rest/jobs/run",
                type: "POST",
                data: data,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (success) {
                    console.log(success);
                },
                error: function(error) {
                    if (error.responseText) {
                        console.log(error.responseText);
                        alert(error.responseText);
                    }
                }
            })
        });

        var finishedJobsTable = $('#finishedJobstable').DataTable({
            "paging": false,
            "ordering": false,
            "info": false,
            "sAjaxSource": "/rest/jobs/finished",
            "sAjaxDataProp": "",
            "order": [[0, "asc"]],
            "aoColumns": [
                {"mData": "name"},
                {"mData": "type"},
                {"mData": "finishTime"}
            ]
        });
        setInterval(function () {
            finishedJobsTable.ajax.reload();
        }, 1000);
    });
</script>

</body>
</html>
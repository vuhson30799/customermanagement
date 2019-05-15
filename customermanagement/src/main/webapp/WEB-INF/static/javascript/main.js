$(document).ready(function () {

    $("#form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search;
    search = $("#lastName").val();
    //search["email"] = $("#email").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/customers/find",
        data: search,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var i,
                customers = data.customers;
            var json = "<h4>Ajax Response</h4>";

            json += "<table>";
            for (i = 0; i <data.sizeCustomers; i++){
                json += "<tr>"
                    +"<td>"+
                        customers[i].lastName
                    +"</td><td>"+
                    customers[i].firstName
                    +"</td></tr>";
            }
            json += ("</table>");
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
        }
    });

}
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>New order</title>

    <script type="text/javascript" src="http://www.websitecodetutorials.com/code/jquery-plugins/jquery.js"></script>
    <script type="text/javascript" src="http://www.websitecodetutorials.com/code/jquery-plugins/validation.js"></script>

    <script type="text/javascript">
        /* Fire Valaidate */
        $(document).ready(function () {
            $.validator.addMethod("nourl",
                function (value, element) {
                    return !/http\:\/\/|www\.|link\=|url\=/.test(value);
                },
                "No URL's"
            );
            $(".form.b").validate({
                rules: {
                    name: {
                        required: true
                    }
                },
                messages: {
                    name: "Required Field"
                }
            });
        });
    </script>

    <style type="text/css">
        h1, h2 {
            text-align: center;
            line-height: 20px;
        }

        /* ----------------- Form ----------------- */
        .form {
            width: 310px;
            margin: 50px auto;
            border: 1px solid #333;
            padding: 15px 70px 45px;
            position: relative;
            background: #C8E8FB;
        }

        * html .form {
            padding-top: 45px;
        }

        * + html .form {
            padding-top: 45px;
        }

        .form h3 {
            position: absolute;
            top: -11px;
            left: 35px;
            padding: 0 8px;
            background: #fff;
            margin: 0;
            border: 1px solid #333;
        }

        .form input {
            display: block;
            padding: 6px 4px;
            width: 300px;
            outline: 0;
            border: 1px solid #333;
            background: #fff;
        }

        .form label {
            display: block;
            margin: 30px 0 4px;
        }

        .form textarea {
            display: block;
            width: 308px;
            height: 100px;
            outline: 0;
            border: 1px solid #333;
            overflow: auto;
        }

        .form input.submit {
            width: 100px;
            cursor: pointer;
            margin: 40px 0 0;
        }

        /* ----------- jQuery Validation ----------- */
        /* --- Form B --- */
        .form.b label.error {
            float: left;
            margin: 0;
            color: red;
        }
    </style>
</head>

<body>
<spring:url value="/users/add" var="userActionUrl"/>

<form:form cssClass="form b" method="post" modelAttribute="userForm" action="${userActionUrl}">
<label for="number">Number</label>
<form:input path="number" type="text"/><p>

    <label for="description">Description</label>
<form:input path="description" type="text"/><p>

    <label for="amount">Amount</label>
<form:input path="amount" type="number"/><p>

    <form:button>Add</form:button>
</form:form>
</body>
</html>

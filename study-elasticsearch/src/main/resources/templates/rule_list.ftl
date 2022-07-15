<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>rule</title>
    <script src="/jquery.min.js"></script>
    <style type="text/css">
        .table {
            border-collapse: collapse;
            font-size: 13px;
            height: 24px;
            line-height: 24px;
            color: #09F;
            text-align: center;
        }

        .table tr th {
            background: #36F;
            color: #FFF;
            font-size: 13px;
            height: 24px;
            line-height: 24px;
        }

        .table tr th.th_border {
            border-right: solid 1px #FFF;
            border-left: solid 1px #36F;
        }

        .table tr td {
            border: solid 1px #36F;
        }

        a {
            display: inline-block;
            margin: 0px 0px 5px 5px;
            padding: 6px 8px;
            font-size: 14px;
            outline: none;
            text-align: center;
            width: 50px;
            line-height: 30px;
            cursor: pointer;
        }

        a.btn_a:hover {
            color: white;
            background-color: rgba(0, 64, 156, .8);
        }

        a.btn_b:hover {
            color: white;
            background-color: rgba(255, 0, 0, .8);
        }
    </style>
</head>
<body>
<div id="connect-container">
    <table class="table">
        <tr>
            <th width="10%">规则类型</th>
            <th width="10%">匹配方式</th>
            <th width="20%">说明</th>
            <th width="50%">例句</th>
        </tr>
        <#list ruleList as rule>
        <#--判断下标为第一行时，设置字体颜色-->
            <tr>
                <td width="10%">${rule.ruleType}</td>
                <td width="10%">${rule.likeMode}</td>
                <td width="20%">${rule.remark?if_exists}</td>
                <td width="50%">${rule.sentence}</td>
            </tr>
        </#list>
    </table>
</div>


<script>

</script>
</body>
</html>

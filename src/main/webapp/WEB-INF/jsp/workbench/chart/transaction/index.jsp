<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>

<head>

    <base href="<%=basePath%>">
    <title>title</title>
    <script src="jquery/jquery-1.11.1-min.js"></script>
    <script src="ECharts/echarts.min.js"></script>
    <script>


        function getChart() {

            $.ajax({

                url:'workbench/chart/transaction/getChartData.do',
                type:'get',
                dataType:'json',
                success:function (data) {

                    var myChart = echarts.init(document.getElementById("main"));

                    var option = {

                        title:{

                            text:'交易漏斗图',
                            subtext:'统计交易阶段数量的漏斗图'

                        },

                        legend:{

                            data:data.stageNameList

                        },

                        series:[

                            {

                                name:'交易漏斗图',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                bottom: 60,
                                width: '80%',
                                min: 0,
                                max: data.max,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,

                                label: {
                                    show: true,
                                    position: 'inside'
                                },

                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },

                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },

                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },

                                data:data.dataList

                            },

                        ]

                    };

                    myChart.setOption(option);

                }

            });

        }

    </script>

</head>

<body>

    <div id="main" style="height: 700px; width: 900px;"></div>

</body>

</html>
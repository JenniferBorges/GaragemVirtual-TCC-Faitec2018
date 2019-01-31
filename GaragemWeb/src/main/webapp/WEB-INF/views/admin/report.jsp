<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.academico.garagem.model.enumeration.EImage"%>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <jsp:include page="../common/head.jsp" flush="true">
            <jsp:param name="title" value="Lista de Garagens"/>
        </jsp:include>
    </head>
    <body>
        <div class="container-fluid min-100 d-flex flex-column p-0">
            <jsp:include page="../common/nav.jsp"/>
            <div class="main-content">  
                <div class="container-fluid my-5">
                    <!-- Card stats -->
                    <div class="row">
                        <div class="col-xl-3 col-lg-6">
                            <div class="card card-stats mb-4 mb-xl-0">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col">
                                            <h5 class="card-title text-uppercase text-muted mb-0">Traffic</h5>
                                            <span class="h2 font-weight-bold mb-0">350,897</span>
                                        </div>
                                        <div class="col-auto">
                                            <div class="icon icon-shape bg-danger text-white rounded-circle shadow">
                                                <i class="fas fa-chart-bar"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <p class="mt-3 mb-0 text-muted text-sm">
                                        <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                                        <span class="text-nowrap">Since last month</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-lg-6">
                            <div class="card card-stats mb-4 mb-xl-0">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col">
                                            <h5 class="card-title text-uppercase text-muted mb-0">New users</h5>
                                            <span class="h2 font-weight-bold mb-0">2,356</span>
                                        </div>
                                        <div class="col-auto">
                                            <div class="icon icon-shape bg-warning text-white rounded-circle shadow">
                                                <i class="fas fa-chart-pie"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <p class="mt-3 mb-0 text-muted text-sm">
                                        <span class="text-danger mr-2"><i class="fas fa-arrow-down"></i> 3.48%</span>
                                        <span class="text-nowrap">Since last week</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-lg-6">
                            <div class="card card-stats mb-4 mb-xl-0">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col">
                                            <h5 class="card-title text-uppercase text-muted mb-0">Sales</h5>
                                            <span class="h2 font-weight-bold mb-0">924</span>
                                        </div>
                                        <div class="col-auto">
                                            <div class="icon icon-shape bg-yellow text-white rounded-circle shadow">
                                                <i class="fas fa-users"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <p class="mt-3 mb-0 text-muted text-sm">
                                        <span class="text-warning mr-2"><i class="fas fa-arrow-down"></i> 1.10%</span>
                                        <span class="text-nowrap">Since yesterday</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-lg-6">
                            <div class="card card-stats mb-4 mb-xl-0">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col">
                                            <h5 class="card-title text-uppercase text-muted mb-0">Performance</h5>
                                            <span class="h2 font-weight-bold mb-0">49,65%</span>
                                        </div>
                                        <div class="col-auto">
                                            <div class="icon icon-shape bg-info text-white rounded-circle shadow">
                                                <i class="fas fa-percent"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <p class="mt-3 mb-0 text-muted text-sm">
                                        <span class="text-success mr-2"><i class="fas fa-arrow-up"></i> 12%</span>
                                        <span class="text-nowrap">Since last month</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-xl-8 mb-5 mb-xl-0">
                            <div class="card bg-gradient-default shadow">
                                <div class="card-header bg-transparent">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-uppercase text-light ls-1 mb-1">Overview</h6>
                                            <h2 class="text-white mb-0">Sales value</h2>
                                        </div>
                                        <div class="col">
                                            <ul class="nav nav-pills justify-content-end">
                                                <li class="nav-item mr-2 mr-md-0" data-toggle="chart" data-target="#chart-sales" data-update='{"data":{"datasets":[{"data":[0, 20, 10, 30, 15, 40, 20, 60, 60]}]}}' data-prefix="$" data-suffix="k">
                                                    <a href="#" class="nav-link py-2 px-3 active" data-toggle="tab">
                                                        <span class="d-none d-md-block">Month</span>
                                                        <span class="d-md-none">M</span>
                                                    </a>
                                                </li>
                                                <li class="nav-item" data-toggle="chart" data-target="#chart-sales" data-update='{"data":{"datasets":[{"data":[0, 20, 5, 25, 10, 30, 15, 40, 40]}]}}' data-prefix="$" data-suffix="k">
                                                    <a href="#" class="nav-link py-2 px-3" data-toggle="tab">
                                                        <span class="d-none d-md-block">Week</span>
                                                        <span class="d-md-none">W</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <!-- Chart -->
                                    <div class="chart">
                                        <!-- Chart wrapper -->
                                        <canvas id="chart-sales" class="chart-canvas"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-4">
                            <div class="card shadow">
                                <div class="card-header bg-transparent">
                                    <div class="row align-items-center">
                                        <div class="col">
                                            <h6 class="text-uppercase text-muted ls-1 mb-1">Performance</h6>
                                            <h2 class="mb-0">Total orders</h2>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <!-- Chart -->
                                    <div class="chart">
                                        <canvas id="chart-orders" class="chart-canvas"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../common/wrapper.jsp"/>
        <script type="text/javascript" src="<c:url value="/resources/js/Chart.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/Chart.extension.js"/>"></script>
        <script>

//
// Charts
//

            'use strict';

            var Charts = (function () {

                // Variable

                var $toggle = $('[data-toggle="chart"]');
                var mode = 'light';//(themeMode) ? themeMode : 'light';
                var fonts = {
                    base: 'Open Sans'
                }

                // Colors
                var colors = {
                    gray: {
                        100: '#f6f9fc',
                        200: '#e9ecef',
                        300: '#dee2e6',
                        400: '#ced4da',
                        500: '#adb5bd',
                        600: '#8898aa',
                        700: '#525f7f',
                        800: '#32325d',
                        900: '#212529'
                    },
                    theme: {
                        'default': '#172b4d',
                        'primary': '#5e72e4',
                        'secondary': '#f4f5f7',
                        'info': '#11cdef',
                        'success': '#2dce89',
                        'danger': '#f5365c',
                        'warning': '#fb6340'
                    },
                    black: '#12263F',
                    white: '#FFFFFF',
                    transparent: 'transparent',
                };


                // Methods

                // Chart.js global options
                function chartOptions() {

                    // Options
                    var options = {
                        defaults: {
                            global: {
                                responsive: true,
                                maintainAspectRatio: false,
                                defaultColor: (mode == 'dark') ? colors.gray[700] : colors.gray[600],
                                defaultFontColor: (mode == 'dark') ? colors.gray[700] : colors.gray[600],
                                defaultFontFamily: fonts.base,
                                defaultFontSize: 13,
                                layout: {
                                    padding: 0
                                },
                                legend: {
                                    display: false,
                                    position: 'bottom',
                                    labels: {
                                        usePointStyle: true,
                                        padding: 16
                                    }
                                },
                                elements: {
                                    point: {
                                        radius: 0,
                                        backgroundColor: colors.theme['primary']
                                    },
                                    line: {
                                        tension: .4,
                                        borderWidth: 4,
                                        borderColor: colors.theme['primary'],
                                        backgroundColor: colors.transparent,
                                        borderCapStyle: 'rounded'
                                    },
                                    rectangle: {
                                        backgroundColor: colors.theme['warning']
                                    },
                                    arc: {
                                        backgroundColor: colors.theme['primary'],
                                        borderColor: (mode == 'dark') ? colors.gray[800] : colors.white,
                                        borderWidth: 4
                                    }
                                },
                                tooltips: {
                                    enabled: false,
                                    mode: 'index',
                                    intersect: false,
                                    custom: function (model) {

                                        // Get tooltip
                                        var $tooltip = $('#chart-tooltip');

                                        // Create tooltip on first render
                                        if (!$tooltip.length) {
                                            $tooltip = $('<div id="chart-tooltip" class="popover bs-popover-top" role="tooltip"></div>');

                                            // Append to body
                                            $('body').append($tooltip);
                                        }

                                        // Hide if no tooltip
                                        if (model.opacity === 0) {
                                            $tooltip.css('display', 'none');
                                            return;
                                        }

                                        function getBody(bodyItem) {
                                            return bodyItem.lines;
                                        }

                                        // Fill with content
                                        if (model.body) {
                                            var titleLines = model.title || [];
                                            var bodyLines = model.body.map(getBody);
                                            var html = '';

                                            // Add arrow
                                            html += '<div class="arrow"></div>';

                                            // Add header
                                            titleLines.forEach(function (title) {
                                                html += '<h3 class="popover-header text-center">' + title + '</h3>';
                                            });

                                            // Add body
                                            bodyLines.forEach(function (body, i) {
                                                var colors = model.labelColors[i];
                                                var styles = 'background-color: ' + colors.backgroundColor;
                                                var indicator = '<span class="badge badge-dot"><i class="bg-primary"></i></span>';
                                                var align = (bodyLines.length > 1) ? 'justify-content-left' : 'justify-content-center';
                                                html += '<div class="popover-body d-flex align-items-center ' + align + '">' + indicator + body + '</div>';
                                            });

                                            $tooltip.html(html);
                                        }

                                        // Get tooltip position
                                        var $canvas = $(this._chart.canvas);

                                        var canvasWidth = $canvas.outerWidth();
                                        var canvasHeight = $canvas.outerHeight();

                                        var canvasTop = $canvas.offset().top;
                                        var canvasLeft = $canvas.offset().left;

                                        var tooltipWidth = $tooltip.outerWidth();
                                        var tooltipHeight = $tooltip.outerHeight();

                                        var top = canvasTop + model.caretY - tooltipHeight - 16;
                                        var left = canvasLeft + model.caretX - tooltipWidth / 2;

                                        // Display tooltip
                                        $tooltip.css({
                                            'top': top + 'px',
                                            'left': left + 'px',
                                            'display': 'block',
                                            'z-index': '100'
                                        });

                                    },
                                    callbacks: {
                                        label: function (item, data) {
                                            var label = data.datasets[item.datasetIndex].label || '';
                                            var yLabel = item.yLabel;
                                            var content = '';

                                            if (data.datasets.length > 1) {
                                                content += '<span class="badge badge-primary mr-auto">' + label + '</span>';
                                            }

                                            content += '<span class="popover-body-value">' + yLabel + '</span>';
                                            return content;
                                        }
                                    }
                                }
                            },
                            doughnut: {
                                cutoutPercentage: 83,
                                tooltips: {
                                    callbacks: {
                                        title: function (item, data) {
                                            var title = data.labels[item[0].index];
                                            return title;
                                        },
                                        label: function (item, data) {
                                            var value = data.datasets[0].data[item.index];
                                            var content = '';

                                            content += '<span class="popover-body-value">' + value + '</span>';
                                            return content;
                                        }
                                    }
                                },
                                legendCallback: function (chart) {
                                    var data = chart.data;
                                    var content = '';

                                    data.labels.forEach(function (label, index) {
                                        var bgColor = data.datasets[0].backgroundColor[index];

                                        content += '<span class="chart-legend-item">';
                                        content += '<i class="chart-legend-indicator" style="background-color: ' + bgColor + '"></i>';
                                        content += label;
                                        content += '</span>';
                                    });

                                    return content;
                                }
                            }
                        }
                    }

                    // yAxes
                    Chart.scaleService.updateScaleDefaults('linear', {
                        gridLines: {
                            borderDash: [2],
                            borderDashOffset: [2],
                            color: (mode == 'dark') ? colors.gray[900] : colors.gray[300],
                            drawBorder: false,
                            drawTicks: false,
                            lineWidth: 0,
                            zeroLineWidth: 0,
                            zeroLineColor: (mode == 'dark') ? colors.gray[900] : colors.gray[300],
                            zeroLineBorderDash: [2],
                            zeroLineBorderDashOffset: [2]
                        },
                        ticks: {
                            beginAtZero: true,
                            padding: 10,
                            callback: function (value) {
                                if (!(value % 10)) {
                                    return value
                                }
                            }
                        }
                    });

                    // xAxes
                    Chart.scaleService.updateScaleDefaults('category', {
                        gridLines: {
                            drawBorder: false,
                            drawOnChartArea: false,
                            drawTicks: false
                        },
                        ticks: {
                            padding: 20
                        },
                        maxBarThickness: 10
                    });

                    return options;

                }

                // Parse global options
                function parseOptions(parent, options) {
                    for (var item in options) {
                        if (typeof options[item] !== 'object') {
                            parent[item] = options[item];
                        } else {
                            parseOptions(parent[item], options[item]);
                        }
                    }
                }

                // Push options
                function pushOptions(parent, options) {
                    for (var item in options) {
                        if (Array.isArray(options[item])) {
                            options[item].forEach(function (data) {
                                parent[item].push(data);
                            });
                        } else {
                            pushOptions(parent[item], options[item]);
                        }
                    }
                }

                // Pop options
                function popOptions(parent, options) {
                    for (var item in options) {
                        if (Array.isArray(options[item])) {
                            options[item].forEach(function (data) {
                                parent[item].pop();
                            });
                        } else {
                            popOptions(parent[item], options[item]);
                        }
                    }
                }

                // Toggle options
                function toggleOptions(elem) {
                    var options = elem.data('add');
                    var $target = $(elem.data('target'));
                    var $chart = $target.data('chart');

                    if (elem.is(':checked')) {

                        // Add options
                        pushOptions($chart, options);

                        // Update chart
                        $chart.update();
                    } else {

                        // Remove options
                        popOptions($chart, options);

                        // Update chart
                        $chart.update();
                    }
                }

                // Update options
                function updateOptions(elem) {
                    var options = elem.data('update');
                    var $target = $(elem.data('target'));
                    var $chart = $target.data('chart');

                    // Parse options
                    parseOptions($chart, options);

                    // Toggle ticks
                    toggleTicks(elem, $chart);

                    // Update chart
                    $chart.update();
                }

                // Toggle ticks
                function toggleTicks(elem, $chart) {

                    if (elem.data('prefix') !== undefined || elem.data('prefix') !== undefined) {
                        var prefix = elem.data('prefix') ? elem.data('prefix') : '';
                        var suffix = elem.data('suffix') ? elem.data('suffix') : '';

                        // Update ticks
                        $chart.options.scales.yAxes[0].ticks.callback = function (value) {
                            if (!(value % 10)) {
                                return prefix + value + suffix;
                            }
                        }

                        // Update tooltips
                        $chart.options.tooltips.callbacks.label = function (item, data) {
                            var label = data.datasets[item.datasetIndex].label || '';
                            var yLabel = item.yLabel;
                            var content = '';

                            if (data.datasets.length > 1) {
                                content += '<span class="popover-body-label mr-auto">' + label + '</span>';
                            }

                            content += '<span class="popover-body-value">' + prefix + yLabel + suffix + '</span>';
                            return content;
                        }

                    }
                }


                // Events

                // Parse global options
                if (window.Chart) {
                    parseOptions(Chart, chartOptions());
                }

                // Toggle options
                $toggle.on({
                    'change': function () {
                        var $this = $(this);

                        if ($this.is('[data-add]')) {
                            toggleOptions($this);
                        }
                    },
                    'click': function () {
                        var $this = $(this);

                        if ($this.is('[data-update]')) {
                            updateOptions($this);
                        }
                    }
                });


                // Return

                return {
                    colors: colors,
                    fonts: fonts,
                    mode: mode
                };

            })();

//
// Orders chart
//

            var OrdersChart = (function () {

                //
                // Variables
                //

                var $chart = $('#chart-orders');
                var $ordersSelect = $('[name="ordersSelect"]');


                //
                // Methods
                //

                // Init chart
                function initChart($chart) {

                    // Create chart
                    var ordersChart = new Chart($chart, {
                        type: 'bar',
                        options: {
                            scales: {
                                yAxes: [{
                                        ticks: {
                                            callback: function (value) {
                                                if (!(value % 10)) {
                                                    //return '$' + value + 'k'
                                                    return value
                                                }
                                            }
                                        }
                                    }]
                            },
                            tooltips: {
                                callbacks: {
                                    label: function (item, data) {
                                        var label = data.datasets[item.datasetIndex].label || '';
                                        var yLabel = item.yLabel;
                                        var content = '';

                                        if (data.datasets.length > 1) {
                                            content += '<span class="popover-body-label mr-auto">' + label + '</span>';
                                        }

                                        content += '<span class="popover-body-value">' + yLabel + '</span>';

                                        return content;
                                    }
                                }
                            }
                        },
                        data: {
                            labels: ['Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                            datasets: [{
                                    label: 'Sales',
                                    data: [25, 20, 30, 22, 17, 29]
                                }]
                        }
                    });

                    // Save to jQuery object
                    $chart.data('chart', ordersChart);
                }


                // Init chart
                if ($chart.length) {
                    initChart($chart);
                }

            })();

//
// Charts
//

            'use strict';

//
// Sales chart
//

            var SalesChart = (function () {

                // Variables

                var $chart = $('#chart-sales');


                // Methods

                function init($chart) {

                    var salesChart = new Chart($chart, {
                        type: 'line',
                        options: {
                            scales: {
                                yAxes: [{
                                        gridLines: {
                                            color: Charts.colors.gray[900],
                                            zeroLineColor: Charts.colors.gray[900]
                                        },
                                        ticks: {
                                            callback: function (value) {
                                                if (!(value % 10)) {
                                                    return '$' + value + 'k';
                                                }
                                            }
                                        }
                                    }]
                            },
                            tooltips: {
                                callbacks: {
                                    label: function (item, data) {
                                        var label = data.datasets[item.datasetIndex].label || '';
                                        var yLabel = item.yLabel;
                                        var content = '';

                                        if (data.datasets.length > 1) {
                                            content += '<span class="popover-body-label mr-auto">' + label + '</span>';
                                        }

                                        content += '<span class="popover-body-value">$' + yLabel + 'k</span>';
                                        return content;
                                    }
                                }
                            }
                        },
                        data: {
                            labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                            datasets: [{
                                    label: 'Performance',
                                    data: [0, 20, 10, 30, 15, 40, 20, 60, 60]
                                }]
                        }
                    });

                    // Save to jQuery object

                    $chart.data('chart', salesChart);

                }
                ;


                // Events

                if ($chart.length) {
                    init($chart);
                }

            })();
        </script>
    </body>
</html>
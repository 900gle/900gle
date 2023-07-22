const lecture = {
    init: function () {
        var _this = this;
    }, // init end
    search: function () {
        let columns = $("#columns").val();
        if(columns.length < 1){
            alert("컬럼을 선택하여 주십시오.");
            return false;
        }
        let lecture_name = $("#search-word").val();
        let order = $("#order").val();
        let order_field = $("#order-field").val();
        let limit = $("#limit").val();
        let data = {columns: columns.join(','), lectureName: lecture_name, order: order + order_field, limit: limit}
        const config = {
            headers: {'content-type': 'application/json'}
        }
        return axios.get($("#search").val() + '/api/lecture', {params: data}, config)
            .then(function (response) {
                console.log('response', response);
                let html = "";
                $("#result-area").show();
                $("#result-table").html("");

                response.data.list.map((value, index) => {
                    if(index ==0){
                        html += "<thead><tr>";
                        value.map((v, i) => {
                            html += "<th>" + v + "</th>";
                        });
                        html += "</tr></thead>";
                    } else {
                        html += "<tr>";
                        value.map((v, i) => {
                            html += "<td>" + v + "</td>";
                        });
                        html += "</tr>";
                    }
                });
                $("#result-table").html(html);
            });
    },
    get: {
        columns: function () {

            let homeTeam = $.trim($("#home_team").val());
            let awayTeam = $.trim($("#away_team").val());
            let data = {home_team: homeTeam, away_team: awayTeam}
            const config = {
                headers: {'content-type': 'application/json'}
            }

            return axios.get($("#search").val() + '/api/lecture/columns', data, config)
                .then(function (response) {


                    console.log('response', response);
                    // $("#columns").html("");

                    let html = "<option selected>Alabama</option>";


                    response.data.list.map((value, index) => {
                        html += "<option>value</option>";

                    });
                    // $("#columns").html(html);


                });
        }
    }
};

//
// SELECT * FROM (
//     SELECT row_number() over() AS rn, *
// FROM (
//     SELECT *
//     FROM "s3|ets|database"."utf8testing_lecture"
// ORDER BY lecture_id ASC
// )
// )
// WHERE rn BETWEEN 5 AND 10;
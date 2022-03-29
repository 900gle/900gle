const search = {
    init: function () {
        var _this = this;
    }, // init end
    form: {
        send: function () {
            var form = $('#file-form')[0];
            var formData = new FormData(form);
            formData.append("file", $("#file")[0].files[0]);
            const config = {
                headers: {'content-type': 'multipart/form-data'}
            }



            return axios.post( $("#host").val()+'/api/images/opencv', formData, config)
                .then(function (response) {
                    $("#result-list").html("");
                    let html = "";
                    response.data.list.map((value, index) => {
                        html += "<tr>" +
                            "<td><a href='" + value.name + "' target='_blank'><img src='" + value.image + "' style='width: 100px; height: 100px;' onclick=''/></a></td>" +
                            "<td>" + value.score + "</td>" +
                            "<td>" + value.name + "</td>" +
                            "</tr>";
                    });
                    $("#result-list").html(html);
                    console.log('response', response.data.list);
                });
        }
    }


};
const main = {
    init: function () {


        var _this = this;
        this.list.search();
    }, // init end

    list: {
        search: function () {
            var searchWord = $("#searchWord").val();
            var data = {searchWord: searchWord}
            const config = {
                headers: {'content-type': 'application/json'}
            }
            return axios.get($("#api").val() + '/api/search', {params: data}, config)
                .then(function (response) {
                    $("#scroll-list").html("");
                    let html = "";
                    response.data.list.map((value, index) => {


                        console.log("value : ",value);

                        html += "<div className=\"col mb-5\">" +
                            "<div className=\"card h-100\">" +
                            "<div className=\"badge bg-dark text-white position-absolute\" style=\"top: 0.5rem; right: 0.5rem\">Sale" +
                            "</div>" +
                            "<img className=\"card-img-top\" src=\"" + value.image + "\" alt=\"...\" style=\"width:450px; height:300px;\" />" +
                            "<div className=\"card-body p-4\">" +
                            "<div className=\"text-center\">" +
                            "<h5 className=\"fw-bolder\"><a href='/detail/"+value.id+"'>" + value.name + "</a></h5>" +
                            "<div className=\"d-flex justify-content-center small text-warning mb-2\">" +
                            "<div className=\"bi-star-fill\"></div>" +
                            "<div className=\"bi-star-fill\"></div>" +
                            "<div className=\"bi-star-fill\"></div>" +
                            "<div className=\"bi-star-fill\"></div>" +
                            "<div className=\"bi-star-fill\"></div>" +
                            "</div>" +
                            "<span className=\"text-muted text-decoration-line-through\">"+value.price+"</span>" +
                            "$18.00</div>" +
                            "</div>" +
                            "<div className=\"card-footer p-4 pt-0 border-top-0 bg-transparent\">" +
                            "<div className=\"text-center\"><a className=\"btn btn-outline-dark mt-auto\" href=\"#\">Add to cart</a></div>" +
                            "</div>" +
                            "</div>" +
                            "</div>";


                    });
                    $("#scroll-list").html(html).trigger('create');
                    console.log('response', response.data.list);
                });
        },
        delete: function (id) {
            var data = {indexKey: "opencv", documentId: id}
            const config = {
                headers: {'content-type': 'application/json'}
            }
            return axios.delete($("#host").val() + '/indexer/data', {params: data}, config)
                .then(function (response) {
                    alert(response.data.msg);
                    console.log('response', response.data.list);
                });
        }
    }

};
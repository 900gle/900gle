var keyword = {
    init: function () {
        var _this = this;

    }, // init end
    list: {
        search: function () {
            var data = {};
            const config = {
                headers: {'content-type': 'application/json'}
            }
            return axios.get($("#api").val() + '/manage/keywords', {params: data}, config).then(function (response) {
                return response.data;
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




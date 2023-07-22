const dash = {
    init: function () {
        var _this = this;
        _this.get();
    }, // init end
    get: function () {
        return axios.get(dash.data.host() + "/v1/static/users")
            .then(function (response) {
                console.log('response : ', response.data);
                if (response.data.success == true) {
                    $("#user-count").text(response.data.data.user);
                    $("#role-count").text(response.data.data.role);
                    $("#auth-count").text(response.data.data.auth);
                }
            });
    },
    data: {
        host: function () {
            let host = $.trim($("#host").val());
            if (host.length < 1) {
                toastr.error('서버(host) 필수 정보가 존재하지않습니다.')
                return false;
            }
            return host;
        }
    }
};


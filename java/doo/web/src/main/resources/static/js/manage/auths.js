const auths = {
    init: function () {
        var _this = this;
    }, // init end
    submit: function () {

        if (auths.data.auth() == false) {
            return false;
        }

        const data = new URLSearchParams();
        data.append('name', auths.data.auth());
        data.append('discription', auths.data.discription());

        const config = {
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
            }
        }

        return axios.post(auths.data.host() + "/v1/auth", data, config)
            .then(function (response) {
                console.log(response);
                if (response.data.success == true) {
                    alert('권한정보가 등록되었습니다.');
                    window.location.href="/auths";
                }
            });
    },
    data: {
        auth: function () {
            let role = $.trim($("#auth").val());
            if (role.length < 1) {
                toastr.error('Auth 정보가 존재하지않습니다. Auth Name을 해 주세요.')
                return false;
            }
            return role;
        },

        discription: function () {
            let discription = $.trim($("#discription").val());
            if (discription.length < 1) {
                toastr.error('discription 정보가 존재하지않습니다. 설명을 입해 주세요.')
                return false;
            }
            return discription;
        },

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


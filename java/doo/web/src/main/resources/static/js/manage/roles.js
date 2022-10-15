const roles = {
    init: function () {
        var _this = this;
    }, // init end
    submit: function () {

        if (roles.data.role() == false) {
            return false;
        }

        const data = new URLSearchParams();
        data.append('name', roles.data.role());
        data.append('discription', roles.data.discription());

        const config = {
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
            }
        }

        return axios.post(roles.data.host() + "/v1/role", data, config)
            .then(function (response) {
                console.log(response);
                alert('Role 정보가 등록되었습니다.');
                window.location.href="/roles";
            });
    },
    data: {
        role: function () {
            let role = $.trim($("#role").val());
            if (role.length < 1) {
                toastr.error('Role 정보가 존재하지않습니다. Role Name을 해 주세요.');
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


const users = {
    init: function () {
        var _this = this;
    }, // init end
    submit: function () {

        if (users.data.id() == false || users.data.password() == false) {
            return false;
        }

        const data = new URLSearchParams();
        data.append('id', users.data.id());
        data.append('name', users.data.name());
        data.append('password', users.data.password());
        data.append('role', users.data.role());
        data.append('auths', users.data.auths());

        const config = {
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
            }
        }
        return axios.post(users.data.host() + "/v1/signup", data, config)
            .then(function (response) {
                console.log('response : ', response);
                window.location.href = "/login";
            });
    },
    data: {
        role: function () {
            let role = $.trim($("#role").val());
            if (role.length < 1) {
                toastr.error('Role 정보가 존재하지않습니다. Role를 선택해 주세요.')
                return false;
            }
            return role;
        },
        auths: function () {
            let auths = $("#auths").val();
            if (auths.length < 1) {
                toastr.error('Auths 정보가 존재하지않습니다. Auths를 선택해 주세요.')
                return false;
            }
            return auths.join(',');
        },
        id: function () {
            let id = $.trim($("#id").val());
            if (id.length < 1) {
                toastr.error('회원아이디 정보가 존재하지않습니다. 아이디를 입력해 주세요.')
                return false;
            }
            return id;
        },
        name: function () {
            let name = $.trim($("#name").val());
            if (name.length < 1) {
                toastr.error('이름 정보가 존재하지않습니다. 이름를 입력해 주세요.')
                return false;
            }
            return name;
        },
        password: function () {
            let password = $.trim($("#password").val());
            if (password.length < 1) {
                toastr.error('패스워드 정보가 존재하지않습니다. 패스워드를 입력해 주세요.')
                return false;
            }
            return password;
        },
        retypePassword: function () {
            let retypePassword = $.trim($("#retypePassword").val());
            if (retypePassword.length < 1) {
                toastr.error('패스워드 정보가 존재하지않습니다. 패스워드를 입력해 주세요.')
                return false;
            }
            return retypePassword;
        },
        host: function () {
            return $("#host").val();
        }
    }
};


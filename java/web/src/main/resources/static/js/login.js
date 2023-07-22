const login = {
    init: function () {
        var _this = this;
    }, // init end
    submit: function () {

        if (login.data.id() == false || login.data.password() == false) {
            return false;
        }
        const data = new URLSearchParams();
        data.append('id', login.data.id());
        data.append('password', login.data.password());
        const config = {
            headers: {"Accept": "application/json, text/plain, */*",
                "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"}
        }
        return axios.post(login.data.host() + "/v1/signin", data, config)
            .then(function (response) {

                console.log('response : ',response.data);
                console.log('response success : ',response.data.success);

                if (response.data.success == true) {
                    window.location.href="/loginProcess?token="+response.data.data;
                }
            });
    },
    data: {
        id: function () {
            let id = $.trim($("#id").val());
            if (id.length < 1) {
                //toastr.error('회원아이디 정보가 존재하지않습니다. 아이디를 입력해 주세요.')
                return false;
            }
            return id;
        },
        password: function () {
            let password = $.trim($("#password").val());
            if (password.length < 1) {
                //toastr.error('패스워드 정보가 존재하지않습니다. 패스워드를 입력해 주요.')
                return false;
            }
            return password;
        },
        host: function () {
            return $("#host").val();
        },
        login: function () {
            return $("#login").val();
        }
    }
};


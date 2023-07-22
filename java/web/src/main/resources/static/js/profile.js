const profile = {
    init: function () {
        var _this = this;
    }, // init end

    update: function () {

        if (!profile.data.name() || !profile.data.role() || !profile.data.auths() || !profile.data.msrl()) {
            return false;
        }

        const data = new URLSearchParams();
        data.append('name', profile.data.name());
        data.append('role', profile.data.role());
        data.append('auths', profile.data.auths());
        data.append('msrl', profile.data.msrl());

        const config = {
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
            }
        }

        return axios.put(profile.data.host() + "/v1/user", data, config)
            .then(function (response) {

                console.log('response : ', response.data);
                console.log('response success : ', response.data.success);

                if (response.data.success == true) {
                    const config = {
                        headers: {
                            "Accept": "application/json, text/plain, */*",
                            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
                            "X-AUTH-TOKEN": response.data.data
                        }
                    }
                    return axios.put(profile.data.host() + "/v1/user", data, config)
                        .then(function (response) {
                            console.log('response : ', response.data);
                            if (response.data.success == true) {
                                alert("정보가 변경되었습니다.");
                            } else {
                                toastr.error('정보변경에 실패하였습니다')
                            }
                            location.reload();
                        });
                }
            });
    },

    submit: function () {

        if (profile.data.id() == false || profile.data.password() == false) {
            return false;
        }

        const data = new URLSearchParams();
        data.append('id', profile.data.id());
        data.append('name', profile.data.name());
        data.append('password', profile.data.password());
        data.append('role', profile.data.role());
        data.append('auths', profile.data.auths());
        data.append('msrl', profile.data.msrl());

        const config = {
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
            }
        }

        return axios.post(profile.data.host() + "/v1/signin", data, config)
            .then(function (response) {

                console.log('response : ', response.data);
                console.log('response success : ', response.data.success);

                if (response.data.success == true) {
                    const config = {
                        headers: {
                            "Accept": "application/json, text/plain, */*",
                            "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
                            "X-AUTH-TOKEN": response.data.data
                        }
                    }
                    return axios.put(profile.data.host() + "/v1/user", data, config)
                        .then(function (response) {
                            console.log('response : ', response.data);
                            if (response.data.success == true) {
                                alert("정보가 변경되었습니다.");
                            } else {
                                toastr.error('정보변경에 실패하였습니다')
                            }
                            location.reload();
                        });
                }
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
                toastr.error('권한(Auths) 정보가 존재하지않습니다. 권한(Auths)를 선택해 주세요.')
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
        msrl: function () {
            let msrl = $.trim($("#msrl").val());
            if (msrl.length < 1) {
                toastr.error('회원필수 정보가 존재하지않습니다. 관리자에게 문의하십시오.')
                return false;
            }
            return msrl;
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


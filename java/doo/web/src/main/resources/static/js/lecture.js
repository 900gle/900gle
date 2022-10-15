const lecture = {
    init: function () {
        var _this = this;
    }, // init end
    search: function () {
        if ($("#select-columns").val().length < 1) {
            toastr.warning('조회컬럼이 선택되지 않았습니다.')
            return false;
        }
        $("#columns").val($("#select-columns").val());
        $("#search-form").submit();
    },
    s3: {
        down: function () {
            if( lecture.data.key() == false || lecture.data.bucket() == false) {
                return false;
            }
            let data = {key: lecture.data.key(), bucket: lecture.data.bucket()}
            const config = {
                headers: {'content-type': 'application/json'}
            }
            return axios.get(lecture.data.host(), {params: data}, config)
                .then(function (response) {
                    console.log(response.data.data);
                   window.location.href = response.data.data;
                })
                .finally(function () {
                   setTimeout(()=>lecture.s3.stop(), 2000);
                });
        },
        stop: function () {
            if( lecture.data.key() == false || lecture.data.bucket() == false) {
                return false;
            }
            toastr.success('다운로드가 완료되면 쿼리결과 파일을 보호합니다.(권한변경)');
            let data = {key: lecture.data.key(), bucket: lecture.data.bucket()}
            const config = {
                headers: {'content-type': 'application/json'}
            }
            return axios.delete(lecture.data.host(), {params: data}, config)
                .then(function (response) {
                    // console.log(response);
                });
        }
    },
    data: {
        key: function () {
            let queryId =  $.trim($("#query-id").val());
            if (queryId.length < 1) {
                toastr.error('데이터가 존재하지않습니다. 올바르지않은 접근입니다.')
                return false;
            }
            return "lecture/" + queryId + ".csv";
        },
        bucket: function () {
            let outputBucket =  $.trim($("#output-bucket").val());
            if (outputBucket.length < 1) {
                toastr.error('아웃풋 버킷명이 존재하지않습니다. 올바르지않은 접근입니다.')
                return false;
            }
            return $.trim($("#output-bucket").val());
        },
        host: function () {
            return $("#host").val() + '/api/s3';
        }
    }

};


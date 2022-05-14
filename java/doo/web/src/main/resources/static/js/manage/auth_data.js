(async function () {

    var arr = new Array();
    const getRoleData = await axios.get(auths.data.host() + "/v1/auths").then(function (response) {
        response.data.list.map((value, index) => {
            arr[index] = {"Name": value.name , "Description": value.discription};
        });
    });

    const createData = await function () {
        var data = {
            loadData: function (filter) {
                return $.grep(this.roles, function (client) {
                    return (!filter.Name || auths.Name.indexOf(filter.Name) > -1)
                        && (!filter.Description || auths.Description.indexOf(filter.Description) > -1);
                });
            },

            insertItem: function (insertingClient) {
                this.auths.push(insertingClient);
            },

            updateItem: function (updatingClient) {
            },

            deleteItem: function (deletingClient) {
                var clientIndex = $.inArray(deletingClient, this.auths);
                this.auths.splice(clientIndex, 1);
            }
        };
        return data;
    };

    window.list_data = createData();
    list_data.auths = arr;

    $("#auths-list").jsGrid({
        width: "100%",
        sorting: true,
        paging: true,
        data: list_data.auths,
        fields: [
            {name: "Name", type: "text", width: 200},
            {name: "Description", type: "text", width: 500}
        ]
    });
}());
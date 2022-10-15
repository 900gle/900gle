(async function () {

    var arr = new Array();
    const getData = await axios.get(users.data.host() + "/v1/users").then(function (response) {

        console.log(response);

        response.data.list.map((value, index) => {
            arr[index] = {
                "Msrl": value.msrl,
                "Name": value.name,
                "Id": value.uid,
                "Roles": value.roles.join(", "),
                "Auths": value.auths.join(", ")
            };
        });
    });

    const createData = await function () {
        var data = {
            loadData: function (filter) {
                return $.grep(this.users, function (client) {
                    return (!filter.Name || users.Name.indexOf(filter.Name) > -1)
                        && (!filter.Id || users.Id.indexOf(filter.Id) > -1);
                });
            },

            insertItem: function (insertingClient) {
                this.users.push(insertingClient);
            },

            updateItem: function (updatingClient) {
            },

            deleteItem: function (deletingClient) {
                var clientIndex = $.inArray(deletingClient, this.users);
                this.users.splice(clientIndex, 1);
            }
        };
        return data;
    };

    window.list_data = createData();
    list_data.users = arr;

    $("#users-list").jsGrid({
        width: "100%",
        sorting: true,
        paging: true,
        rowClick: function (args) {
            window.location.href="/profile/"+ args.item.Msrl;
        },

        data: list_data.users,
        fields: [
            {name: "Name", type: "text", width: 150},
            {name: "Id", type: "text", width: 200},
            {name: "Roles", type: "text", width: 150},
            {name: "Auths", type: "text", width: 300}
        ]
    });
}());
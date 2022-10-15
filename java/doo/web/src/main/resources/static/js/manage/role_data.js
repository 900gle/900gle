(async function () {

    var arr = new Array();
    const getRoleData = await axios.get(roles.data.host() + "/v1/roles").then(function (response) {
        response.data.list.map((value, index) => {
            arr[index] = {"Name": value.name , "Description": value.discription};
        });
    });

    const createRoleData = await function () {
        var roleData = {
            loadData: function (filter) {
                return $.grep(this.roles, function (client) {
                    return (!filter.Name || roles.Name.indexOf(filter.Name) > -1)
                        && (!filter.Description || roles.Description.indexOf(filter.Description) > -1);
                });
            },

            insertItem: function (insertingClient) {
                this.roles.push(insertingClient);
            },

            updateItem: function (updatingClient) {
            },

            deleteItem: function (deletingClient) {
                var clientIndex = $.inArray(deletingClient, this.roles);
                this.roles.splice(clientIndex, 1);
            }
        };
        return roleData;
    };

    window.list_data = createRoleData();
    list_data.roles = arr;

    $("#roles-list").jsGrid({
        width: "100%",
        sorting: true,
        paging: true,
        data: list_data.roles,
        fields: [
            {name: "Name", type: "text", width: 200},
            {name: "Description", type: "text", width: 500}
        ]
    });
}());
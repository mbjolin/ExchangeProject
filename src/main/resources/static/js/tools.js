/* In Data Table */


$("#standardModal").on("hidden.bs.modal", function () {
    myTable.ajax.reload();
});

var objectType;

function initTools(newObjectType) {
    console.log("initTools");
    objectType = newObjectType;
}

function createObject(selector) {
    console.log("Create Object");

    let object = myTable.row(selector.parents('tr') ).data();
    object.type = objectType;
    object.action = "create";
    object.modal = true;

    getCrud(object);
}

function deleteObject(selector) {
    console.log("Delete Object");

    let object = myTable.row(selector.parents('tr') ).data();
    object.type = objectType;
    object.action = "delete";
    object.modal = true;

    getCrud(object);
}

function editObject(selector) {
    console.log("Edit Object");

    let object = myTable.row(selector.parents('tr') ).data();
    object.type = objectType;
    object.action = "update";
    object.modal = true;

    getCrud(object);
}

function editObjectNewWindow() {

}

function openObjectNewWindow() {

}


// object.type = {member, activity}
// object.modal = {true, false}
// object.id = number
// object.action = {create, read, update, delete}

// $.getScript("/webjars/requirejs/2.3.6/require.js", function () {
//     $.getScript("/webjars/is-buffer/2.0.3/index.js", function () {
//         $.getScript("/webjars/flat/4.0.0/index.js", function () {
//         });
//     });
// });
//

function flatObject(object, deepNumber, lastKey) {
    let newObject = {};
    console.log("Call flatObject : " + deepNumber);
    if (deepNumber >= 0) {
        let keys = Object.keys(object);
        for (let i = 0; i < keys.length; i++) {
            let key = keys[i];
            let value = object[key];
            //console.log(" key : " + key + " value : " + value);
            if (value != undefined && typeof value === 'object') {
                let newDeep = deepNumber-1;
                let inObject = flatObject(value, newDeep, key);
                let inKey = Object.keys(inObject);
                for(let j=0; j<inKey.length; j++) {
                    let key = inKey[j];
                    let value = inObject[key];
                    newObject[key] = value;
                }
            } else if (lastKey == null) {
                newObject[key] = value;
            } else {
                newObject[lastKey + "." + key] = value;
            }
        }
        return newObject;
    }
}

function unflatObject(object, deepNumber, lastKey) {
    // TODO
}

function getValueJson(object) {

    var type = object.type;

    var flatId = flatObject(object, 2);
    delete flatId.action;
    delete flatId.type;
    delete flatId.modal;

    console.log(JSON.stringify(flatId));

    const keys = Object.keys(flatId);
    for (let i = 0; i < keys.length; i++) {
        let key = keys[i];
        let inputSelector = $("input[name='" + type + "." + key + "']");
        if (inputSelector.length === 1) {
            object[key] = inputSelector.val();
        } else {
           console.error("This field not exist in form : " + key);
        }
    }
}

function getCrud(object) {
    console.log("Call getCrud : " + object.type + ", " + object.action);

    var current = {};
    current.type = object.type;
    current.id = object.id;
    current.modal = object.modal;
    current.action = object.action;
    $.get('/' + object.type, current,
        function (data) {
            createModal(object, data);
        });
}

function postCrud(objectString) {
    let object = JSON.parse(objectString);
    console.log("Call postCrud : " + object.type + ", " + object.action);

    getValueJson(object);

    $.post('/' + object.type, object,
        function (data) {
            console.log("success");
        });
}

function createModal(object, data) {
    console.log("Create modal");
    $('document').ready(function () {
        $("#inModal").html(data);
        $("#inModalTitle").html(object.action + " - " + object.type);
        $("#inModalSaveButton").attr("onclick", "postCrud('" + JSON.stringify(object) + "'); $('#inModalQuitButton').click();");
        $('#standardModal').modal('show');
    });
}
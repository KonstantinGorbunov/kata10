
// Begin Something modal population and submit functions
var url = "/admin/";
var editModalTarget = url + "loadEntity/";
var tableTarget = url + "loadUserTable/";

// Build the url for the Ajax request for Something.
function showEditModal(index) {
    var editUrl = editModalTarget + index;
    loadEntity(editUrl);
}

function showDeleteModal(index) {
    $('#delete-id').val(index);
}

// Ajax request for Something to populate the modal form.
function loadEntity(url) {
    $.getJSON(url, {}, function (data) {
        populateModal(data, names);
    });
}

// Assign the data values to the modal form.
function populateModal(data) {
    $('#update-id').val(data.id);
    $('#update-name').val(data.name);
    $('#update-sureName').val(data.sureName);
    $('#update-userName').val(data.userName);
    $('#update-address').val(data.address);
    $('#update-password').val(data.password);
}

function clearModal() {
    $('#update-id').val('');
    $('#update-name').val('');
}

function closeModal(name) {
    $(name).modal('toggle');
}

function clearAndCloseModal(name) {
    clearModal();
    closeModal(name);
}

// POST the edits to Something to the server.
function postEdit() {
    var something = $('#edit-form').serialize();
    var editUrl = url + 'update';
    $.post(editUrl, User, function (data) {
        updateTable(data);
    });
    clearAndCloseModal('#umodal');
}

function deleteEntity(entity) {
    var input = $('#delete-id');
    var url = '/' + entity + '/delete/' + input.val();
    $.post(url, function (data) {
        updateTable(data);
    });
    closeModal('#dmodal');
    input.val('');
}

function updateTable(data) {
    $.ajax({
        dataType: "json",
        url: tableTarget,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        success: function (response) {
            $('#table-body').empty();
            $.each(response, function (i, e) {
                var end = e.id + ");'";
                var edit = "'showEditModal(" + end;
                var del = "'showDeleteModal(" + end;
                var row = $('<tr>').append(
                    $('<td>').text(e.id),
                    $('<td>').text(e.name),
                    $('<td>').text(e.sureName),
                    $('<td>').text(e.userName),
                    $('<td>').text(e.address),
                    $('<td>').text(e.password),
                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#umodal' onclick=" +
                        edit + ">Edit</a>"
                    ),
                    $('<td>').append(
                        "<a data-toggle='modal' data-target='#dmodal' onclick=" +
                        del + ">Delete</a>"
                    )
                );
                $('#user-table').append(row);
            });
        }
    });
}
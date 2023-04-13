import EnhancedTable from "../../../EnhancedTable/EnhancedTable";
import {useEffect, useState} from 'react';
import CreateUserDialog from "./CreateUserDialog";
import DeleteEntityDialog from "../../../Dialog/DeleteEntityDialog";
import {deleteUser, getAllUsers} from "../../../../services/user/UserService";
import {ResponseCode} from "../../../../services/utils/ErrorHandler";
import UpdateUserDialog from "./UpdateUserDialog";

const UsersCrudPage = () => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [createDialogState, setCreateDialogState] = useState(false);
    const [updateDialogState, setUpdateDialogState] = useState(false);
    const [deleteDialogState, setDeleteDialogState] = useState(false);


    useEffect(() => {
        refresh();
    }, []);

    function handleDelete(id) {
        deleteUser(id).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                setUsers(users.filter((user) => user.id !== id));
            } else if (response === ResponseCode.FORBIDDEN) {
                alert("You cannot delete the last admin.");
            }
        });
    }

    function refresh(){
        getAllUsers().then((array) => {
            setUsers(array);
        });
    }


    return (
        <>
            <EnhancedTable
                entityName='user'
                rows={users}
                setCreateDialogState={setCreateDialogState}
                setUpdateDialogState={setUpdateDialogState}
                setDeleteDialogState={setDeleteDialogState}
                setSelectedTarget={setSelectedUser}
            />
            <CreateUserDialog
                open={createDialogState}
                setOpen={setCreateDialogState}
                callback={refresh}
            />
            <UpdateUserDialog
                open={updateDialogState}
                setOpen={setUpdateDialogState}
                user={selectedUser}
                callback={refresh}
            />
            <DeleteEntityDialog open={deleteDialogState}
                                entityName='user'
                                entityId={selectedUser?.id}
                                deleteFunction={handleDelete}
                                setOpen={setDeleteDialogState}
            />
        </>
    );
}
export default UsersCrudPage;

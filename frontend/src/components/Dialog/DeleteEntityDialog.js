import DialogButton from "./DialogButton";
import * as React from "react";
import SimpleDialog from "./SimpleDialog";
import PropTypes from "prop-types";

export default function DeleteEntityDialog(props) {
    const {open, setOpen, entityName, entityId, deleteFunction} = props;

    function handleDelete(){
        deleteFunction(entityId);
        setOpen(false);
    }
    function handleClose(){
        setOpen(false);
    }

    return (
        <SimpleDialog title={'Are you sure want to delete ' + (entityName?.toLowerCase() || 'entity') + ' with id \'' + entityId + '\' ?'}
                      open={open}>
            <DialogButton onClick={handleDelete} label='Yes' isDefault={true}/>
            <DialogButton onClick={handleClose} label='No' isDefault={false}/>
        </SimpleDialog>
    );
}

DeleteEntityDialog.propTypes = {
    open: PropTypes.bool.isRequired,
    setOpen: PropTypes.func.isRequired,
    entityName: PropTypes.string,
    entityId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    deleteFunction: PropTypes.func.isRequired,
}
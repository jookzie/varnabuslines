import * as React from 'react';
import PropTypes from 'prop-types';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import Box from "@mui/material/Box";

export default function SimpleDialog(props) {
    const {title, open, children, sx } = props;

    return (
        <Dialog open={open}
                PaperProps={{
                    style: {
                        borderRadius: 20,
                        minWidth: 'fit-content'
                    }

                }}
                sx={{
                    mt: 5
                }}
        >
            <DialogTitle>{title}</DialogTitle>
            <Box sx={{
                display: 'flex',
                margin: 2,
                gap: 2,
                ...sx
            }}>
                {children}
            </Box>
        </Dialog>
    );
}


SimpleDialog.propTypes = {
    title: PropTypes.string.isRequired,
    open: PropTypes.bool.isRequired,
};

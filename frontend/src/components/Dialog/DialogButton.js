import * as React from "react";
import PropTypes from "prop-types";
import Button from "@mui/material/Button";

export default function DialogButton(props){
    const {label, isDefault, sx, ...params} = props;

    return (
        <Button
            sx={{
                flex: 1,
                border: 2,
                borderRadius: 4,
                borderColor: 'primary.main',
                bgcolor: isDefault ? 'primary.main' : 'transparent',
                color: isDefault ? 'white' : 'primary.main',
                '&:hover': {
                    borderColor: 'secondary.main',
                    bgcolor: 'secondary.main',
                    color: 'white',
                },
                ...sx
            }}
            {...params}
        >
            {label}
        </Button>
    )
}
DialogButton.propTypes = {
    label: PropTypes.string.isRequired,
    isDefault: PropTypes.bool.isRequired,
}

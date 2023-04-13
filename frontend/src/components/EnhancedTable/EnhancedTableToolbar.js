import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import PropTypes from 'prop-types';
import AddIcon from '@mui/icons-material/Add';
import CustomButton from "../CustomButton";

export default function EnhancedTableToolbar(props) {
    const {title, onClickAddNew, children} = props;

    return (
        <Toolbar
            sx={{
                px: 2,
                display: 'flex',
            }}
        >
            <Typography
                variant="h6"
                id="tableTitle"
                component="div"
                sx={{
                    ml: 2,
                    flex: 1
            }}>
                {title}
            </Typography>
            {children}
            {onClickAddNew && (
                <CustomButton
                    onClick={onClickAddNew}
                    invertColors={true}
                >
                    <AddIcon/>
                    Add new
                </CustomButton>
            )}
        </Toolbar>
    );
}

EnhancedTableToolbar.propTypes = {
    title: PropTypes.string.isRequired,
    onClickAddNew: PropTypes.func,
};
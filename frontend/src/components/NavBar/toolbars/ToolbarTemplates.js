import Box from "@mui/material/Box";

export const LeftSide = (props) => (
    <Box sx={{
        display: 'flex',
        flexGrow: 1,
        gap: 3,
        mr: 1.5
    }}>
        {props.children}
    </Box>
);
export const RightSide = (props) => (
    <Box sx={{
        display: 'flex',
        flexGrow: 1,
        justifyContent: 'flex-end',
        gap: 3,
        ml: 1.5
    }}>
        {props.children}
    </Box>
);
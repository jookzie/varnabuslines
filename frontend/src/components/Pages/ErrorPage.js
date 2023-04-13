import {useRouteError} from "react-router-dom";
import {Box} from "@mui/system";
import Typography from "@mui/material/Typography";

export default function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <Box id="error-page" sx={{
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        height: '90vh',
    }}>
        <Typography sx={{
            fontSize: '2rem',
            fontWeight: 'bold',
        }}>
            Error: {error.statusText || error.message}
        </Typography>
    </Box>
  );
}
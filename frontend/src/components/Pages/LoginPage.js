import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import {useState} from 'react';
import {login} from "../../services/authentication/Authentication";
import {Link, useNavigate} from "react-router-dom";
import {getMessageFromErrorCode, ResponseCode} from "../../services/utils/ErrorHandler";
import LoadingButton from "@mui/lab/LoadingButton";

const LoginPage = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        setLoading(true);
        const data = new FormData(event.currentTarget);
        const email = data.get('email');
        const password = data.get('password');

        login(email, password).then((response) => {
            if (response === ResponseCode.SUCCESS) {
                navigate('/');
            }
            else if(response === ResponseCode.UNAUTHORIZED){
                setErrorMessage('Invalid email or password.');
            }
            else {
                setErrorMessage(getMessageFromErrorCode(response, 'user') || "Unknown error.");
            }
            setLoading(false);
        });
    }

    return (
        <Container component="main" maxWidth="xs">
            <Box sx={{
                marginTop: 24,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}>
                <Typography component="h1" variant="h5">
                    Log in
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />

                    <LoadingButton
                        loading={loading}
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2, borderRadius: 3, py: 1, fontSize: 16}}
                    >
                        Log in
                    </LoadingButton>
                    <Grid container>
                        <Grid item>
                            <Link to="/register" style={{textDecoration: 'none'}}>
                                <Typography id="register-tip" sx={{
                                    color: 'primary.main',
                                    textDecoration: 'underline',
                                    textDecorationColor: 'primary.main'}}>
                                    Don't have an account? Sign Up
                                </Typography>
                            </Link>
                            <Typography id="error-message"
                                sx={{
                                    color: 'red',
                                    marginTop: 1,
                                    fontSize: 20,
                            }}>
                                {errorMessage}
                            </Typography>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
}
export default LoginPage;
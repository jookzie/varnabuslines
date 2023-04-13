import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import {Link, useNavigate} from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import {createUser} from "../../services/user/UserService";
import {getMessageFromErrorCode, ResponseCode} from "../../services/utils/ErrorHandler";
import LoadingButton from '@mui/lab/LoadingButton';


const RegisterPage = () => {
    const [errorMessage, setErrorMessage] = React.useState('');
    const [loading, setLoading] = React.useState(false);
    const navigate = useNavigate();

    function handleSubmit(event) {
        event.preventDefault();
        setLoading(true);
        const data = new FormData(event.currentTarget);

        const email = data.get('email');
        const password = data.get('password');
        const confirmPassword = data.get('confirm-password');

        if (password !== confirmPassword) {
            setErrorMessage('Passwords do not match.');
            setLoading(false);
        }
        else {
            createUser(email, password).then((response) => {
                if(response === ResponseCode.SUCCESS){
                    navigate('/authentication');
                }
                else{
                    setErrorMessage(getMessageFromErrorCode(response, 'user'));
                }
                setLoading(false);
            });
        }
    }
    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 20,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography component="h1" variant="h5">
                    Register
                </Typography>
                <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="new-password"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="confirm-password"
                                label="Confirm Password"
                                type="password"
                                id="confirm-password"
                                autoComplete="new-password"
                            />
                        </Grid>
                    </Grid>
                    <LoadingButton
                        loading={loading}
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2, borderRadius: 3, py: 1, fontSize: 16}}
                    >
                        Register
                    </LoadingButton>
                    <Grid container justifyContent="flex-start">
                        <Grid item>
                            <Link to="/authentication" style={{textDecoration: 'none'}}>
                                <Typography id="login-tip" sx={{
                                    color: 'primary.main',
                                    textDecoration: 'underline',
                                    textDecorationColor: 'primary.main'
                                }}>
                                    Already have an account? Login here
                                </Typography>
                            </Link>
                            <Typography id="error-message"
                                sx={{
                                    color: 'red',
                                    marginTop: 1,
                                    fontSize: 20,
                                }}
                            >
                                {errorMessage}
                            </Typography>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
};
export default RegisterPage;